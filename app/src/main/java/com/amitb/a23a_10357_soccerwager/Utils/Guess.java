package com.amitb.a23a_10357_soccerwager.Utils;

import java.util.ArrayList;
import java.util.List;

public class Guess {
    private List<Score> fixtureScores = new ArrayList<>();
    private String user;

    public Guess() {
    }

    public void addScore(int pos, int s1, int s2){
        fixtureScores.add(pos,new Score().setS1(s1).setS2(s2));
    }

    public List<Score> getFixtureScores() {
        return fixtureScores;
    }

    public void setFixtureScores(List<Score> fixtureScores) {
        this.fixtureScores = fixtureScores;
    }

    public String getUser() {
        return user;
    }

    public Guess setUser(String user) {
        this.user = user;
        return this;
    }
}