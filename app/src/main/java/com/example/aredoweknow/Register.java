package com.example.aredoweknow;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


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

    public void signin2Btn_clicked(View view) {
        finish();
    }
    public  void login2Btn_clicked(View view){
        finish();
    }

}