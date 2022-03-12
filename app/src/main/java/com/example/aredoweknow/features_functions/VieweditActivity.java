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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.aredoweknow.R;
import com.example.aredoweknow.databases_folder.DatabaseHandler;
import com.example.aredoweknow.other_class.REFRESH;
import com.example.aredoweknow.other_class.dialogClass;

import java.io.ByteArrayOutputStream;

public class VieweditActivity extends AppCompatActivity implements View.OnFocusChangeListener {
    private boolean edit_mode = true;

    DatabaseHandler dbh;
    AppCompatImageButton backBTN, scanBTN, editBTN;

    Button cameraBTN, galleryBTN, delBTN;
    FrameLayout saveBTN;
    Uri image_Uri;
    Intent intent;

    EditText name_field, barcode_field, description_field, quantity_field, price_field;
    Bitmap captureImage;
    ImageView imageView;
    CardView cardView;

    String name, price, barcode, description, quantity;
    Bitmap image;

    //keep track of cropping intent
    final int PIC_CROP = 2, PIC_GALLERY = 3, PIC_CAMERA = 100;
    TextView req_label1, req_label2, req_label3;
    private String data;

    @SuppressLint("StaticFieldLeak")
    public static EditText static_barcode;

    @SuppressLint({"CutPasteId", "UseCompatLoadingForDrawables"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewedit);

        SharedPreferences sf = getSharedPreferences("Shopyy", Context.MODE_PRIVATE);
        String final_un = sf.getString("final_username", "");

        //Database
        dbh = new DatabaseHandler(this, final_un);
        cardView = findViewById(R.id.image_panel2);

        //==================VARIABLE XML
        name_field = findViewById(R.id.itemname_val2);
        barcode_field = findViewById(R.id.barcode_val2);

        static_barcode = barcode_field; //--> To update by Scanner Class
        description_field = findViewById(R.id.description_val2);
        quantity_field = findViewById(R.id.quantity_val2);
        price_field = findViewById(R.id.price_val2);
        imageView = findViewById(R.id.image_val2);

        //==============GET INTENT EXTRAS=====================
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

        //-------------------Labels
        req_label1 = findViewById(R.id.required1);
        req_label2 = findViewById(R.id.required2);
        req_label3 = findViewById(R.id.required3);

        //--------------BACK TO DASHBOARD--------------
        backBTN = findViewById(R.id.view_back_btn);
        backBTN.setOnClickListener(v -> {
            this.onBackPressed();
        });

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

            //--------------------------REFRESH
            REFRESH r = new REFRESH();
            r.updateArrayList2(this);
        });
        //-----------------> Capture Mode
        cameraBTN = findViewById(R.id.camera_btn2);
        cameraBTN.setOnClickListener(v -> {
            if (CamPermissionGranted()) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 100);

                //--------------------------REFRESH
                REFRESH r = new REFRESH();
                r.updateArrayList2(this);
            }
        });

        //----------------> Toggles Edit
        editBTN = findViewById(R.id.edit_btn);
        editBTN.setOnClickListener(v -> {
            if (edit_mode) {
                editBTN_clicked(false);
            } else {
                editBTN_clicked(true);
            }
        });

        //-----------------> Save Button
        saveBTN = findViewById(R.id.save_btn);
        saveBTN.setOnClickListener(v -> {
            String data2 = name_field.getText().toString() +
                    barcode_field.getText().toString() +
                    description_field.getText().toString() +
                    quantity_field.getText().toString() +
                    price_field.getText().toString() +
                    imageView.getDrawable().toString();

            if (data2.equals(data)) {

            }
            if (!isFieldsEmpty() && !wrongInputFormat()) {
                String id = intent.getStringExtra("id");
                String name = name_field.getText().toString();
                String barcode = "" + barcode_field.getText().toString();
                String description = "" + description_field.getText().toString();
                int quantity = Integer.parseInt(quantity_field.getText().toString());
                double price = Double.parseDouble(price_field.getText().toString());

                Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
//                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.id.image_val);
                ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArray);
                byte[] img = byteArray.toByteArray();
                int res = Integer.parseInt(id);

                dbh.updateAccount(res, name, img, barcode, description, quantity, price);

                //--------------------------REFRESH
                REFRESH r = new REFRESH();
                r.updateArrayList2(this);

                Toast.makeText(VieweditActivity.this, "Item Updated Successfully", Toast.LENGTH_SHORT).show();
                editBTN_clicked(false);
            }
        });

        //----------------> Delete Button
        delBTN = findViewById(R.id.delete_btn);
        delBTN.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setIcon(getDrawable(R.drawable.delete_red_28px))
                    .setTitle("Delete Item")
                    .setMessage("Are you sure do want to delete this item?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        String id = intent.getStringExtra("id");
                        long l = Long.parseLong(id);
                        dbh.deleteAccount(l);

                        //REFRESH
                        REFRESH r = new REFRESH();
                        r.updateArrayList2(this);

//                        delBTN.refreshDrawableState();
//                        ArrayList<GetterSetter> al = new ArrayList<>();

                        Toast.makeText(VieweditActivity.this, " Item Remove Successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }).setNegativeButton("No", null).show();
        });

        name_field.setOnFocusChangeListener(this);
