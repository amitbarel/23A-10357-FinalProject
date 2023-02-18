package com.amitb.a23a_10357_soccerwager.Utils;

import java.util.ArrayList;

public class Guess {
    private ArrayList<Score> fixtureScores;
    private String user;


    public Guess() {
        fixtureScores = new ArrayList<>();
    }

    public void addScore(int pos, int s1, int s2){
        fixtureScores.add(pos,new Score().setS1(s1).setS2(s2));
    }

    public Guess setUser(String user) {
        this.user = user;
        return this;
    }

    public ArrayList<Score> getFixtureScores() {
        return fixtureScores;
    }

    public String getUser() {
        return user;
    }
}