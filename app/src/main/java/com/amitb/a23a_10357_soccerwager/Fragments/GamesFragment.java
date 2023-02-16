package com.amitb.a23a_10357_soccerwager.Fragments;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amitb.a23a_10357_soccerwager.R;
import com.amitb.a23a_10357_soccerwager.Utils.DataManager;
import com.amitb.a23a_10357_soccerwager.Utils.Guess;
import com.amitb.a23a_10357_soccerwager.Utils.Match;
import com.amitb.a23a_10357_soccerwager.Utils.MatchAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GamesFragment extends Fragment {

    private View view;
    private RecyclerView matches;
    private AppCompatButton sendGuess;
    private MatchAdapter matchAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_games, container, false);
        matches = view.findViewById(R.id.games_recycle);
        sendGuess = view.findViewById(R.id.BTN_send);
        DataManager.loadFixture();
        matchAdapter = new MatchAdapter(getContext(),DataManager.getFixture().getMatches());
        matches.setLayoutManager(new LinearLayoutManager(getContext()));
        matches.setAdapter(matchAdapter);
        sendGuess.setOnClickListener(v -> handleGuess());
        return view;
    }

    private void handleGuess() {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Guess myGuess = new Guess().setUser(uid);
        for (int i = 0; i < 7; i++) {
            View v = matches.getChildAt(i);
            AppCompatEditText s1 = (AppCompatEditText) v.findViewById(R.id.ET_team1_score);
            int score1 = Integer.parseInt(s1.getText().toString());
            AppCompatEditText s2 = (AppCompatEditText) v.findViewById(R.id.ET_team2_score);
            int score2 = Integer.parseInt(s2.getText().toString());
            myGuess.addScore(i,score1,score2);
        }
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("fixture").child("guesses").child(uid);
        ref.setValue(myGuess);
    }
}