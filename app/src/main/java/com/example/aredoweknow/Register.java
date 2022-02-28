package com.example.aredoweknow;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ImageButton;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.Statement;

import java.sql.SQLException;

public class Register extends AppCompatActivity {

    EditText usern, passwd, cpasswd;
    Button register;
    ConnectionMYSQL ConnectionMYSQL;
    ProgressDialog progressDialog;

    //Jerreme Objects and Variables
    ImageButton eye1, eye2;
    private boolean pass_isHidden1 = true, pass_isHidden2 = true;
    //Jerreme Objects and Variables//

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

        ConnectionMYSQL = new ConnectionMYSQL();
        progressDialog = new ProgressDialog(this);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = usern.getText().toString().trim();
                String pass = passwd.getText().toString();
                String cpass = cpasswd.getText().toString();

                if (!isFieldsEmpty(user, pass, cpass) && isPasswordLength6(pass) && isPasswordMatch(pass, cpass)) {
                    RegisterACTION registeract = new RegisterACTION();
                    registeract.execute("");
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

    @SuppressWarnings("deprecation")
    @SuppressLint({"NewApi", "StaticFieldLeak"})
    protected class RegisterACTION extends AsyncTask< String, String, String > {

        ConnectionMYSQL connectionMYSQL = new ConnectionMYSQL();

        String usernstr = usern.getText().toString();
        String passwd1 = passwd.getText().toString();
//        String passwd2 = cpasswd.getText().toString();
        String i = "";
        boolean isSuccess = false;


        @Override
        protected void onPreExecute() {

            progressDialog.setMessage("Loading...");
            progressDialog.show();


        }

        @Override
        public String doInBackground(String... params) {

            if (usernstr.trim().equals("") || passwd1.trim().equals("")) {
                System.out.println("Fill in the blank");
                i = "Fill in the blank...";
            }else{
                    try {
                        Connection conn = connectionMYSQL.CONNECT();
                        if (conn == null) {
                            i = "Please check your internet Connection ";
                        } else {
                          
                            String query = "INSERT INTO account (userName,passWD) VALUES ('" + usernstr + "', '" + passwd1 + "')";
                          
                            Statement stmt = conn.createStatement();
                            stmt.execute(query);

                            i = "Register Successful Please Proceed To Log in";
                            isSuccess = true;
                        }

                    } catch (SQLException ex) {
                        isSuccess = false;
                        i = "Exceptions" + ex;

                    }
                }
                return i;

            }


        @Override
        protected void onPostExecute(String s) {
            progressDialog.hide();

            if (isSuccess) {
                Toast.makeText(getBaseContext(), "" + i, Toast.LENGTH_LONG).show();
                signin2Btn_clicked(null);
            }
            System.out.println(i);
        }
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
    } //-----------------------------------------------------------------------------------------
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
    public void reg_eye_clicked(View view) {
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
    } //-----------------------------------------------------------------------------------------


}

