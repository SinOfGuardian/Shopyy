package com.example.aredoweknow;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void signin2Btn_clicked(View view) {
        finish();
    }
    public  void login2Btn_clicked(View view){
        finish();
    }

}