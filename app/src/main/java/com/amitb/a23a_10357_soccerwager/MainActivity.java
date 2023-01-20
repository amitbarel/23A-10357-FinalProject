package com.amitb.a23a_10357_soccerwager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import com.amitb.a23a_10357_soccerwager.Fragments.HomeFragment;
import com.amitb.a23a_10357_soccerwager.Fragments.GamesFragment;
import com.amitb.a23a_10357_soccerwager.Fragments.LeaguesFragment;
import com.amitb.a23a_10357_soccerwager.Fragments.ProfileFragment;
import com.amitb.a23a_10357_soccerwager.Interfaces.CallBack_logout;
import com.firebase.ui.auth.AuthUI;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private BottomNavigationView bottomNav;

    CallBack_logout callBack_logout = () -> goBackToLogin();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        findViews();
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragments_frame,new HomeFragment()).commit();
    }


    private void goBackToLogin() {
        AuthUI.getInstance().signOut(this);
        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivity(loginIntent);
        finish();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = item -> {
        Fragment selectedFragment = null;
        switch (item.getItemId()){
            case R.id.nav_home:
                selectedFragment = new HomeFragment();
                break;
            case R.id.nav_games:
                selectedFragment = new GamesFragment();
                break;
            case R.id.nav_leagues:
                selectedFragment = new LeaguesFragment();
                break;
            case R.id.nav_profile:
                selectedFragment = new ProfileFragment();
                ((ProfileFragment) selectedFragment).setCallBack(callBack_logout);
                break;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragments_frame,selectedFragment).commit();
        return true;
    };

    private void findViews() {
        bottomNav = findViewById(R.id.bottom_navi);
    }
}