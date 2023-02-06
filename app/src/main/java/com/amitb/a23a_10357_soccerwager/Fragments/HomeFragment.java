package com.amitb.a23a_10357_soccerwager.Fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        mAuth = FirebaseAuth.getInstance();
        //mDatabase = FirebaseDatabase.getInstansce().getReference();
        findViews();
        greetings.setText("Hello, " + mAuth.getCurrentUser().getDisplayName());
//        User user = new User(mDatabase.child("users").child(mAuth.getCurrentUser().getDisplayName()).toString(),mDatabase.child("users").child(mAuth.getCurrentUser().getEmail()).toString());
//        points.setText(user.getScore());
        return view;
    }

    private void findViews() {
        greetings = view.findViewById(R.id.greetings_MSG);
        points = view.findViewById(R.id.points_TXT);
    }
}