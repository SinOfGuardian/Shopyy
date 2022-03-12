package com.example.aredoweknow.features_functions;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import com.example.aredoweknow.R;
import com.example.aredoweknow.databases_folder.DatabaseHandler;
import com.example.aredoweknow.other_class.REFRESH;
import com.example.aredoweknow.other_class.dialogClass;

import java.io.ByteArrayOutputStream;


public class AddItem extends AppCompatActivity implements View.OnFocusChangeListener {

    AppCompatImageButton backbtn;
    Button cameraBTN, GalleryBTN, AddBTN;
    AppCompatImageButton scanBTN;

    DatabaseHandler dataHandler;
    @SuppressLint("StaticFieldLeak")
    public static EditText resulttextview;

    EditText name_field, barcode_field, description_field, quantity_field, price_field;
    Bitmap image_Bitmap;
    Uri image_Uri;

    ImageView imageView;
    CardView cardView;

    //keep track of cropping intent
    final int PIC_CROP = 2, PIC_GALLERY = 3, PIC_CAMERA = 100;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        SharedPreferences sf = getSharedPreferences("Shopyy",  Context.MODE_PRIVATE);
        String final_un = sf.getString("final_username", "");

        //Database
        dataHandler = new DatabaseHandler(this, final_un);
        //Static Barcode
        resulttextview = findViewById(R.id.barcode_val);

        name_field = findViewById(R.id.itemname_val);
        barcode_field = findViewById(R.id.barcode_val);
        description_field = findViewById(R.id.description_val);
        quantity_field = findViewById(R.id.quantity_val);
        price_field = findViewById(R.id.price_val);

        imageView = findViewById(R.id.image_val);
        cardView = findViewById(R.id.image_panel);

        //------------------Back to Dashboard
        backbtn = findViewById(R.id.back_btn);
        backbtn.setOnClickListener(v -> finish());

        //--------------------Open gallery
        GalleryBTN = findViewById(R.id.gallery_btn);
        GalleryBTN.setOnClickListener(v -> {
            if (CamPermissionGranted()) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PIC_GALLERY);

                //--------------------------REFRESH
                REFRESH r = new REFRESH();
                r.updateArrayList2(this);
            }
        });

        //-------------------Open Camera for Image
        cameraBTN = findViewById(R.id.camera_btn);
        cameraBTN.setOnClickListener(v -> {
            if (CamPermissionGranted()) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, PIC_CAMERA);

                //--------------------------REFRESH
                REFRESH r = new REFRESH();
                r.updateArrayList2(this);
            }
        });

        //-------------------Open Scanner
        scanBTN = findViewById(R.id.barscan_btn);
        scanBTN.setOnClickListener(v -> {
            if (CamPermissionGranted()) {
                Intent intent = new Intent(AddItem.this, Scanner.class);
                intent.putExtra("update", "adding_item");
                startActivity(intent);
            }
        });

        //-------------------Add New Item
        AddBTN = findViewById(R.id.add_btn);
        AddBTN.setOnClickListener(v -> {
            if (!isFieldsEmpty() && !wrongInputFormat()) {
                String name = name_field.getText().toString();
                String barcode = "" + barcode_field.getText().toString();
                String description = "" + description_field.getText().toString();
                int quantity = Integer.parseInt(quantity_field.getText().toString());
                double price = Double.parseDouble(price_field.getText().toString());

                Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
//                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.id.image_val);
                ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, byteArray);
                byte[] img = byteArray.toByteArray();

                //----------------------image insert to database------------------------
                long res = dataHandler.insertItem(name, img, barcode, description, quantity, price);
                if (res > 0) {
                    //TODO: first item to add in database, not refreshing the HOME
                    Toast.makeText(AddItem.this, name + " Added Recently", Toast.LENGTH_SHORT).show();
                    display_messageDialog("Item Added Successfully.");
                    //----------REFRESH
                    REFRESH r = new REFRESH();
                    r.updateArrayList2(this);
                    //--------------------
                    resetFields();
                } else {
                    Toast.makeText(AddItem.this, "Adding " + name + " Item Failed!", Toast.LENGTH_SHORT).show();
                    display_messageDialog("Error Adding Item!");
                }
            }
        });

        name_field.setOnFocusChangeListener(this);
