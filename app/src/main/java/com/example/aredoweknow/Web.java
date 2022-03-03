package com.example.aredoweknow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Web extends AppCompatActivity {
    String customSearch;

    private WebView webview;
    private AppCompatImageButton back_btn;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        customSearch = getIntent().getStringExtra("ToSearch");

        webview = findViewById(R.id.webview1);
        back_btn = findViewById(R.id.web_back_btn);


        webview.setWebViewClient(new WebViewClient());
//        webview.loadUrl("https://www.google.com/");

        webview.getSettings().setLoadsImagesAutomatically(true);
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);

        if(!customSearch.equals("")) {
            webview.loadUrl("https://www.google.com/search?q="+customSearch);
        }else {
            webview.loadUrl("https://www.google.com/");
        }

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Execute in Background Process
        Handler handler = new Handler();
        handler.postAtTime(new Runnable() {
            @Override
            public void run() {
                toggleWifi(true);
            }
        }, 100);
    }


    @Override
    public void onBackPressed() {
        if (webview.canGoBack()) {
            webview.goBack();
        } else {
            super.onBackPressed();
        }
    }

    //---------------------Turn On Wifi Automatically if Support Enabling Wifi-----------------------
    private void toggleWifi(boolean state) {
        WifiManager wifiManager = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        }

        if (wifiManager != null) {
            if (state && !wifiManager.isWifiEnabled()) {
                wifiManager.setWifiEnabled(state);
            } else if (!state && wifiManager.isWifiEnabled()) {
                wifiManager.setWifiEnabled(state);
            }
        }
    }
}