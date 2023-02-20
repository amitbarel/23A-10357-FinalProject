package com.amitb.a23a_10357_soccerwager.Fragments;

import android.os.Bundle;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amitb.a23a_10357_soccerwager.Interfaces.CallBack_logout;
import com.amitb.a23a_10357_soccerwager.Interfaces.OnGetDataListener;
import com.amitb.a23a_10357_soccerwager.R;
import com.amitb.a23a_10357_soccerwager.Utils.DataManager;
import com.amitb.a23a_10357_soccerwager.Utils.Fixture;
import com.amitb.a23a_10357_soccerwager.Utils.Guess;
import com.amitb.a23a_10357_soccerwager.Utils.Match;
import com.amitb.a23a_10357_soccerwager.Utils.Score;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class ProfileFragment extends Fragment {

    private FirebaseAuth mAuth;
    private LinearLayout genFrame;
    private View view;
    private TextView name,email;
    private AppCompatButton logout,generateNewFixture;
    private CallBack_logout callBack;

    public void setCallBack(CallBack_logout callBack){
        this.callBack = callBack;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        mAuth = FirebaseAuth.getInstance();
        findViews();
        name.setText(mAuth.getCurrentUser().getDisplayName());
        email.setText(mAuth.getCurrentUser().getEmail());
        String uid = mAuth.getCurrentUser().getUid();
        Log.d("onCreateView: ",uid);
        logout.setOnClickListener(v->btnClicked());
        DataManager.readData(FirebaseDatabase.getInstance().getReference("admin"), new OnGetDataListener() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                if (DataManager.isAdmin(dataSnapshot,uid)){
                    genFrame.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onStart() {
                Log.d("onStart: ","started");
            }

            @Override
            public void onFailure() {

            }
        });
        generateNewFixture.setOnClickListener(v->generateFixture());
        return view;
    }

    private void generateFixture() {
        DataManager.readData(FirebaseDatabase.getInstance().getReference("fixture"), new OnGetDataListener() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                //what to do if finished
                DataManager.loadFixture(dataSnapshot);
                Fixture fixture = DataManager.getFixture();
                Log.d("onSuccess: ",fixture.toString());
                //DataManager.handleScores();
                ArrayList<Match> realScores = fixture.getMatches();
                ArrayList<Guess> guesses = fixture.getGuesses();
                HashMap<String,Integer> uidAndPoints = new HashMap<>();
                for(Guess guess: guesses){
                    String uid = guess.getUser();
                    int points = DataManager.calcPtsFromGuess(guess);
                    Log.d("onSuccess: ",points+"");
                    uidAndPoints.put(uid,points);
                }
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFailure() {

            }
        });
        //Log.d("onSuccess",scores.toString());
//        if (DataManager.getFixture() == null || DataManager.getFixture().getGuesses() == null){
//            DataManager.createFixture();
//            Log.d("onSuccess","if");
//        }
//        else{
////            DataManager.fillFixture(); // Give scores to the current fixture
//            DataManager.givePoints(); // Give points
//            DataManager.createFixture();
//            Log.d("onSuccess","else");
//        }
    }

    private void btnClicked() {
        if (callBack != null)
            callBack.logout();
    }


    private void findViews() {
        name = view.findViewById(R.id.TXT_name);
        email = view.findViewById(R.id.TXT_email);
        logout = view.findViewById(R.id.logout_BTN);
        genFrame = view.findViewById(R.id.frame_generate);
        generateNewFixture = view.findViewById(R.id.BTN_generate);
    }
}