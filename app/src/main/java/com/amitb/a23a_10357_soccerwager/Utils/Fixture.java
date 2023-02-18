package com.amitb.a23a_10357_soccerwager.Utils;

import java.util.ArrayList;

public class Fixture {
    ArrayList<Guess> guesses;
    ArrayList<Match> matches;

    public Fixture() {
    }

    public ArrayList<Guess> getGuesses() {
        return guesses;
    }

    public Fixture setGuesses(ArrayList<Guess> guesses) {
        this.guesses = guesses;
        return this;
    }

    public ArrayList<Match> getMatches() {
        return matches;
    }

    public Fixture setMatches(ArrayList<Match> matches) {
        this.matches = matches;
        return this;
    }


}
