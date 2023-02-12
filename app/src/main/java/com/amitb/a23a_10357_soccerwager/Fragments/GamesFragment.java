package com.amitb.a23a_10357_soccerwager.Fragments;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amitb.a23a_10357_soccerwager.R;
import com.amitb.a23a_10357_soccerwager.Utils.DataManager;
import com.amitb.a23a_10357_soccerwager.Utils.MatchAdapter;

public class GamesFragment extends Fragment {

    private View view;
    private RecyclerView matches;
    private AppCompatButton sendGuess;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_games, container, false);
        matches = view.findViewById(R.id.games_recycle);
        sendGuess = view.findViewById(R.id.BTN_send);
        DataManager.createFixture();
        DataManager.loadFixture();
        MatchAdapter matchAdapter = new MatchAdapter(getContext(),DataManager.getFixture());
        matches.setLayoutManager(new LinearLayoutManager(getContext()));
        matches.setAdapter(matchAdapter);
        sendGuess.setOnClickListener(v -> handleGuess());
        return view;
    }

    private void handleGuess() {

    }
}