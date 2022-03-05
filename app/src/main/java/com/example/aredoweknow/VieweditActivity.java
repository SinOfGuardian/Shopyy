package com.example.aredoweknow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toolbar;

public class VieweditActivity extends AppCompatActivity {
    private boolean editw = true;

    AppCompatImageButton backbtn;
    Button cameraBTN, GalleryBTN, AddBTN;
    ImageButton scanBTN;
    DatabaseHandler dataHandler;
    EditText name_field, barcode_field, description_field, quantity_field, price_field;
    Bitmap captureImage;
    ImageView imageView;
    CardView cardView;
    Toolbar toolbar;

    Intent intent = getIntent();
     String name = intent.getStringExtra("name");
    String price = intent.getStringExtra("price");
//    String barcode = intent.getStringExtra("barcode");
   // String description = intent.getStringExtra("description");
    String quantity = intent.getStringExtra("quan");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewedit);



        AddBTN = findViewById(R.id.edit_btn);
        name_field = findViewById(R.id.itemname_val);
        barcode_field = findViewById(R.id.barcode_val);
        description_field = findViewById(R.id.description_val);
        quantity_field = findViewById(R.id.quantity_val);
        price_field = findViewById(R.id.price_val);
        imageView = findViewById(R.id.image_val);


        name_field.setText(name);
        //barcode_field.setText(barcode);
//        description_field.setText(description);
        quantity_field.setText(quantity);
        price_field.setText(getText(price));


//--------------------BACK TO DASHBOARD----------------------------
        backbtn = findViewById(R.id.add_back_btn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    public void eye_clicked(View view) {
        if (editw) { // --> cen type
            name_field.setShowSoftInputOnFocus(false);
            barcode_field.setShowSoftInputOnFocus(false);
            description_field.setShowSoftInputOnFocus(false);
            quantity_field.setShowSoftInputOnFocus(false);
            price_field.setShowSoftInputOnFocus(false);

            editw = false;
        } else { // --> CAN NOT TYPE
            name_field.setShowSoftInputOnFocus(true);
            barcode_field.setShowSoftInputOnFocus(true);
            description_field.setShowSoftInputOnFocus(true);
            quantity_field.setShowSoftInputOnFocus(true);
            price_field.setShowSoftInputOnFocus(true);
            editw = true;
        }
    }
}