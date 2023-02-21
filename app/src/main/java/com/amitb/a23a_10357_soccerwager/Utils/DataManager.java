package com.amitb.a23a_10357_soccerwager.Utils;


import androidx.annotation.NonNull;

import com.amitb.a23a_10357_soccerwager.Interfaces.OnGetDataListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class DataManager {

    private final static int FIXTURE_SIZE = 7;
    private static ArrayList<Team> teams = new ArrayList<>();
    private static ArrayList<League> leauges = new ArrayList<>();
    private static FirebaseDatabase db = FirebaseDatabase.getInstance();
    private static Fixture currentFixture;
    private static String adminId;


    public static Fixture getFixture() {
        return currentFixture;
    }

    public static void readData(DatabaseReference ref, final OnGetDataListener listener) {
        listener.onStart();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listener.onSuccess(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onFailure();
            }
        });

    }

    public static Boolean isAdmin(DataSnapshot snapshot, String uid) {
        final String[] retValue = new String[1];
        adminId = snapshot.getValue(String.class);
        retValue[0] = adminId.substring(1);
        if (uid.equals(retValue[0])){
            return true;
        }
        return false;
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

    public static void loadFixture(DataSnapshot snapshot){
        ArrayList<Guess> guesses = new ArrayList<>();
        ArrayList<Match> matches = new ArrayList<>();
        for(DataSnapshot db:snapshot.child("guesses").getChildren()){
            guesses.add(db.getValue(Guess.class));
        }
        for(DataSnapshot db:snapshot.child("matches").getChildren()){
            matches.add(db.getValue(Match.class));
        }
        currentFixture = new Fixture().setGuesses(guesses).setMatches(matches);
    }

    public static void createFixture(){
        ArrayList<Team> temp = teams;
        Collections.shuffle(temp);
        Fixture fixture = new Fixture();
        ArrayList<Match> allMatches = new ArrayList<>();
        for (int i = 0; i < teams.size(); i+=2) {
            Match m = new Match().setTeam1(temp.get(i)).setTeam2(temp.get(i+1));
            m.setScore1(randScore());
            m.setScore2(randScore());
            allMatches.add(m);
        }
        fixture.setMatches(allMatches);
        DatabaseReference ref = db.getReference("fixture");
        ref.setValue(fixture);
    }


    public static void setUserToDB(User user, String uid){
        DatabaseReference ref = db.getReference("users").child(uid);
        ref.setValue(user);
    }


    public static int calcPtsFromGuess(Guess g) {
        int points = 0;
        for (int i = 0; i < FIXTURE_SIZE; i++) {
            Score current = g.getFixtureScores().get(i);
            points += calcPts(currentFixture.getMatches().get(i),current.getS1(),current.getS2());
        }
        return points;
    }

    public static int randScore(){
        int min = 0;
        int max = 4;
        return (int)(Math.random() * ((max-min)+1));
    }

    public static int calcPts(Match match, int s1, int s2){
        if (match.getScore1() == s1 && match.getScore2() == s2)
            return 3;
        else if((s1>s2 && (match.getScore1() > match.getScore2())) || (s1<s2 && (match.getScore1()< match.getScore2()))){
            return 1;
        }
        else if (s1 == s2 && match.getScore1() == match.getScore2()){
            return 1;
        }
        return 0;
    }


}
