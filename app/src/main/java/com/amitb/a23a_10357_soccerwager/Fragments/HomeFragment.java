package com.amitb.a23a_10357_soccerwager.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amitb.a23a_10357_soccerwager.R;
import com.google.firebase.auth.FirebaseAuth;


public class HomeFragment extends Fragment {

    private FirebaseAuth mAuth;
    private View view;
    private TextView greetings;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        mAuth = FirebaseAuth.getInstance();
        greetings = view.findViewById(R.id.greetings_MSG);
        greetings.setText("Hello, " + mAuth.getCurrentUser().getDisplayName());
        return view;
    }
}