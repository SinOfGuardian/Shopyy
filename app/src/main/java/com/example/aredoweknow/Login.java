package com.example.aredoweknow;

import androidx.appcompat.app.AppCompatActivity;

//Jerreme Imports
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
//Jerreme Imports//


public class Login extends AppCompatActivity {
    private boolean pass_isHidden = true;

    EditText pass_field; ImageButton eye_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        pass_field = findViewById(R.id.password);
        eye_button = findViewById(R.id.eyeButton);

    }

    //-------------This sets the Password Field view to either to SHOW password or NOT-------------
    public void eye_clicked(View view) {
        if (pass_isHidden) { // --> Show Password
            pass_field.setInputType(InputType.TYPE_CLASS_TEXT);
            pass_field.setSelection(pass_field.getText().length());
            pass_field.setTypeface(pass_field.getTypeface(), Typeface.BOLD);
            eye_button.setImageResource(R.drawable.eye_28px);
            pass_isHidden = false;
        } else { // --> Hide Password
            pass_field.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            pass_field.setSelection(pass_field.getText().length());
            pass_field.setTypeface(pass_field.getTypeface(), Typeface.BOLD);
            eye_button.setImageResource(R.drawable.hide_28px);
            pass_isHidden = true;
        }
    } //-----------------------------------------------------------------------------------------

    public void signupBtn_clicked(View view) {
        startActivity(new Intent(this, Register.class));
    }

    public void loginBtn_clicked(View view) {
        finish();
        startActivity(new Intent(this, dashboard.class));
    }
}