package com.amitb.a23a_10357_soccerwager.Utils;

import java.util.ArrayList;
import java.util.List;

public class Fixture {
    List<Guess> guesses;
    List<Match> matches;

    public Fixture() {
    }

    public List<Guess> getGuesses() {
        return guesses;
    }

    public Fixture setGuesses(List<Guess> guesses) {
        this.guesses = guesses;
        return this;
    }

    public List<Match> getMatches() {
        return matches;
    }

    public Fixture setMatches(ArrayList<Match> matches) {
        this.matches = matches;
        return this;
    }


}
