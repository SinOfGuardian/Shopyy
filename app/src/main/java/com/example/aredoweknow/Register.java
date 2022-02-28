package com.example.aredoweknow;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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
                RegisterACTION registeract = new RegisterACTION();
                registeract.execute("");
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
                            stmt.executeUpdate(query);

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

