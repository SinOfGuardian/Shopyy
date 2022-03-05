package com.example.aredoweknow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toolbar;

public class VieweditActivity extends AppCompatActivity {
    private boolean editw = true;

    AppCompatImageButton backbtn;
    Button cameraBTN, galleryBTN, editBTN, saveBTN;
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

    public static EditText static_namefield;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewedit);

        intent = getIntent();
        name = intent.getStringExtra("name");
        price = intent.getStringExtra("price");
        barcode = intent.getStringExtra("barcode");
        description = intent.getStringExtra("descr");
        quantity = intent.getStringExtra("quant");
        image = intent.getParcelableExtra("image");

        imageView = findViewById(R.id.image_val2);
        name_field = findViewById(R.id.itemname_val2);
        barcode_field = findViewById(R.id.barcode_val2);
        static_namefield = barcode_field;
        description_field = findViewById(R.id.description_val2);
        price_field = findViewById(R.id.price_val2);
        quantity_field = findViewById(R.id.quantity_val2);

        imageView.setImageBitmap(image);
        name_field.setText(name);
        barcode_field.setText(barcode);
        description_field.setText(description);
        quantity_field.setText(quantity);
        price_field.setText(price);


        //--------------BACK TO DASHBOARD--------------
        backbtn = findViewById(R.id.view_back_btn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        editBTN = findViewById(R.id.edit_btn);
        editBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editBTN_clicked(v);
            }
        });

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
        scanBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VieweditActivity.this, Scanner.class);
                intent.putExtra("update", "viewing_item");
                startActivity(intent);
            }
        });

        saveBTN = findViewById(R.id.save_btn);
        saveBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO save function here
            }
        });
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