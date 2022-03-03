package com.example.aredoweknow;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Shopyy";
    private static final String TABLE_NAME = "item";
    private static final String KEY_ID = "id";
    public static final String KEY_ITEM = "itemname";
    public static final String KEY_IMAGE = "itemimage";
    private static final String KEY_BARCODE = "barcode";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_QUANTITY = "quantity";
    private static final String KEY_PRICE = "price";
    SQLiteDatabase db;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_ITEM + " TEXT,"
                + KEY_IMAGE + " BLOB NOT NULL,"
                + KEY_BARCODE + " TEXT,"
                + KEY_DESCRIPTION + " TEXT,"
                + KEY_QUANTITY + " INTEGER,"
                + KEY_PRICE + " DOUBLE " + ")";
        db.execSQL(query);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    public void insertItem(String item, byte[] img, String barc, String desc, int quant, double price) {
        db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ITEM, item);                 // ITEM NAME
        values.put(KEY_IMAGE, img);                 // ITEM IMAGE
        values.put(KEY_BARCODE, barc);              // ITEM BARCODE
        values.put(KEY_DESCRIPTION, desc);          // ITEM DESCRIPTION
        values.put(KEY_QUANTITY, quant);            // ITEM QUANTITY
        values.put(KEY_PRICE, price);               // ITEM PRICE

        long result = db.insert(TABLE_NAME, null, values);
        db.close();//closing database
    }
    public String itemName(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from item where id = ?", new String[]{id});
        cursor.moveToFirst();
        return cursor.getString(0);
    }
    public Bitmap getImage(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from item where id = ?", new String[]{id});
        cursor.moveToFirst();
        byte[] bitmap = cursor.getBlob(1);
        Bitmap image = BitmapFactory.decodeByteArray(bitmap, 0 , bitmap.length);
        return image;
    }

    // code to update the single employee
    public void updateAccount(long l, String uITEM, String uIMAGE, String uBARCODE, String uDESCRIPTION, String uQUANTIY, String uPRICE) {
        db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ITEM, uITEM); // ITEM NAME
        values.put(KEY_IMAGE, uIMAGE); // ITEM IMAGE
        values.put(KEY_BARCODE, uBARCODE); // ITEM BARCODE
        values.put(KEY_DESCRIPTION, uDESCRIPTION); // ITEM DESCRIPTION
        values.put(KEY_QUANTITY, uQUANTIY); // ITEM QUANTITY
        values.put(KEY_PRICE, uPRICE); // ITEM PRICE

        db.update(TABLE_NAME, values, KEY_ID + "='" + 1, null);
        db.close();

    }

    // Deleting single employee
    public void deleteAccount(long l) {
        db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + " ="+l,null);
    }

}







//    public String getitem(long l1){
//        db = this.getReadableDatabase();
//         try {
//        Cursor cursor = db.query(TABLE_NAME, new String[]{KEY_ID, KEY_ITEM, KEY_IMAGE, KEY_BARCODE, KEY_DESCRIPTION, KEY_QUANTITY, KEY_PRICE},
//                KEY_ID + "=" + l1, null, null, null, null, null, null, null);
//
//        if (cursor != null) {
//            cursor.moveToFirst();
//            byte[] imgbyte = cursor.getBlob(0);
//
//            String gitem = cursor.getString(1);
//            return gitem;
//             }
//          }catch (IOException e) {
//         e.printStackTrace();
//
//        }
//        return null;
   // }
//
//    public String getPASSword(long l1){
//        db = this.getReadableDatabase();
//
//        Cursor cursor = db.query(TABLE_NAME, new String[] {KEY_ID,KEY_USERNAME,KEY_PASSWORD,KEY_STORE},
//                KEY_ID+"="+l1, null, null, null, null);
//
//        if (cursor != null) {
//            cursor.moveToFirst();
//            String age = cursor.getString(2);
//            return  age;
//        }
//        return null;
//    }
//
//    public String getstoreNAME(long l1){
//        db = this.getReadableDatabase();
//
//        Cursor cursor = db.query(TABLE_NAME, new String[] {KEY_ID,KEY_USERNAME,KEY_PASSWORD,KEY_STORE},
//                KEY_ID+"="+l1, null, null, null, null);
//
//        if (cursor != null) {
//            cursor.moveToFirst();
//            String store = cursor.getString(3);
//            return  store;
//        }
//        return null;
//    }


  //  }
