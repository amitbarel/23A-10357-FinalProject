package com.amitb.a23a_10357_soccerwager.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.amitb.a23a_10357_soccerwager.R;
import com.amitb.a23a_10357_soccerwager.Utils.League;
import com.amitb.a23a_10357_soccerwager.Utils.RankAdapter;
import com.amitb.a23a_10357_soccerwager.Utils.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class LeaguesFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private View view;
    private AppCompatSpinner leagues;
    private RecyclerView lst_Leagues;
    private FirebaseAuth mAuth;
    private FirebaseDatabase db;
    private User currentUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_leagues, container, false);
        leagues = view.findViewById(R.id.spinner);
        lst_Leagues = view.findViewById(R.id.leagues_frame);
        db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("users");
        mAuth = FirebaseAuth.getInstance();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List <User> users = snapshot.getValue(List.class);
                for (User user:users) {
                    if (user.getEmail().equals(mAuth.getCurrentUser().getEmail())){
                        currentUser = user;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        List<String> userLeagueNames = null;
        for (int i = 0; i < currentUser.getLeagues().size(); i++) {
            userLeagueNames.add(currentUser.getLeagues().get(i));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, userLeagueNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        leagues.setAdapter(adapter);
        leagues.setOnItemSelectedListener(this);
        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}