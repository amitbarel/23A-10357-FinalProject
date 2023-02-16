package com.amitb.a23a_10357_soccerwager.Fragments;

import android.os.Bundle;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.amitb.a23a_10357_soccerwager.Interfaces.CallBack_logout;
import com.amitb.a23a_10357_soccerwager.R;
import com.amitb.a23a_10357_soccerwager.Utils.DataManager;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileFragment extends Fragment {

    private FirebaseAuth mAuth;
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
        Toast.makeText(getContext(),mAuth.getCurrentUser().getUid(),Toast.LENGTH_SHORT);
        findViews();
        name.setText(mAuth.getCurrentUser().getDisplayName());
        email.setText(mAuth.getCurrentUser().getEmail());
        logout.setOnClickListener(v->btnClicked());
        generateNewFixture.setOnClickListener(v->generateFixture());
        return view;
    }

    private void generateFixture() {
        DataManager.createFixture();

//        if (mAuth.getCurrentUser().getUid().equals(DataManager.getAdminId())){
//            DataManager.fillFixture(); // Give scores to the current fixture
//            DataManager.givePoints(); // Give points
//            DataManager.createFixture();
//            Toast.makeText(getContext(),"Fixture updated",Toast.LENGTH_SHORT);
//        }
//        else{
//            Toast.makeText(getContext(),"You are not the admin!",Toast.LENGTH_SHORT);
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
        generateNewFixture = view.findViewById(R.id.BTN_generate);
    }
}