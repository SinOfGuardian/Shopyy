package com.example.aredoweknow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        -----------------This sets the Title to Gradient Color-----------------
        TextView title = findViewById(R.id.Title);
        title.setTextColor(getResources().getColor(R.color.gr1_end));
        Shader textShader = new LinearGradient(0, 0, title.getPaint().measureText(title.getText().toString()), title.getTextSize(),
                new int[]{getResources().getColor(R.color.gr1_end),
                        getResources().getColor(R.color.gr1_start)},
                null, Shader.TileMode.MIRROR);
        title.getPaint().setShader(textShader); //--> paint to gradient
//        ------------------------------------------------------------------------

    }


    public void signupBtn_clicked(View view) {
        startActivity(new Intent(this,Register.class));
    }
    public  void loginBtn_clicked(View view){
        finish();
        startActivity(new Intent(this, dashboard.class));
    }
}