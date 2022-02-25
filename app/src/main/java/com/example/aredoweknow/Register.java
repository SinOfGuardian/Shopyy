package com.example.aredoweknow;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.Statement;

public class Register extends AppCompatActivity {

    EditText usern, passwd, cpasswd;
    Button register;
    ConnectionMYSQL ConnectionMYSQL;
     ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usern = findViewById(R.id.username);
        passwd = findViewById(R.id.password1);
        cpasswd = findViewById(R.id.password2);
        register = findViewById(R.id.Registered);

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

     public class RegisterACTION extends AsyncTask<String,String,String> {

        ConnectionMYSQL   connectionMYSQL = new ConnectionMYSQL();

        String usernstr = usern.getText().toString();
        String passwd1 = passwd.getText().toString();
        String passwd2 = cpasswd.getText().toString();
        String i = "";
        boolean isSuccess = false;

        @Override
        protected  void onPreExecute(){
            progressDialog.setMessage("Loading...");
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {

            if (usernstr.trim().equals("") || passwd1.trim().equals("") || passwd2.trim().equals(""))
                i = "Fill in the blank...";
             else {
                try {
                    Connection conn = connectionMYSQL.CONNECT();
                        if (conn == null) {
                            i = "Please check your internet Connection ";
                        } else {
                            String query = "INSERT INTO user VALUES ('" + usernstr + "', '" + passwd1 + "', '" + passwd2 + "')";

                            Statement stmt = conn.createStatement();
                                stmt.executeUpdate(query);

                            i = "Register Successful Please Proceed To Log in";
                            isSuccess = true;
                        }

                } catch (Exception ex) {
                    isSuccess = false;
                    i = "Exceptions" + ex;

                }
            }
            return i;

        }
        @Override
        protected  void onPostExecute(String s){

            if(isSuccess){
                Toast.makeText(getBaseContext(),""+i,Toast.LENGTH_LONG).show();
            }
            progressDialog.hide();
        }
    }

        public void signin2Btn_clicked(View view) {
            finish();
        }

}

