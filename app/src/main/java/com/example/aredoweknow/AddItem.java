package com.example.aredoweknow;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddItem extends AppCompatActivity {
    AppCompatImageButton ADDBTN;
        ImageView imageView;
        Button cameraBTN, GalleryBTN, backbtn;
        ImageButton scanBTN;
        public static EditText resulttextview;
  
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
      
        ADDBTN = findViewById(R.id.add_back_btn);
        resulttextview = findViewById(R.id.barcode_val);
        
      
       backbtn = findViewById(R.id.add_back_btn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
      
        ADDBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddItem.this, DashboardNew.class);
                startActivity(intent);
            }
        });
      
        //--------------CAMERA CODE
        imageView = findViewById(R.id.image_val);
        cameraBTN = findViewById(R.id.camera_btn);

        if (ContextCompat.checkSelfPermission(AddItem.this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(AddItem.this, new String[]{Manifest.permission.CAMERA}, 100);
        }

        cameraBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 100);
            }
        });


        //-----------------Open gallery
        GalleryBTN = findViewById(R.id.gallery_btn);
        GalleryBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 3);
            }
        });
//----------Open Scanner
        scanBTN = findViewById(R.id.barscan_btn);
        scanBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddItem.this, Scanner.class);
                startActivity(intent);
            }
        });
    }


//-------------capture camera code And pick picture in Gallery
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            //Capture Image
            Bitmap captureImage = (Bitmap) data.getExtras().get("data");
            //set capture image to image view
            imageView.setImageBitmap(captureImage);
        }
        if(resultCode == RESULT_OK && data != null){
            Uri selectedImage = data.getData();
            ImageView imageView = findViewById(R.id.image_val);
            imageView.setImageURI(selectedImage);
        }

    }
}