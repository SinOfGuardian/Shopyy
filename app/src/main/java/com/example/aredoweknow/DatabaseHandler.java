package com.example.aredoweknow;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileInputStream;
import java.io.IOException;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "shopyy_db";
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
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_ITEM + " TEXT,"
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

    // code to add the new item
    public boolean additem(String iTEM, String iMAGE, String bARCODE, String dESCRIPTION, String qUANTITY, String pRICE) {
        db = this.getWritableDatabase();
        try {
            FileInputStream fs = new FileInputStream(iMAGE);
            byte[] imgbyte = new byte[fs.available()];
            fs.read(imgbyte);
            ContentValues values = new ContentValues();
            values.put(KEY_ITEM, iTEM); // ITEM NAME
            values.put(KEY_IMAGE, imgbyte); // ITEM IMAGE
            values.put(KEY_BARCODE, bARCODE); // ITEM BARCODE
            values.put(KEY_DESCRIPTION, dESCRIPTION); // ITEM DESCRIPTION
            values.put(KEY_QUANTITY, qUANTITY); // ITEM QUANTITY
            values.put(KEY_PRICE, pRICE); // ITEM PRICE

             db.insert(TABLE_NAME, null, values);
             fs.close();
            return true;
        }catch (IOException e){
            e.printStackTrace();
        return false;
        }
    }
    //WALA ITO hayaan mo lang
//    // code to update the item
//    public void updateitem(String uITEM, String uIMAGE, String uBARCODE, String uDESCRIPTION, String uQUANTIY, String uPRICE) {
//        db = this.getWritableDatabase();
//        FileInputStream fs = new FileInputStream(uIMAGE);
//        byte[] uimgbyte = new byte[fs.available()];
//        ContentValues values = new ContentValues();
//        values.put(KEY_ITEM, uITEM); // ITEM NAME
//        values.put(KEY_IMAGE, uimgbyte); // ITEM IMAGE
//        values.put(KEY_BARCODE, uBARCODE); // ITEM BARCODE
//        values.put(KEY_DESCRIPTION, uDESCRIPTION); // ITEM DESCRIPTION
//        values.put(KEY_QUANTITY, uQUANTIY); // ITEM QUANTITY
//        values.put(KEY_PRICE, uPRICE); // ITEM PRICE
//
//        db.update(TABLE_NAME, values, KEY_ID + "='" + 1,null);
//        db.close();
//    }

    // code to get the value of item please edit wisely
//    public String getAccount() {
//        db = this.getReadableDatabase();
//
//        Cursor cursor = db.query(TABLE_NAME, new String[] {KEY_ID,KEY_USERNAME,KEY_PASSWORD,KEY_STORE},
//                null, null, null, null, null);
//
//        int eId = cursor.getColumnIndex(KEY_ID);
//        int usern = cursor.getColumnIndex(KEY_USERNAME);
//        int passwd = cursor.getColumnIndex(KEY_PASSWORD);
//        int eSN = cursor.getColumnIndex(KEY_STORE);
//
//        String res = "";
//
//        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
//            res = res +
//                    "Id: "+cursor.getString(eId)+"\n"+
//                    "Username: "+cursor.getString(usern)+"\n"+
//                    "Password: "+cursor.getString(passwd)+"\n"+
//                    "Store name: "+cursor.getString(eSN)+"\n\n";
//        }
//
//        db.close();
//        return res;
//    }
//    // code to glogin





    // code to update the single employee
    public void updateAccount(long l, String uITEM, String uIMAGE, String uBARCODE, String uDESCRIPTION, String uQUANTIY, String uPRICE) throws IOException {
        db = this.getWritableDatabase();

        FileInputStream fs = new FileInputStream(uIMAGE);
        byte[] imgbyte = new byte[fs.available()];

        ContentValues values = new ContentValues();
        values.put(KEY_ITEM, uITEM); // ITEM NAME
        values.put(KEY_IMAGE, imgbyte); // ITEM IMAGE
        values.put(KEY_BARCODE, uBARCODE); // ITEM BARCODE
        values.put(KEY_DESCRIPTION, uDESCRIPTION); // ITEM DESCRIPTION
        values.put(KEY_QUANTITY, uQUANTIY); // ITEM QUANTITY
        values.put(KEY_PRICE, uPRICE); // ITEM PRICE

        db.update(TABLE_NAME, values, KEY_ID + "='" + 1,null);
        db.close();

    }

    // Deleting item
    public void deleteitem(long l) {
        db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + " ="+l,null);
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
//    }
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


}
