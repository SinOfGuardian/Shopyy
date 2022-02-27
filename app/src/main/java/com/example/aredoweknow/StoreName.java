package com.example.aredoweknow;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class StoreName extends AppCompatActivity {
    EditText textField;
    Dialog dialog1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_name);

        textField = findViewById(R.id.storeNameField);
        dialog1 = new Dialog(this);
    }

    //------------------Finish Function & Prompt Dialog for empty input-------------------
    public void finistBtn_clicked(View view) {
        if (!textField.getText().toString().trim().equals("")) {

            finish();
            startActivity(new Intent(this, dashboard.class));
        }else {
            dialog1.setContentView(R.layout.dialog_alert);
            dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            TextView message = dialog1.findViewById(R.id.messageLabel);
            Button okayBtn = dialog1.findViewById(R.id.okayBtn);

            okayBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog1.dismiss();
                }
            });

            message.setText(R.string.message1);
            dialog1.show();
        }
    }// ----------------------------------------------------------------------------------

}