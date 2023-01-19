package com.amitb.a23a_10357_soccerwager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.amitb.a23a_10357_soccerwager.Fragments.LeaguesFragment;
import com.amitb.a23a_10357_soccerwager.Fragments.LineupFragment;
import com.amitb.a23a_10357_soccerwager.Fragments.ProfileFragment;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.auth.api.Auth;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private MaterialButton games,leagues,profile, logout;
    private TextView greetings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        findViews();
        games.setOnClickListener(v-> replaceFragment(new LineupFragment()));
        leagues.setOnClickListener(v-> replaceFragment(new LeaguesFragment()));
        profile.setOnClickListener(v-> replaceFragment(new ProfileFragment()));
        logout.setOnClickListener(v-> {
            AuthUI.getInstance().signOut(this);
            goBackToLogin();
        });
        greetings.setText("Hello, " + mAuth.getCurrentUser().getDisplayName());
    }

    private void goBackToLogin() {
        Intent loginIntent = new Intent(this,LoginActivity.class);
        startActivity(loginIntent);
        finish();
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragments_frame,fragment);
        fragmentTransaction.commit();
    }

    private void findViews() {
        games = findViewById(R.id.BTN_games);
        leagues = findViewById(R.id.BTN_leagues);
        profile = findViewById(R.id.BTN_profile);
        logout = findViewById(R.id.logout_BTN);
        greetings = findViewById(R.id.greetings_MSG);
    }
}