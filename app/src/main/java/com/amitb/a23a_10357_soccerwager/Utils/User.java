package com.amitb.a23a_10357_soccerwager.Utils;

import java.util.ArrayList;

public class User {

    public String username;
    public String email;
    public int score;
    public ArrayList<String> leagues = new ArrayList<>();

    public User() {
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
        this.score = 0;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public User setScore(int score) {
        this.score = score;
        return this;
    }

    public int getScore() {
        return score;
    }

    public void incScore(int inc){
        score += inc;
    }

    public ArrayList<String> getLeagues(){
        return leagues;
    }

    public void setLeagues(ArrayList<String> leagues){
        this.leagues = leagues;
    }
}