package com.example.aredoweknow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MainActivity extends AppCompatActivity {
    private Animation bounce;
    private AppCompatImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logo = findViewById(R.id.logo);
        bounce = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.boucne_anim);

        //-------------This is the Bounce Animation------------
        Handler handler = new Handler();
        handler.postAtTime(new Runnable() {
            @Override
            public void run() {
                logo.startAnimation(bounce);
            }
        }, 100);
        //------------------------------------------------------

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                Intent activity = new Intent(MainActivity.this, Login.class);
                Intent activity = new Intent(MainActivity.this, dashboard.class);
                startActivity((Intent) activity); // --> Start Login Activity
                finish(); // --> prevent user from coming back to this activity
            }
        }, 2000);

    }
}