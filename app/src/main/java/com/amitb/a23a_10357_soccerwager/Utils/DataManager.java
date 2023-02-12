package com.amitb.a23a_10357_soccerwager.Utils;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class DataManager {

    private static ArrayList<Team> teams = new ArrayList<>();
    private static ArrayList<Match> fixture = new ArrayList<>();
    private static FirebaseDatabase db = FirebaseDatabase.getInstance();


    public static ArrayList<Match> getFixture() {
        return fixture;
    }

    public static void loadTeams(){
        DatabaseReference teamsRef = db.getReference("teams");
        teamsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot team:snapshot.getChildren()) {
                    teams.add(team.getValue(Team.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public static void loadFixture(){
        DatabaseReference ref = db.getReference("fixture");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot match:snapshot.getChildren()) {
                    fixture.add(match.getValue(Match.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    };

    public static void createFixture(){
        ArrayList<Team> temp = teams;
        Collections.shuffle(temp);
        for (int i = 0; i < teams.size(); i+=2) {
            Match m = new Match().setTeam1(temp.get(i)).setTeam2(temp.get(i+1));
            fixture.add(m);
        }
        writeData("fixture",fixture);
    }

    private static void writeData(String path,Collection collection) {
        DatabaseReference ref = db.getReference(path);
        ref.setValue(collection);
    }
}
