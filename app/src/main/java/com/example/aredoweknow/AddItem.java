package com.example.aredoweknow;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import java.io.ByteArrayOutputStream;

public class AddItem extends AppCompatActivity implements View.OnFocusChangeListener {

    AppCompatImageButton backbtn;
    Button cameraBTN, GalleryBTN, AddBTN, AddITEMBTN;
    ImageButton scanBTN;

    DatabaseHandler dataHandler;

    public static EditText resulttextview;

    EditText name_field, barcode_field, description_field, quantity_field, price_field;
    Bitmap captureImage;
    ImageView imageView;
    Uri selectedImage;
    Bitmap imageDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        resulttextview = findViewById(R.id.barcode_val);

        name_field = findViewById(R.id.itemname_val);
        barcode_field = findViewById(R.id.barcode_val);
        description_field = findViewById(R.id.description_val);
        quantity_field = findViewById(R.id.quantity_val);
        price_field = findViewById(R.id.price_val);

        dataHandler = new DatabaseHandler(this);


        //--------------CAMERA CODE
        imageView = findViewById(R.id.image_val);

        if (ContextCompat.checkSelfPermission(AddItem.this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(AddItem.this, new String[]{Manifest.permission.CAMERA}, 100);
        }


        //------------------Back to Dashboard
        backbtn = findViewById(R.id.add_back_btn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //-------------------Open Camera for Image
        cameraBTN = findViewById(R.id.camera_btn);
        cameraBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 100);
            }
        });

        //--------------------Open gallery
        GalleryBTN = findViewById(R.id.gallery_btn);
        GalleryBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 3);
            }
        });

        //-------------------Open Scanner
        scanBTN = findViewById(R.id.barscan_btn);
        scanBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddItem.this, Scanner.class);
                startActivity(intent);
            }
        });

        //-------------------Add New Item
        AddBTN = findViewById(R.id.add_btn);
        AddBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFieldsEmpty() && !wrongInputFormat() && !wrongInputFormat() && !nullImage()) {
//                    TODO Add Data to database

                    String name = name_field.getText().toString();
                    String  barcode = barcode_field.getText().toString();
                    String description = description_field.getText().toString();
                    Integer quantity = Integer.parseInt(quantity_field.getText().toString());
                    double price = Double.parseDouble(price_field.getText().toString());

                    Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
//                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.id.image_val);
                    ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArray);
                    byte[] img = byteArray.toByteArray();

                    //------------image insert to database
                    dataHandler.insertItem(name, img, barcode,description, quantity,price);
//                    Toast.makeText(AddItem.this, "DATABASE SUCCESSFUL YOU FUCKER!!", Toast.LENGTH_SHORT).show();

                }
            }
        });


        name_field.setOnFocusChangeListener(this);
        barcode_field.setOnFocusChangeListener(this);
        description_field.setOnFocusChangeListener(this);
        quantity_field.setOnFocusChangeListener(this);
        price_field.setOnFocusChangeListener(this);
    }
    //--------------Removes Warning on Text Fields when received focus
    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        if (view.isFocused()) {
            view.setSelected(false); //--> Remove Highlight red
        }
    }


    //---------------capture camera code And pick picture in Gallery
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            //Capture Image
            captureImage = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(captureImage);
        }
        if(resultCode == RESULT_OK && data != null){
            selectedImage = data.getData();
            imageView.setImageURI(selectedImage);
        }
    }


    //---------------------------------------------------------
    //This checks if one or more fields are empty-
    private boolean isFieldsEmpty() {
        boolean isEmpty = false;

        if (name_field.getText().toString().trim().equals("")) {
            isEmpty = true;
            name_field.setSelected(true); //--> Highlight red
        }
        if (barcode_field.getText().toString().trim().equals("")) {
            isEmpty = true;
            barcode_field.setSelected(true); //--> Highlight red
        }
        if (description_field.getText().toString().trim().equals("")) {
            isEmpty = true;
            description_field.setSelected(true); //--> Highlight red
        }
        if (quantity_field.getText().toString().trim().equals("")) {
            isEmpty = true;
            quantity_field.setSelected(true); //--> Highlight red
        }
        if (price_field.getText().toString().trim().equals("")) {
            isEmpty = true;
            price_field.setSelected(true); //--> Highlight red
        }

        if (isEmpty) {
            display_messageDialog("One or more field/s is Empty!");
        }
        return isEmpty;
    }

    //Check inputed formats
    private boolean wrongInputFormat() {
        boolean isWrongFormat = false;

        //Check barcode
        int length = barcode_field.getText().toString().trim().length();
        if (length > 128 || length < 3) {
            isWrongFormat = true;
            barcode_field.setSelected(true);
        }

        //check quantity
        int quantity = Integer.parseInt(quantity_field.getText().toString().trim());
        if (quantity < 1) {
            isWrongFormat = true;
            quantity_field.setSelected(true);
        }
        //check price
        int price = Integer.parseInt(price_field.getText().toString().trim());
        if (price < 1) {
            isWrongFormat = true;
            price_field.setSelected(true);
        }

        if (isWrongFormat) {
            barcode_field.setSelected(true);
            display_messageDialog("Invalid Format/s");
        }
        return isWrongFormat;
    }

    //Lastly checks if images is selected
    private boolean nullImage() {
        Resources resources  = getResources();
        boolean res = false;

        //check image is not empty
        final ImageView img  = (ImageView)findViewById(R.id.image_val);
        final Bitmap bmap = ((BitmapDrawable)img.getDrawable()).getBitmap();

        @SuppressLint("UseCompatLoadingForDrawables") Drawable myDrawable = getResources().getDrawable(R.drawable.image_100px);
        final Bitmap myLogo = ((BitmapDrawable) myDrawable).getBitmap();

        if (bmap.sameAs(myLogo)) {
            res = true;
            CardView cardView = findViewById(R.id.image_panel);
            cardView.setCardBackgroundColor(resources.getColor(R.color.red_border));
            display_messageDialog("Image cannot be empty.");
        }

        return res;
    }

    //Message Dialog that notifies user
    private void display_messageDialog(String message) {
        Dialog dialog1 = new Dialog(this);
        dialogClass dialog = new dialogClass();

        dialog.simpleDialog(dialog1, message); //--> show simple dialog
    }
    //-----------------------------------------------------------
}