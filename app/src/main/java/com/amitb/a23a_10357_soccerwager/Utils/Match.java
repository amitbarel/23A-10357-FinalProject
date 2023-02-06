package com.amitb.a23a_10357_soccerwager.Utils;

public class Match {
    String team1;
    String team2;
    int score1 = 0;
    int score2 = 0;
    String winner;

    public Match() {
    }

    public String getTeam1() {
        return team1;
    }

    public Match setTeam1(String team1) {
        this.team1 = team1;
        return this;
    }

    public String getTeam2() {
        return team2;
    }

    public Match setTeam2(String team2) {
        this.team2 = team2;
        return this;
    }

    public int getScore1() {
        return score1;
    }

    public Match setScore1(int score1) {
        this.score1 = score1;
        return this;
    }

    public int getScore2() {
        return score2;
    }

    public Match setScore2(int score2) {
        this.score2 = score2;
        return this;
    }

    public String getWinner() {
        String win = score1>score2? team1:team2;
        return win ;
    }

    public Match setWinner(String winner) {
        this.winner = winner;
        return this;
    }
}



