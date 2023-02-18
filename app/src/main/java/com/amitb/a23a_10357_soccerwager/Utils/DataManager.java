package com.amitb.a23a_10357_soccerwager.Utils;

import android.util.Log;

import androidx.annotation.NonNull;

import com.amitb.a23a_10357_soccerwager.Interfaces.OnGetDataListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.lang.ref.Reference;
import java.util.ArrayList;
import java.util.Collections;

public class DataManager {

    private final static int FIXTURE_SIZE = 7;
    private static ArrayList<Team> teams = new ArrayList<>();
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
        Log.d("loadFixture: ",snapshot.getKey());
//        GenericTypeIndicator<ArrayList<Guess>> t1 = new GenericTypeIndicator<ArrayList<Guess>>() {};
//        ArrayList<Guess> guesses = snapshot.child("guesses").getValue(t1);
//        GenericTypeIndicator<ArrayList<Match>> t2 = new GenericTypeIndicator<ArrayList<Match>>() {};
//        ArrayList<Match> matches = snapshot.child("matches").getValue(t2);
//        currentFixture = new Fixture().setGuesses(guesses).setMatches(matches);
//        currentFixture = snapshot.getValue(Fixture.class);
        currentFixture = snapshot.getValue(Fixture.class);
        Log.d("loadFixture: ",currentFixture.toString());
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

    public static void setUserToDB(User user, String uid){
        DatabaseReference ref = db.getReference("users").child(uid);
        ref.setValue(user);
    }

    public static User loadUserFromDB(String uid){
        final User[] user = new User[1];
        DatabaseReference ref = db.getReference("users").child(uid);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user[0] = snapshot.getValue(User.class);
                Log.d("user",user[0].toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return user[0];
    }

    public static void givePoints() {
        DatabaseReference guessRef = db.getReference("fixture").child("guesses");
        guessRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()) {
                    Guess g = ds.getValue(Guess.class);
                    User user = loadUserFromDB(g.getUser());
                    user.incScore(calcPtsFromGuess(g));
                    setUserToDB(user,g.getUser());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private static int calcPtsFromGuess(Guess g) {
        int points = 0;
        for (int i = 0; i < FIXTURE_SIZE; i++) {
            Score current = g.getFixtureScores().get(i);
            points += calcPts(currentFixture.getMatches().get(i),current.getS1(),current.getS2());
        }
        return points;
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
