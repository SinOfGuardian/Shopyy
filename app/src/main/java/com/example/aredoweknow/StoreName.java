package com.example.aredoweknow;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class StoreName extends AppCompatActivity {
    EditText textField;

    public String username = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_name);

        textField = findViewById(R.id.storeNameField);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        textField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (textField.isFocused()) {
                    textField.setSelected(false);
                }
            }
        });
    }

    //------------------Finish Function & Prompt Dialog for empty input-------------------
    public void finistBtn_clicked(View view) {
        if (!textField.getText().toString().trim().equals("")) {
            Database db = new Database(this);
            db.updateStore(username, textField.getText().toString());

            finish();
            startActivity(new Intent(this, DashboardNew.class));
        }else {
            Dialog dialog1 = new Dialog(this);
            dialogClass dialog = new dialogClass();

            textField.setSelected(true);
            Button b = findViewById(R.id.finishBtn);
            b.setFocusableInTouchMode(true);
            b.requestFocus();
            dialog.simpleDialog(dialog1, "Store Name must be provide!"); //--> show simple dialog
        }
    }// ----------------------------------------------------------------------------------

}