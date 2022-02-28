package com.example.aredoweknow;

import androidx.appcompat.app.AppCompatActivity;

//Jerreme Imports
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
//Jerreme Imports//


public class Login extends AppCompatActivity {
    private boolean pass_isHidden = true;

    Database db;

    EditText user_field, pass_field; ImageButton eye_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        user_field = findViewById(R.id.username);
        pass_field = findViewById(R.id.password);
        eye_button = findViewById(R.id.eyeButton);


        user_field.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (user_field.isFocused()) {
                    user_field.setSelected(false); //--> Remove Highlight red
                }
            }
        });

        pass_field.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (pass_field.isFocused()) {
                    pass_field.setSelected(false); //--> Remove Highlight red
                }
            }
        });
    }

    public void signupBtn_clicked(View view) {
        startActivity(new Intent(this, Register.class));
    }

    public void loginBtn_clicked(View view) {
        String userNMLOGIN = user_field.getText().toString();
        String passLOGIN = pass_field.getText().toString();


            //Login SQL Function Starts Here

            finish();
            startActivity(new Intent(this, StoreName.class));

    }

    //---------------------This checks if one or more fields are empty--------------------------
    private boolean isFieldsEmpty() {
        String user = user_field.getText().toString().trim();
        String pass = pass_field.getText().toString();

        boolean isEmpty = false;
        user_field.setText(user); //--> Remove leading and trailing spaces

        if (user.equals("")) {
            isEmpty = true;
            user_field.setSelected(true); //--> Highlight red
        }

        if (pass.equals("")) {
            isEmpty = true;
            pass_field.setSelected(true); //--> Highlight red
        }

        if (isEmpty) {
            Dialog dialog1 = new Dialog(this);
            dialogClass dialog = new dialogClass();
            eye_button.setFocusableInTouchMode(true);
            eye_button.requestFocus();
            dialog.simpleDialog(dialog1, "Fields is Empty"); //--> show simple dialog
        }
        return isEmpty;
    } //-----------------------------------------------------------------------------------------
    private void display_messageDialog(String message) {
        Dialog dialog1 = new Dialog(this);
        dialogClass dialog = new dialogClass();

        dialog.simpleDialog(dialog1, message); //--> show simple dialog
    }
    //-------------This sets the Password Field view to either to SHOW password or NOT-------------
    public void eye_clicked(View view) {
        if (pass_isHidden) { // --> Show Password
            pass_field.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
            pass_field.setSelection(pass_field.getText().length());
            pass_field.setTypeface(pass_field.getTypeface(), Typeface.BOLD);
            eye_button.setImageResource(R.drawable.eye_28px);
            pass_isHidden = false;
        } else { // --> Hide Password
            pass_field.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
            pass_field.setSelection(pass_field.getText().length());
            pass_field.setTypeface(pass_field.getTypeface(), Typeface.BOLD);
            eye_button.setImageResource(R.drawable.hide_28px);
            pass_isHidden = true;
        }
    } //-----------------------------------------------------------------------------------------


}