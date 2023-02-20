package com.amitb.a23a_10357_soccerwager.Utils;

public class Match {
    Team team1;
    Team team2;
    int score1 = 0;
    int score2 = 0;

    public Match() {
    }

    public Team getTeam1() {
        return team1;
    }

    public Match setTeam1(Team team1) {
        this.team1 = team1;
        return this;
    }

    public Team getTeam2() {
        return team2;
    }

    public Match setTeam2(Team team2) {
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

    @Override
    public String toString() {
        return "Match{" +
                "team1=" + team1.getName() +
                ", team2=" + team2.getName() +
                ", score1=" + score1 +
                ", score2=" + score2 +
                '}';
    }
}



