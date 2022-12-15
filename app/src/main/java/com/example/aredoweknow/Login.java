package com.example.aredoweknow;

import androidx.appcompat.app.AppCompatActivity;

//Jerreme Imports
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.aredoweknow.databases_folder.Database;
import com.example.aredoweknow.other_class.dialogClass;

import java.util.Locale;
//Jerreme Imports//


public class Login extends AppCompatActivity {
    private boolean pass_isHidden = true;
    private static final long START_TIME_IN_MILLIS = 60000;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    private CountDownTimer mCountDownTimer;

    private boolean mTimerRunning;

    Database db;

    EditText user_field, pass_field;
    ImageButton eye_button;
    int attemp = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        user_field = findViewById(R.id.username);
        pass_field = findViewById(R.id.password);
        eye_button = findViewById(R.id.eyeButton);

        db = new Database(this);

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

        user_field.setText("");
        pass_field.setText("");
        user_field.setSelected(false);
        pass_field.setSelected(false);
    }

    public void loginBtn_clicked(View view) {

        String userNMLOGIN = user_field.getText().toString();
        String passLOGIN = pass_field.getText().toString();


        //Login SQL Function Starts Here
        if(attemp<5){
            if (!isFieldsEmpty()) {
                if (usernameLogIn(userNMLOGIN) && passwordLogIn(passLOGIN)) {
                String storeCred = db.ifStoreExist(userNMLOGIN);

                SharedPreferences sf = getSharedPreferences("Shopyy", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sf.edit();
                editor.putString("final_username", userNMLOGIN);
                editor.apply();

                    if (!storeCred.equals("")) {
                        Intent intent = new Intent(this, DashboardNew.class);
                        intent.putExtra("username", userNMLOGIN);
                        intent.putExtra("store", storeCred);
                        startActivity(intent);
                    }else{
                        Intent intent = new Intent(getApplicationContext(),StoreName.class);
                        intent.putExtra("username", userNMLOGIN);
                        startActivity( intent);
                    }
                    finish();

                }
            }else if(attemp==4){
                Toast.makeText(getApplicationContext(), "login limit exceed", Toast.LENGTH_SHORT).show();
                startTimer();
                //System.exit(0);
            }
//            else if(attemp>5){
//                updateCountDownText();
//                System.exit(0);
//            }
//             }
            attemp++;
        }


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
            display_messageDialog("One or more field/s is Empty! And No. Of Attempt is " + attemp);
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
    private boolean usernameLogIn(String username) {
        boolean isExist = true;
        isExist = db.ifUsernameExist(username);

        if(!isExist) {
            user_field.setSelected(true);
            pass_field.setSelected(true);
            display_messageDialog("Incorrect Username! And No. Of Attempt is " + attemp);
        }
        return isExist;
    }
    private boolean passwordLogIn(String password) {
        boolean isExist = true;
        isExist =db.ifPasswordExist(password) ;

        if(!isExist) {
            pass_field.setSelected(true);
            display_messageDialog("Incorrect Password! And No. Of Attempt is " + attemp);
        }
        return isExist;
    }

    //-------------------------------countdown

    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                    updateCountDownText();
//                if (!mCountDownTimer){
//                    System.exit(0);
//                }


            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                System.exit(0);
//                mButtonStartPause.setText("Start");
//                mButtonStartPause.setVisibility(View.INVISIBLE);
//                mButtonReset.setVisibility(View.VISIBLE);
            }
        }.start();

        mTimerRunning = true;

//        mButtonStartPause.setText("pause");
//        mButtonReset.setVisibility(View.INVISIBLE);
    }
    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        display_messageDialog("Try Again In " + timeLeftFormatted);


//        //mTextViewCountDown.setText(timeLeftFormatted);
    }
    private void resetTimer() {
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
//        mButtonReset.setVisibility(View.INVISIBLE);
//        mButtonStartPause.setVisibility(View.VISIBLE);
    }
}