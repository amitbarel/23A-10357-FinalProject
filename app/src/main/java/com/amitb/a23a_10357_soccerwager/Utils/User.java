package com.amitb.a23a_10357_soccerwager.Utils;

import java.util.HashMap;

public class User {

    public String username;
    public String email;
    public int score;
    public HashMap<String,Boolean> leagues;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
        this.score = 0;
        this.leagues = new HashMap<>();
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public int getScore() {
        return score;
    }

    public void incScore(int inc){
        score += inc;
    }

    public HashMap<String, Boolean> getLeagues(){ return leagues;}
}