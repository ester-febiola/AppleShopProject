package com.ester.appleshop.activities;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.ester.appleshop.R;

import java.util.Objects;

public class SplashScreenActivity extends AppCompatActivity {
    private int loading=3000;

    //3000 = 3 detik

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();


        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                //setelah loading maka akan langsung berpindah ke home activity
                Intent home=new Intent(SplashScreenActivity.this, HomeActivity.class);
                startActivity(home);
                finish();

            }
        },loading);
    }
}