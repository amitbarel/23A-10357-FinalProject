package com.amitb.a23a_10357_soccerwager.Fragments;

import android.os.Bundle;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.amitb.a23a_10357_soccerwager.Interfaces.CallBack_logout;
import com.amitb.a23a_10357_soccerwager.R;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileFragment extends Fragment {

    private FirebaseAuth mAuth;
    private View view;
    private TextView name,email;
    private AppCompatButton logout;
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
        logout.setOnClickListener(v->BtnClicked());
        return view;
    }

    private void BtnClicked() {
        if (callBack != null)
            callBack.logout();
    }


    private void findViews() {
        name = view.findViewById(R.id.TXT_name);
        email = view.findViewById(R.id.TXT_email);
        logout = view.findViewById(R.id.logout_BTN);
    }
}