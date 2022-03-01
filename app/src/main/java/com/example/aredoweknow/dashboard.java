package com.example.aredoweknow;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class dashboard extends AppCompatActivity {
    FloatingActionButton browser_btn;
    FloatingActionButton scan_btn;
    FloatingActionButton add_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        resulttextview = findViewById(R.id.dashboard);

        //Open Web View
        browser_btn = findViewById(R.id.browser_button);
        browser_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(dashboard.this, Web.class);
                startActivity(intent);
            }
        });
        //Open camera for scan
        scan_btn = findViewById(R.id.scan_button);
        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(dashboard.this, Scanner.class);
                startActivity(intent);
            }
        });
        add_btn = findViewById(R.id.add_button);
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(dashboard.this, Scanner.class);
                startActivity(intent);
            }
        });


    }

}
