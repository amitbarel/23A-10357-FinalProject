package com.amitb.a23a_10357_soccerwager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import com.amitb.a23a_10357_soccerwager.Utils.User;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
// ...


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null){
            login();
        }else{
            String uid = user.getUid();
            String phone = user.getPhoneNumber();
            String name = user.getDisplayName();
            String email = user.getEmail();
            int x= 0;
            goToMain();
        }

    }

    private void goToMain() {
        Intent mainIntent = new Intent(this,MainActivity.class);
        startActivity(mainIntent);
        finish();
    }

    // See: https://developer.android.com/training/basics/intents/result
    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            result -> onSignInResult(result)
    );

    private void onSignInResult(FirebaseAuthUIAuthenticationResult result) {
        Toast.makeText(this, "Hello " + mAuth.getCurrentUser().getDisplayName(), Toast.LENGTH_SHORT).show();
        User user = new User(mAuth.getCurrentUser().getDisplayName(),mAuth.getCurrentUser().getEmail());
        mDatabase.child("users").child(mAuth.getCurrentUser().getUid()).setValue(user);
        goToMain();
    }



    private void login() {
        // Choose authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.PhoneBuilder().build());

        // Create and launch sign-in intent
        Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setTheme(R.style.AppThemeFirebaseAuth)
                .setLogo(R.drawable.applogo)
                .build();
        signInLauncher.launch(signInIntent);
    }
}