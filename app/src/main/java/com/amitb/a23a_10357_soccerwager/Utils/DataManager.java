package com.amitb.a23a_10357_soccerwager.Utils;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class DataManager {

    private final static int FIXTURE_SIZE = 7;
    private static ArrayList<Team> teams = new ArrayList<>();
    private static FirebaseDatabase db = FirebaseDatabase.getInstance();
    private static Fixture currentFixture = new Fixture();
    private static String adminId;


    public static Fixture getFixture() {
        return currentFixture;
    }

    public static String getAdminId() {
        DatabaseReference ref = db.getReference("admin");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                adminId = snapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return adminId;
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
                currentFixture = snapshot.getValue(Fixture.class);
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
            currentFixture.getMatches().add(m);
        }
        DatabaseReference ref = db.getReference("fixture");
        ref.setValue(currentFixture);
    }


    public static void fillFixture() {
        for (Match m:currentFixture.getMatches()) {
            m.setScore1(randScore());
            m.setScore2(randScore());
        }
    }

    public static void givePoints() {
        for (Guess g:currentFixture.getGuesses()){
            DatabaseReference ref = db.getReference("users");
            int points = 0;
            for (int i = 0; i < FIXTURE_SIZE; i++) {
                Match current = g.getFixtureScores().get(i);
                points += calcPts(currentFixture.getMatches().get(i),current.getScore1(),current.getScore2());
            }
            int fixturePoints = points;
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    HashMap<String,User> users = snapshot.getValue(HashMap.class);
                    users.get(g.getUser()).incScore(fixturePoints);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    public static int randScore(){
        int min = 0;
        int max = 3;
        return (int)(Math.random() * ((max-min)+1));
    }

    public static int calcPts(Match match, int s1, int s2){
        if (match.getScore1() == s1 && match.getScore2() == s2)
            return 3;
        else if(s1>s2 && match.getScore1()> match.getScore2() || s1<s2 && match.getScore1()< match.getScore2()){
            return 1;
        }
        return 0;
    }
}