//        barcode_field.setOnFocusChangeListener(this);
//        description_field.setOnFocusChangeListener(this);
        quantity_field.setOnFocusChangeListener(this);
        price_field.setOnFocusChangeListener(this);
    }
    //----------------------------------------------------------------------------------------------



    @Override //--------------Removes Warning on Text Fields when received focus
    public void onFocusChange(View view, boolean hasFocus) {
        if (view.isFocused()) {
            view.setSelected(false); //--> Remove Highlight red
        }
    }


    @Override //---------------capture camera code And pick picture in Gallery
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        Toast.makeText(this, requestCode + " | " + resultCode, Toast.LENGTH_SHORT).show();
//        System.out.println(requestCode + " | " + resultCode);

        if (CamPermissionGranted() && data != null && resultCode == RESULT_OK) {
            System.out.println(requestCode + " | " + resultCode);
            try {
                if (requestCode == PIC_GALLERY) {  //--> Choose from gallery
                    image_Uri = data.getData();
//                image_Bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), image_Uri);
//                imageView.setImageURI(selectedImage);
                    performCrop(); //--> Requires Cropping the image
                }

                if (requestCode == PIC_CROP || requestCode == PIC_CAMERA) { //100
                    image_Bitmap = data.getExtras().getParcelable("data");
//                image_Bitmap = Bitmap.createScaledBitmap(image_Bitmap, 100, 100, false);

                    imageView.setImageBitmap(image_Bitmap);
                    cardView.setCardBackgroundColor(getResources().getColor(R.color.gray1));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    //Try cropping the image
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

    //---------------------------This checks if one or more fields are empty-
    private boolean isFieldsEmpty() {
        boolean isEmpty = false;

        if (name_field.getText().toString().trim().equals("")) {
            isEmpty = true;
            name_field.setSelected(true); //--> Highlight red
        }
//        if (barcode_field.getText().toString().trim().equals("")) {
//            isEmpty = true;
//            barcode_field.setSelected(true); //--> Highlight red
//        }
//        if (description_field.getText().toString().trim().equals("")) {
//            isEmpty = true;
//            description_field.setSelected(true); //--> Highlight red
//        }
        if (quantity_field.getText().toString().trim().equals("")) {
            isEmpty = true;
            quantity_field.setSelected(true); //--> Highlight red
        }
        if (price_field.getText().toString().trim().equals("")) {
            isEmpty = true;
            price_field.setSelected(true); //--> Highlight red
        }

        if (isEmpty) {
            display_messageDialog("Required field/s cannot be empty!");
        }
        return isEmpty;
    }

    //----------------------Check inputed formats
    private boolean wrongInputFormat() {
        boolean isWrongFormat = false;

        //Check barcode
//        int length = barcode_field.getText().toString().trim().length();
//        if (length > 128 || length < 3) {
//            isWrongFormat = true;
//            barcode_field.setSelected(true);
//        }
        //check quantity
        int quantity = Integer.parseInt(quantity_field.getText().toString().trim());
        if (quantity < 1) {
            isWrongFormat = true;
            quantity_field.setSelected(true);
        }
        //check price
        double price = Double.parseDouble(price_field.getText().toString().trim());
        if (price < 1) {
            isWrongFormat = true;
            price_field.setSelected(true);
        }

        if (isWrongFormat) {
            display_messageDialog("Invalid Format/s");
        }
        return isWrongFormat;
    }

    //---------------------Lastly this checks if images is selected
    private boolean nullImage() {
        Resources resources = getResources();
        boolean res = false;

        //check image if not empty
        final ImageView img = (ImageView) findViewById(R.id.image_val2);
        final Bitmap bmap = ((BitmapDrawable) img.getDrawable()).getBitmap();

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

    //----------------This Reset the fields after successfull add
    public void resetFields() {
        name_field.setText("");
        barcode_field.setText("");
        description_field.setText("");
        quantity_field.setText("");
        price_field.setText("");
        resulttextview.setText("");

        cardView.setCardBackgroundColor(getResources().getColor(R.color.gray1));
        @SuppressLint("UseCompatLoadingForDrawables") Drawable myDrawable = getResources().getDrawable(R.drawable.no_productimage);
        imageView.setImageBitmap(((BitmapDrawable) myDrawable).getBitmap());

        cardView.setFocusableInTouchMode(true);
        cardView.requestFocus();
    }

    //----------------------Message Dialog that notifies user
    private void display_messageDialog(String message) {
        Dialog dialog1 = new Dialog(this);
        dialogClass dialog = new dialogClass();

        cardView.setFocusableInTouchMode(true);
        cardView.requestFocus();
        dialog.simpleDialog(dialog1, message); //--> show simple dialog
    }
    //-----------------------------------------------------------

    //--------- Check Camera Permission
    private boolean CamPermissionGranted() {
        //--------------CAMERA CODE
        if (ContextCompat.checkSelfPermission(AddItem.this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AddItem.this, new String[]{Manifest.permission.CAMERA}, 100);
            return false;
        } else {
            return true;
        }
    }
}