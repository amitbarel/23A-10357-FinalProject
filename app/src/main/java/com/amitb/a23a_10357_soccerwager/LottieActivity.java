package com.amitb.a23a_10357_soccerwager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;

public class LottieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottie);

        LottieAnimationView lottieAnimationView = findViewById(R.id.Lottie_ANIM);
        lottieAnimationView.resumeAnimation();
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);
            finish();
        },3000);
    }

}