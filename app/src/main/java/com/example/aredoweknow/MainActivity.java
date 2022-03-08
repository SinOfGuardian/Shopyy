package com.example.aredoweknow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.aredoweknow.databases_folder.Database;

public class MainActivity extends AppCompatActivity {
    private Animation bounce;
    private AppCompatImageView logo;

    private Database db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logo = findViewById(R.id.logo);
        bounce = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce_anim);

        db = new Database(this);

        //-------------This is the Bounce Animation------------
        Handler handler = new Handler();
        handler.postAtTime(new Runnable() {
            @Override
            public void run() {
                logo.startAnimation(bounce);
                SharedPreferences sf = getSharedPreferences("Shopyy", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sf.edit();
                editor.putBoolean("refresh", true);
                editor.apply();
            }
        }, 100);
        //------------------------------------------------------

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                Intent activity = new Intent(MainActivity.this, AddItem.class);

                String cred[] = db.getCredentials();

                if (cred == null || cred[0].equals("") || cred[1].equals("")) {
                    Intent login = new Intent(MainActivity.this, Login.class);
                    startActivity((Intent) login);              // --> Start Login Activity
                }else {
                    Intent dashboard = new Intent(MainActivity.this, DashboardNew.class);
                    dashboard.putExtra("username", cred[0]);
                    dashboard.putExtra("store", cred[1]);
                    startActivity((Intent) dashboard);       // --> Start Dashboard Activity
                }

                finish();                                   // --> prevent user from coming back to this activity
            }
        }, 2000);

    }
}