//        barcode_field.setOnFocusChangeListener(this);
//        description_field.setOnFocusChangeListener(this);
        quantity_field.setOnFocusChangeListener(this);
        price_field.setOnFocusChangeListener(this);

        data = name_field.getText().toString() +
                barcode_field.getText().toString() +
                description_field.getText().toString() +
                quantity_field.getText().toString() +
                price_field.getText().toString() +
                imageView.getDrawable().toString();
        editBTN_clicked(false);
    }//-------------------------------------------------------------------------------------------

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBackPressed() {
        String data2 = name_field.getText().toString() +
                barcode_field.getText().toString() +
                description_field.getText().toString() +
                quantity_field.getText().toString() +
                price_field.getText().toString() +
                imageView.getDrawable().toString();

        if (data2.equals(data)) {
            super.onBackPressed();
        }else {
            new AlertDialog.Builder(this)
                    .setIcon(getDrawable(R.drawable.undo_32px))
                    .setTitle("Edit Item")
                    .setMessage("" +
                            "Discard Changes?")
                    .setPositiveButton("Yes", (dialog, which) -> finish()
                    ).setNegativeButton("No", null).show();
        }
    }


    @Override //--------------Removes Warning on Text Fields when received focus
    public void onFocusChange(View view, boolean hasFocus) {
        if (view.isFocused()) {
            view.setSelected(false); //--> Remove Highlight red
        }
    }

    //----------------This Reset the fields after successful add
    public void resetFields() {
        name_field.setText("");
        barcode_field.setText("");
        description_field.setText("");
        quantity_field.setText("");
        price_field.setText("");

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

        saveBTN.setFocusableInTouchMode(true);
        saveBTN.requestFocus();
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
            try {
                if (requestCode == PIC_GALLERY) {  //--> Choose from gallery
                    image_Uri = data.getData();
//                image_Bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), image_Uri);
//                imageView.setImageURI(selectedImage);
                    performCrop(); //--> Requires Cropping the image
                }

                if (requestCode == PIC_CROP || requestCode == PIC_CAMERA) {
                    captureImage = data.getExtras().getParcelable("data");
//                image_Bitmap = Bitmap.createScaledBitmap(image_Bitmap, 100, 100, false);

                    imageView.setImageBitmap(captureImage);
//                    cardView.setCardBackgroundColor(getResources().getColor(R.color.gray1));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void performCrop() {
        try {
            //call the standard crop action intent (the user device may not support it)
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            cropIntent.setDataAndType(image_Uri, "image/*");    //indicate image type and Uri
            cropIntent.putExtra("crop", "true");    //set crop properties
            cropIntent.putExtra("aspectX", 1);  //indicate aspect of desired crop
            cropIntent.putExtra("aspectY", 1);
            cropIntent.putExtra("outputX", 520);    //indicate output X and Y
            cropIntent.putExtra("outputY", 520);
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
        // false = Disable Editing, true = Enable Editing
        name_field.setEnabled(state);
        barcode_field.setEnabled(state);
        description_field.setEnabled(state);
        quantity_field.setEnabled(state);
        price_field.setEnabled(state);

        galleryBTN.setEnabled(state);
        cameraBTN.setEnabled(state);
        scanBTN.setEnabled(state);

        saveBTN.setEnabled(state);
        delBTN.setEnabled(state);
        edit_mode = state;

        if (!state) { // --> CAN NOT TYPE/EDIT
            req_label1.setVisibility(View.INVISIBLE);
            req_label2.setVisibility(View.INVISIBLE);
            req_label3.setVisibility(View.INVISIBLE);
        } else { // --> CAN TYPE/EDIT
            req_label1.setVisibility(View.VISIBLE);
            req_label2.setVisibility(View.VISIBLE);
            req_label3.setVisibility(View.VISIBLE);
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


}
