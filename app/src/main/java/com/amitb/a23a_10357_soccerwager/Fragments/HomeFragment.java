package com.amitb.a23a_10357_soccerwager.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amitb.a23a_10357_soccerwager.Interfaces.OnGetDataListener;
import com.amitb.a23a_10357_soccerwager.R;
import com.amitb.a23a_10357_soccerwager.Utils.DataManager;
import com.amitb.a23a_10357_soccerwager.Utils.League;
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
import java.util.concurrent.atomic.AtomicReference;


public class HomeFragment extends Fragment {

    private FirebaseAuth mAuth;
    private User currentUser;
    private View view;
    private TextView greetings,points;
    private RecyclerView leagues;
    private RankAdapter rankAdapter;
    private AppCompatEditText leagueName;
    private AppCompatButton createLeague;
    private Collection<League> allLeagues;
    private ArrayList<String> leaguesArr = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        mAuth = FirebaseAuth.getInstance();
        findViews();
        greetings.setText("Hello, " + mAuth.getCurrentUser().getDisplayName());
        String uid = mAuth.getCurrentUser().getUid();
        if (leagueName.getText() != null){
            createLeague.setEnabled(true);
        }
        createLeague.setOnClickListener(v->addLeague(leagueName.getText().toString()));
        getUserFromUid(uid);

        return view;
    }

    @NonNull
    private void setUserLeagues() {
        for (League l: allLeagues){
            if (checkIsInLeague(l.getUuid())){
                leaguesArr.add(l.getLeagueName());
            }
        }
    }

    private boolean checkIsInLeague(String leagueUid) {
        return currentUser.getLeagues().contains(leagueUid);
    }

    private void getUserFromUid(String uid){
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
                           rankAdapter = new RankAdapter(getContext(), leaguesArr);
                           leagues.setLayoutManager(new LinearLayoutManager(getContext()));
                           leagues.setAdapter(rankAdapter);
                       }
                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError error) {

                   }
               });
           }
        });
    }

    private void addLeague(String name) {
        leagueName.setText("");
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        League league = new League().setLeagueName(name);
        league.addParticipants(uid);
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("leagues").child(league.getUuid());
        ref.setValue(league);
        DatabaseReference ref2 = db.getReference("users").child(uid);
        ref2.get().addOnCompleteListener(event->{
            if (event.isSuccessful()){
                User user = event.getResult().getValue(User.class);
                user.getLeagues().add(league.getUuid());
                ref2.setValue(user);
            }
        });
    }

    private void findViews() {
        greetings = view.findViewById(R.id.greetings_MSG);
        points = view.findViewById(R.id.points_TXT);
        leagues = view.findViewById(R.id.table_leagues);
        leagueName = view.findViewById(R.id.ET_league);
        createLeague = view.findViewById(R.id.BTN_create);
    }
}