package com.amitb.a23a_10357_soccerwager.Utils;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class League {

    private String leagueName;
    private HashMap<Integer, User> participants; //
    public League() {

    }

    public String getLeagueName() {
        return leagueName;
    }

    public League setLeagueName(String leagueName) {
        this.leagueName = leagueName;
        return this;
    }

    public List<User> getParticipants() {
        List<User> sorted = new ArrayList<>(participants.values());
        sorted.sort(Comparator.comparingInt(o -> o.getScore()));
        return sorted;
    }

    public League setParticipants(HashMap<Integer, User> participants) {
        this.participants = participants;
        return this;
    }
}
