package com.example.aredoweknow;

import androidx.appcompat.app.AppCompatActivity;


import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ImageButton;

import android.app.ProgressDialog;

import android.widget.Button;
import android.widget.EditText;


public class Register extends AppCompatActivity {
    Database db;

    EditText usern, passwd, cpasswd;
    Button register;

    ProgressDialog progressDialog;


    ImageButton eye1, eye2;
    private boolean pass_isHidden1 = true, pass_isHidden2 = true;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        usern = findViewById(R.id.username);
        passwd = findViewById(R.id.password1);
        cpasswd = findViewById(R.id.password2);
        register = findViewById(R.id.Registered);


        eye1 = findViewById(R.id.eyeButton1);
        eye2 = findViewById(R.id.eyeButton2);


        progressDialog = new ProgressDialog(this);
        db = new Database(this);

        usern.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                if (!hasFocus) {
                    String user = usern.getText().toString();
//                    if (isUSernameExists(user)) { }
                }
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = usern.getText().toString();
                String pass = passwd.getText().toString();
                String cpass = cpasswd.getText().toString();
                String storename = "";

                if (!isFieldsEmpty(user, pass, cpass) && !isUsernameExist(user) && isPasswordLength6(pass) && isPasswordMatch(pass, cpass)) {
                    //TODO BUTTON REGISTER
                    if ( db.addaccount(user,pass,storename) > 1) {
                        display_messageDialog("Registration Successfull :)");
                        finish();
                    }else{
                        display_messageDialog("Registration Failed");
                    }

                }
            }
        });


        //------------------------------Reset Fields---------------------------------
        usern.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (usern.isFocused()) {
                    usern.setSelected(false); //--> Remove Highlight red
                }
            }
        });

        passwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (passwd.isFocused()) {
                    passwd.setSelected(false); //--> Remove Highlight red
                }
            }
        });

        cpasswd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (cpasswd.isFocused()) {
                    cpasswd.setSelected(false); //--> Remove Highlight red
                }
            }
        });
    }





    public void signin2Btn_clicked(View view) {
        finish();
    }

    //---------------------This checks if one or more fields are empty--------------------------
    private boolean isFieldsEmpty(String user, String pass, String cpass) {
        boolean isEmpty = false;
        usern.setText(user); //--> Remove leading and trailing spaces

        if (user.equals("")) {
            isEmpty = true;
            usern.setSelected(true); //--> Highlight red
        }

        if (pass.equals("")) {
            isEmpty = true;
            passwd.setSelected(true); //--> Highlight red
        }

        if (cpass.equals("")) {
            isEmpty = true;
            cpasswd.setSelected(true); //--> Highlight red
        }

        if (isEmpty) {
            display_messageDialog("One or more fields is Empty");
        }

        return isEmpty;
    }
    //---------------------This checks if password length is 6 characters------------------------
    private boolean isPasswordLength6(String pass) {
        boolean isLength6;

        if (pass.length() >= 6) {
            isLength6 = true;
        }else {
            isLength6 = false;
            passwd.setSelected(true); //--> Highlight red
        }

        if (!isLength6) {
            display_messageDialog("Password must be 6 characters!");
        }
        return isLength6;
    }

    private boolean isPasswordMatch(String pass, String cpass) {
        boolean isMatch;

        if (!pass.equals(cpass)) {
            isMatch = false;
            cpasswd.setSelected(true);
        }else {
            isMatch = true;
        }

        if (!isMatch) {
            display_messageDialog("Password did not match!");
        }
        return isMatch;
    }
    private void display_messageDialog(String message) {
        Dialog dialog1 = new Dialog(this);
        dialogClass dialog = new dialogClass();
        eye1.setFocusableInTouchMode(true);
        eye1.requestFocus();
        dialog.simpleDialog(dialog1, message); //--> show simple dialog
    }
    //-------------This sets the Password Field view to either to SHOW password or NOT-------------
    private void reg_eye_clicked(View view) {
        ImageButton eye = (ImageButton) view;

        if (eye.equals(eye1)) {
            if (pass_isHidden1) { // --> Show Password
                passwd.setInputType(InputType.TYPE_CLASS_TEXT);
                passwd.setSelection(passwd.getText().length());
                passwd.setTypeface(passwd.getTypeface(), Typeface.BOLD);
                eye1.setImageResource(R.drawable.eye_28px);
                pass_isHidden1 = false;
            } else { // --> Hide Password
                passwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                passwd.setSelection(passwd.getText().length());
                passwd.setTypeface(passwd.getTypeface(), Typeface.BOLD);
                eye1.setImageResource(R.drawable.hide_28px);
                pass_isHidden1 = true;
            }
        }else if (eye.equals(eye2)) {
            if (pass_isHidden2) { // --> Show Password
                cpasswd.setInputType(InputType.TYPE_CLASS_TEXT);
                cpasswd.setSelection(cpasswd.getText().length());
                cpasswd.setTypeface(cpasswd.getTypeface(), Typeface.BOLD);
                eye2.setImageResource(R.drawable.eye_28px);
                pass_isHidden2 = false;
            } else { // --> Hide Password
                cpasswd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                cpasswd.setSelection(cpasswd.getText().length());
                cpasswd.setTypeface(cpasswd.getTypeface(), Typeface.BOLD);
                eye2.setImageResource(R.drawable.hide_28px);
                pass_isHidden2 = true;
            }
        }
    }
    //-----------------------------------------------------------------------------------------
    private boolean isUsernameExist(String username) {
        boolean isExist = false;

        if(db.ifUsernameExist(username)) {
            usern.isSelected();

            display_messageDialog("Username Already Exists!");
            isExist = true;
        }
        return isExist;
    }

}
