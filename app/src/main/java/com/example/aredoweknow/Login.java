package com.example.aredoweknow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void signupBtn_clicked(View view) {
        startActivity(new Intent(this,Register.class));
    }
    public  void loginBtn_clicked(View view){
        finish();
        startActivity(new Intent(this, dashboard.class));
    }
}