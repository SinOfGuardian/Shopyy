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
    private static final String DATABASE_NAME = "Shopyy_Items";
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

    public long insertItem(String item, byte[] img, String barc, String desc, int quant, double price) {
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
        return result;
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
    public void updateAccount(long l, String uITEM, byte[] uIMAGE, String uBARCODE, String uDESCRIPTION, int uQUANTIY, double uPRICE) {
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

    // code to get the single employee
       public String getEmployee() {
                   db = this.getReadableDatabase();

                   Cursor cursor = db.query(TABLE_NAME, new String[] {KEY_ID,KEY_ITEM,KEY_IMAGE,KEY_BARCODE,KEY_DESCRIPTION,KEY_QUANTITY,KEY_PRICE},
                                   null, null, null, null, null, null);

                    int eId = cursor.getColumnIndex(KEY_ID);
                    int eItem = cursor.getColumnIndex(KEY_ITEM);
                    int eImage = cursor.getColumnIndex(KEY_IMAGE);
                    int eBarcode = cursor.getColumnIndex(KEY_BARCODE);
                    int eDescription = cursor.getColumnIndex(KEY_DESCRIPTION);
                    int eQuantiy = cursor.getColumnIndex(KEY_QUANTITY);
                    int ePrice = cursor.getColumnIndex(KEY_PRICE);

                   String res = "";

                   for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
                           res = res +
                                            "Image:: "+cursor.getString(eImage)+"\n"+
                                           "Name: "+cursor.getString(eItem)+"\n"+
                                            "Price: "+cursor.getString(ePrice)+"\n\n";


                       }

                   db.close();
                   return res;
               }

//-------------------GET DATA------------
    public Cursor getData(){
        db = this.getReadableDatabase();
        Cursor c = db.rawQuery("select * from item", null);
        return c;
    }


               //getITEMMMMM
    public String getItem(long l1){
        db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[] {KEY_ID,KEY_ITEM,KEY_IMAGE,KEY_BARCODE,KEY_DESCRIPTION,KEY_QUANTITY,KEY_PRICE},
                KEY_ID+"="+l1, null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            String item = cursor.getString(1);
            return  item;
        }
        return null;
    }
//--------------GET IMAGE
    public Bitmap getImage(long l1){
    db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[] {KEY_ID,KEY_ITEM,KEY_IMAGE,KEY_BARCODE,KEY_DESCRIPTION,KEY_QUANTITY,KEY_PRICE},
            KEY_ID+"="+l1, null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();

            byte[] bitmap = cursor.getBlob(2);
            Bitmap image = BitmapFactory.decodeByteArray(bitmap, 0 , bitmap.length);
            return image;
     }
        return null;
    }

    //-------------------GET BARCODE
    public String getbarcode(long l1){
        db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[] {KEY_ID,KEY_ITEM,KEY_IMAGE,KEY_BARCODE,KEY_DESCRIPTION,KEY_QUANTITY,KEY_PRICE},
                KEY_ID+"="+l1, null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            String barcode = cursor.getString(3);
            return  barcode;
        }
        return null;
    }

    //-------------GET DESCRIPTION
    public String getdescription(long l1){
        db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[] {KEY_ID,KEY_ITEM,KEY_IMAGE,KEY_BARCODE,KEY_DESCRIPTION,KEY_QUANTITY,KEY_PRICE},
                KEY_ID+"="+l1, null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            String description = cursor.getString(4);
            return  description;
        }
        return null;
    }
//------------------GET QUANTITY

    public String getquantity(long l1){
        db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[] {KEY_ID,KEY_ITEM,KEY_IMAGE,KEY_BARCODE,KEY_DESCRIPTION,KEY_QUANTITY,KEY_PRICE},
                KEY_ID+"="+l1, null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            String quantity = cursor.getString(5);
            return  quantity;
        }
        return null;
    }
    //----------------------------GET PRICE
    public String getprice(long l1){
        db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[] {KEY_ID,KEY_ITEM,KEY_IMAGE,KEY_BARCODE,KEY_DESCRIPTION,KEY_QUANTITY,KEY_PRICE},
                KEY_ID+"="+l1, null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            String price = cursor.getString(5);
            return  price;
        }
        return null;
    }

}







