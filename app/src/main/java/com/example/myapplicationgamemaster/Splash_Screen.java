package com.example.myapplicationgamemaster;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplicationgamemaster.auth.LoginActivity;

public class Splash_Screen extends AppCompatActivity {

    private Intent Intent;
    private boolean isLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();

        //read from shared
         isLogin = getSharedPreferences("userShared",MODE_PRIVATE)
                .getBoolean("isLogin",false);


    }

    @Override
    protected void onStart() {
        super.onStart();
        new Handler().postDelayed(() -> {
            Intent intent;
            if (isLogin){
                 intent = new Intent(Splash_Screen.this, MainActivity.class);
                startActivity(intent);
                finish();

            } else {
                 intent = new Intent(Splash_Screen.this, LoginActivity.class);
                startActivity(intent);
                finish();

            }
        },3000);


    }
}