package com.example.aredoweknow.features_functions;

import android.os.Bundle;
import android.Manifest;
import android.os.Handler;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import com.example.aredoweknow.R;

public class History extends AppCompatActivity {
    private AppCompatImageButton back_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

    }
}
