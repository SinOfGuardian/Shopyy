package com.example.aredoweknow.features_functions;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
public class Scanner extends AppCompatActivity implements ZXingScannerView.ResultHandler{
    String whom_toUpdate;


    int MY_PERMISSIONS_REQUEST_CAMERA=0;
    ZXingScannerView scannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);

        Intent intent = getIntent();
        whom_toUpdate = intent.getStringExtra("update");
    }

    //--> RESULT
    @Override
    public void handleResult(Result result) {
//        onBackPressed();

        switch (whom_toUpdate) {
            case "searching_item":
                Intent intent = new Intent(this, Web.class);
                intent.putExtra("ToSearch", result.getText());
                startActivity(intent);
                break;
            case "adding_item":
                AddItem.resulttextview.setText(result.getText());
                break;
            case "viewing_item":
                VieweditActivity.static_barcode.setText(result.getText());
                break;
        }

        finish();
     }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        scannerView.setResultHandler(this);
//        scannerView.startCamera();
//    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    MY_PERMISSIONS_REQUEST_CAMERA);
        }
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }
}
