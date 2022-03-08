package com.example.aredoweknow;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.aredoweknow.databases_folder.Database;
import com.example.aredoweknow.other_class.dialogClass;

public class StoreName extends AppCompatActivity {
    EditText textField;

    private String username = "";
    private String store = "";

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
        store = textField.getText().toString().trim();

        if (!store.equals("")) {
            Database db = new Database(this);
            db.updateStore(username, textField.getText().toString());

            finish();

            Intent intent = new Intent(this, DashboardNew.class);
            intent.putExtra("username", username);
            intent.putExtra("store", store);
            startActivity(intent);
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