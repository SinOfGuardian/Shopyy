package com.example.aredoweknow.features_functions;


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
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.aredoweknow.R;
import com.example.aredoweknow.databases_folder.DatabaseHandler;
import com.example.aredoweknow.other_class.GetterSetter;
import com.example.aredoweknow.other_class.REFRESH;
import com.example.aredoweknow.other_class.dialogClass;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class VieweditActivity extends AppCompatActivity {
    private boolean editw = true;

    DatabaseHandler db;
    AppCompatImageButton backbtn;

    Button cameraBTN, galleryBTN, editBTN, delBTN;
    FrameLayout saveBTN;
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
    public static EditText static_barcode;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewedit);

        SharedPreferences sf = getSharedPreferences("Shopyy", Context.MODE_PRIVATE);
        String final_un = sf.getString("final_username", "");

        //Database
        db = new DatabaseHandler(this, final_un);
//==================VARIABLE XML
        name_field = findViewById(R.id.itemname_val2);
        barcode_field = findViewById(R.id.barcode_val2);

        static_barcode = barcode_field; //--> To update by Scanner Class
        description_field = findViewById(R.id.description_val2);
        quantity_field = findViewById(R.id.quantity_val2);
        price_field = findViewById(R.id.price_val2);
        imageView = findViewById(R.id.image_val2);

//==============GET INTENT=====================
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

        //-------------------Open Scanner
        scanBTN = findViewById(R.id.barscan_btn2);
        scanBTN.setOnClickListener(v -> {
            if (CamPermissionGranted()) {
                Intent intent = new Intent(VieweditActivity.this, Scanner.class);
                intent.putExtra("update", "viewing_item");
                startActivity(intent);
            }
        });
        //--------------------Open gallery
        galleryBTN = findViewById(R.id.gallery_btn2);
        galleryBTN.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 3);

            //--------------------------RREFrESH
            REFRESH r = new REFRESH();
            r.updateArrayList2(this);

        });
        //-----------------> Capture Mode
        cameraBTN = findViewById(R.id.camera_btn2);
        cameraBTN.setOnClickListener(v -> {
            if (CamPermissionGranted()) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 100);

                //--------------------------RREFrESH
                REFRESH r = new REFRESH();
                r.updateArrayList2(this);
            }
        });

        //----------------> Toggles Edit
        editBTN = findViewById(R.id.edit_btn);
        editBTN.setOnClickListener(v -> {
            if (editw) {
                editBTN_clicked(false);
            } else {
                editBTN_clicked(true);
            }
        });
        //-----------------> Save Button
        saveBTN = findViewById(R.id.save_btn);
        saveBTN.setOnClickListener(v -> {
            //TODO save function here

            if (!isFieldsEmpty() && !wrongInputFormat() && !wrongInputFormat() && !nullImage()) {
              //  Toast.makeText(VieweditActivity.this, "SAVE SAVE", Toast.LENGTH_SHORT).show();


                String id = intent.getStringExtra("id");
                String name = name_field.getText().toString();
                String barcode = "" + barcode_field.getText().toString();
                String description = description_field.getText().toString();
                int quantity = Integer.parseInt(quantity_field.getText().toString());
                double price = Double.parseDouble(price_field.getText().toString());

                Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                Bitmap.createScaledBitmap(bitmap, 100, 100, false);
//                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.id.image_val);
                ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArray);
                byte[] img = byteArray.toByteArray();
                int res = Integer.parseInt(id);

                db.updateAccount(res, name, img, barcode, description, quantity, price);

                //--------------------------RREFrESH
                REFRESH r = new REFRESH();
                r.updateArrayList2(this);

                Toast.makeText(VieweditActivity.this, name + " Update Recently", Toast.LENGTH_SHORT).show();
                display_messageDialog("Item Updated Successfully.");

                Toast.makeText(VieweditActivity.this, " Data Did Not Change", Toast.LENGTH_SHORT).show();


            }

        });
        //----------------> Delete Button
        delBTN = findViewById(R.id.delete_btn);
        delBTN.setOnClickListener(v -> {
            //TODO delete function here
            String id = intent.getStringExtra("id");
            if (id.equals("")) {
                Toast.makeText(VieweditActivity.this, "Please fill the ITEM    ", Toast.LENGTH_SHORT).show();
            } else {
                long l = Long.parseLong(id);
                db.deleteAccount(l);
                Toast.makeText(VieweditActivity.this, " Successful Remove Item ", Toast.LENGTH_SHORT).show();


                //REFRESH
                REFRESH r = new REFRESH();
                r.updateArrayList2(this);

                delBTN.refreshDrawableState();
                ArrayList<GetterSetter> al = new ArrayList<>();

                finish();
            }
        });
        editBTN_clicked(false);
    }
//----------------ewan


    //----------------This Reset the fields after successful add
    public void resetFields() {
        name_field.setText("");
        barcode_field.setText("");
        description_field.setText("");
        quantity_field.setText("");
        price_field.setText("");

        cardView.setCardBackgroundColor(getResources().getColor(R.color.gray1));
        @SuppressLint("UseCompatLoadingForDrawables") Drawable myDrawable = getResources().getDrawable(R.drawable.image_100px);
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

        } catch (ActivityNotFoundException e) {
            //display an error message
            String errorMessage = "Whoops - your device doesn't support the crop action!";
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }

    }

    public void editBTN_clicked(boolean state) {
        if (!state) { // --> CAN NOT TYPE/EDIT
            name_field.setEnabled(false);
            barcode_field.setEnabled(false);
            description_field.setEnabled(false);
            quantity_field.setEnabled(false);
            price_field.setEnabled(false);


            galleryBTN.setEnabled(false);
            cameraBTN.setEnabled(false);
            scanBTN.setEnabled(false);

            saveBTN.setEnabled(false);
            delBTN.setEnabled(false);
            editw = false;

        } else { // --> CAN TYPE/EDIT
            name_field.setEnabled(true);
            barcode_field.setEnabled(true);
            description_field.setEnabled(true);
            quantity_field.setEnabled(true);
            price_field.setEnabled(true);

            galleryBTN.setEnabled(true);
            cameraBTN.setEnabled(true);
            scanBTN.setEnabled(true);

            saveBTN.setEnabled(true);
            delBTN.setEnabled(true);
            editw = true;

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
        Double price = Double.parseDouble(price_field.getText().toString().trim());
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


}
