package com.example.aredoweknow;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
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
import android.widget.Toast;
import android.widget.Toolbar;

public class VieweditActivity extends AppCompatActivity {
    private boolean editw = true;

    DatabaseHandler db;
    AppCompatImageButton backbtn;

    Button cameraBTN, galleryBTN, editBTN, saveBTN;
    Uri image_Uri;
    ImageButton scanBTN;

    DatabaseHandler dataHandler;
    EditText name_field, barcode_field, description_field, quantity_field, price_field;
    Bitmap captureImage;
    ImageView imageView;
    CardView cardView;
    Toolbar toolbar;

    Intent intent;
    String name;
    String price;
    String barcode;
    String description;
    String quantity;
    Bitmap image;

    @SuppressLint("StaticFieldLeak")
    public static EditText static_namefield;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewedit);

        //Database
        dataHandler = new DatabaseHandler(this);

        name_field = findViewById(R.id.itemname_val2);
        barcode_field = findViewById(R.id.barcode_val2);
        static_namefield = barcode_field; //--> To update by Scanner Class
        description_field = findViewById(R.id.description_val2);
        quantity_field = findViewById(R.id.quantity_val2);
        price_field = findViewById(R.id.price_val2);
        imageView = findViewById(R.id.image_val2);


        name_field.setShowSoftInputOnFocus(false);
        barcode_field.setShowSoftInputOnFocus(false);
        description_field.setShowSoftInputOnFocus(false);
        quantity_field.setShowSoftInputOnFocus(false);
        price_field.setShowSoftInputOnFocus(false);

        intent = getIntent();
        name = intent.getStringExtra("name");
        price = intent.getStringExtra("price");
        barcode = intent.getStringExtra("barcode");
        description = intent.getStringExtra("descr");
        quantity = intent.getStringExtra("quant");
        image = intent.getParcelableExtra("image");

        imageView.setImageBitmap(image);
        name_field.setText(name);
        barcode_field.setText(barcode);
        description_field.setText(description);
        quantity_field.setText(quantity);
        price_field.setText(price);

        //--------------BACK TO DASHBOARD--------------
        backbtn = findViewById(R.id.view_back_btn);
        backbtn.setOnClickListener(v -> finish());

        //--------------------Open gallery
        galleryBTN = findViewById(R.id.gallery_btn2);
        galleryBTN.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 3);
        });

        //-------------------Open Camera for Image
        cameraBTN = findViewById(R.id.camera_btn2);
        cameraBTN.setOnClickListener(v -> {
            if (CamPermissionGranted()) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 100);
            }
        });

        //-------------------Open Scanner
        scanBTN = findViewById(R.id.barscan_btn2);
        scanBTN.setOnClickListener(v -> {
            if (CamPermissionGranted()) {
                startActivityForResult(intent, 100);
                Intent intent = new Intent(VieweditActivity.this, Scanner.class);
                intent.putExtra("update", "viewing_item");
                startActivity(intent);
            }
        });

        editBTN = findViewById(R.id.edit_btn);
        editBTN.setOnClickListener(v -> editBTN_clicked(v));

        cameraBTN = findViewById(R.id.camera_btn2);
        cameraBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 1 wait ako na dito - ji
            }
        });

        galleryBTN = findViewById(R.id.gallery_btn2);
        galleryBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 2 wait ako na dito - ji
            }
        });

        scanBTN = findViewById(R.id.barscan_btn2);
        scanBTN.setOnClickListener(v -> {  //--> This is Lambda Format, this is just the same with regular runnable void onClick()
            Intent intent = new Intent(VieweditActivity.this, Scanner.class);
            intent.putExtra("update", "viewing_item");
            startActivity(intent);
        });

        saveBTN = findViewById(R.id.save_btn);
        saveBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO save function here
            }
        });
    }


    //------------------------------------------------CAMERA CODE
    private boolean CamPermissionGranted() {
        if (ContextCompat.checkSelfPermission(VieweditActivity.this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(VieweditActivity.this, new String[]{Manifest.permission.CAMERA}, 100);
            return false;
        } else {
            return true;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        Toast.makeText(this, requestCode + " | " + resultCode, Toast.LENGTH_SHORT).show();
//        System.out.println(requestCode + " | " + resultCode);

        if (data != null && resultCode == RESULT_OK) {
            System.out.println(requestCode + " | " + resultCode);
            try {
                if (requestCode == 3) {  //--> Choose from gallery
                    image_Uri = data.getData();
//                image_Bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), image_Uri);
//                imageView.setImageURI(selectedImage);
                    performCrop(); //--> Requires Cropping the image
                }

                if (requestCode == PIC_CROP || requestCode == 100) { //3
                    captureImage = data.getExtras().getParcelable("data");
//                image_Bitmap = Bitmap.createScaledBitmap(image_Bitmap, 100, 100, false);

                    imageView.setImageBitmap(captureImage);
                    cardView.setCardBackgroundColor(getResources().getColor(R.color.gray1));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //keep track of cropping intent
    final int PIC_CROP = 2;
    public void performCrop() {
        try {
            Intent cropIntent = new Intent("com.android.camera.action.CROP");   //call the standard crop action intent (the user device may not support it)

            cropIntent.setDataAndType(image_Uri, "image/*");    //indicate image type and Uri
            cropIntent.putExtra("crop", "true");    //set crop properties
            cropIntent.putExtra("aspectX", 1);  //indicate aspect of desired crop
            cropIntent.putExtra("aspectY", 1);
            cropIntent.putExtra("outputX", 420);    //indicate output X and Y
            cropIntent.putExtra("outputY", 420);
            cropIntent.putExtra("return-data", true);   //retrieve data on return

            cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_Uri);
            startActivityForResult(cropIntent, PIC_CROP);
        } catch (ActivityNotFoundException anfe) {
            String errorMessage = "Whoops - your device doesn't support the crop action!";
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }
    }


    public void editBTN_clicked(View view) {
        if (editw) { // --> cen type
            name_field.setEnabled(false);
            barcode_field.setEnabled(false);
            description_field.setEnabled(false);
            quantity_field.setEnabled(false);
            price_field.setEnabled(false);
            editw = false;

        } else { // --> CAN NOT TYPE
            name_field.setEnabled(true);
            barcode_field.setEnabled(true);
            description_field.setEnabled(true);
            quantity_field.setEnabled(true);
            price_field.setEnabled(true);
            editw = true;

        }
    }
}
