package com.amitb.a23a_10357_soccerwager.Utils;

import java.util.ArrayList;

public class League {

    private String leagueName;
    private ArrayList<User> participants;

    public League() {
    }

    public String getLeagueName() {
        return leagueName;
    }

    public League setLeagueName(String leagueName) {
        this.leagueName = leagueName;
        return this;
    }

    public ArrayList<User> getParticipants() {
        return participants;
    }

    public League setParticipants(ArrayList<User> participants) {
        this.participants = participants;
        return this;
    }
}
