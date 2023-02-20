package com.amitb.a23a_10357_soccerwager.Utils;

import java.util.ArrayList;
import java.util.UUID;

public class League {

    private String leagueName;
    private ArrayList<String> participants;
    private String uuid;

    public League() {
        uuid = UUID.randomUUID().toString();
    }

    public String getLeagueName() {
        return leagueName;
    }

    public League setLeagueName(String leagueName) {
        this.leagueName = leagueName;
        return this;
    }

    public String getUuid() {
        return uuid;
    }

    public League setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public ArrayList<String> getParticipants() {
        return participants;
    }

    public League setParticipants(ArrayList<String> participants) {
        this.participants = participants;
        return this;
    }

    public void addParticipants(String uid) {
        if (participants == null){
            participants = new ArrayList<>();
        }
        participants.add(uid);
    }
}
