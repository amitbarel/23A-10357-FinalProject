package com.amitb.a23a_10357_soccerwager.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.amitb.a23a_10357_soccerwager.R;
import com.amitb.a23a_10357_soccerwager.Utils.League;
import com.amitb.a23a_10357_soccerwager.Utils.LeagueAdapter;
import com.amitb.a23a_10357_soccerwager.Utils.RankAdapter;
import com.amitb.a23a_10357_soccerwager.Utils.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class LeaguesFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private View view;
    private AppCompatSpinner leaguesSpinner;
    private RecyclerView lst_Leagues;
    private RecyclerView joinLeagues;
    private AppCompatButton btnJoin;
    private RankAdapter rankAdapter;
    private LeagueAdapter leagueAdapter;
    private FirebaseAuth mAuth;
    private FirebaseDatabase db;
    private User currentUser;
    private Collection<League> allLeagues;
    private ArrayList<String> userLeagues = new ArrayList<>();
    private ArrayList<User> leagueParticipants = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_leagues, container, false);
        leaguesSpinner = view.findViewById(R.id.spinner);
        lst_Leagues = view.findViewById(R.id.leagues_frame);
        joinLeagues = view.findViewById(R.id.leagues_to_join);
        btnJoin = view.findViewById(R.id.BTN_join);
        db = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        handleLeagues();
        return view;
    }

    private void handleLeagues() {
        String uid = mAuth.getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users").child(uid);
        ref.get().addOnCompleteListener(event->{
            if (event.isSuccessful()){
                currentUser = event.getResult().getValue(User.class);
                DatabaseReference leaguesRef = FirebaseDatabase.getInstance().getReference("leagues");
                leaguesRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        GenericTypeIndicator<Map<String, League>> genericTypeIndicator = new GenericTypeIndicator<Map<String, League>>() {};
                        Map<String,League> leaguesMap = snapshot.getValue(genericTypeIndicator);
                        if (leaguesMap != null){
                            allLeagues = leaguesMap.values();
                            setUserLeagues();
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, userLeagues);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            leaguesSpinner.setAdapter(adapter);
                            leaguesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    String value = parent.getItemAtPosition(position).toString();
                                    for (League league: allLeagues) {
                                        if (league.getLeagueName() == value){
                                            loadUsersFromDB(league.getParticipants());
                                        }
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        }
                        ArrayList<String> freeLeagues = new ArrayList<>();
                        for (League league: allLeagues) {
                            freeLeagues.add(league.getLeagueName());
                        }
                        freeLeagues.removeAll(userLeagues);
                        rankAdapter = new RankAdapter(getContext(), freeLeagues);
                        joinLeagues.setLayoutManager(new LinearLayoutManager(getContext()));
                        joinLeagues.setAdapter(rankAdapter);
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    private void loadUsersFromDB(ArrayList<String> users) {
        leagueParticipants.clear();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
        ref.get().addOnCompleteListener(event->{
            if (event.isSuccessful()){
                for (DataSnapshot ds: event.getResult().getChildren()){
                    if (users.contains(ds.getKey())){
                        leagueParticipants.add(ds.getValue(User.class));
                    }

                }
                leagueAdapter = new LeagueAdapter(getContext(),leagueParticipants);
                lst_Leagues.setLayoutManager(new LinearLayoutManager(getContext()));
                lst_Leagues.setAdapter(leagueAdapter);
            }
        });
    }

    @NonNull
    private void setUserLeagues() {
        for (League l: allLeagues){
            if (checkIsInLeague(l.getUuid())){
                userLeagues.add(l.getLeagueName());
            }
        }
    }

    private boolean checkIsInLeague(String leagueUid) {
        return currentUser.getLeagues().contains(leagueUid);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}