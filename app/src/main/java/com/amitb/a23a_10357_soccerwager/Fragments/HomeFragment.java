package com.amitb.a23a_10357_soccerwager.Fragments;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.amitb.a23a_10357_soccerwager.R;
import com.amitb.a23a_10357_soccerwager.Utils.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class HomeFragment extends Fragment {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private View view;
    private TextView greetings,points;
    private RecyclerView leagues;
    private AppCompatEditText leagueName;
    private AppCompatButton createLegaue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        mAuth = FirebaseAuth.getInstance();
        findViews();
        greetings.setText("Hello, " + mAuth.getCurrentUser().getDisplayName());
        if (leagueName.getText() != null){
            createLegaue.setEnabled(true);
        }
        return view;
    }

    private void findViews() {
        greetings = view.findViewById(R.id.greetings_MSG);
        points = view.findViewById(R.id.points_TXT);
        leagues = view.findViewById(R.id.table_leagues);
        leagueName = view.findViewById(R.id.ET_league);
        createLegaue = view.findViewById(R.id.BTN_create);
    }
}