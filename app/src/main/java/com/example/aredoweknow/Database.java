package com.example.aredoweknow;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "shopyy_db";
    private static final String TABLE_NAME = "account";
    private static final String KEY_ID = "id";
    public static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_STORE = "storename";


    private static final String TABLE2_CRED = "user_credential";

    SQLiteDatabase db;
    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        // -->Table 1
        String query = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_USERNAME + " TEXT,"
                + KEY_PASSWORD + " TEXT,"
                + KEY_STORE + " TEXT " + ")";
        db.execSQL(query);

//         -->Table 2
        String query2 = "CREATE TABLE " + TABLE2_CRED + "("
                + KEY_USERNAME + " TEXT," +  KEY_STORE + " TEXT " + ")";
        db.execSQL(query2);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE2_CRED);
        // Create tables again
        onCreate(db);
    }

    // code to add the new employee
    public long addaccount(String userNM, String passWD, String storeNM) {
        db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, userNM); // userName
        values.put(KEY_PASSWORD, passWD); // password
        values.put(KEY_STORE, storeNM); // Store Name

       return db.insert(TABLE_NAME, null, values);
    }
    // code to update the single employee
    public void updateStore(String username, String storename) {
        db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_STORE, storename); // Store Name

        db.update(TABLE_NAME, values, KEY_USERNAME + "='" + username + "'",null);
        db.close();
    }








    // code to get the single employee
    public String getAccount() {
        db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[] {KEY_ID,KEY_USERNAME,KEY_PASSWORD,KEY_STORE},
                null, null, null, null, null);

        int eId = cursor.getColumnIndex(KEY_ID);
        int usern = cursor.getColumnIndex(KEY_USERNAME);
        int passwd = cursor.getColumnIndex(KEY_PASSWORD);
        int eSN = cursor.getColumnIndex(KEY_STORE);

        String res = "";

        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
            res = res +
                    "Id: "+cursor.getString(eId)+"\n"+
                    "Username: "+cursor.getString(usern)+"\n"+
                    "Password: "+cursor.getString(passwd)+"\n"+
                    "Store name: "+cursor.getString(eSN)+"\n\n";
        }

        db.close();
        return res;
    }
    // code to login

    // code to update the single employee
    public void updateAccount(long l, String name, String age, String city) {
        db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, name); // Employee Name
        values.put(KEY_PASSWORD, age); // Employee Age
        values.put(KEY_STORE, city); // Employee City

        db.update(TABLE_NAME, values, KEY_ID+"="+l,null);
        db.close();

    }

    // Deleting single employee
    public void deleteAccount(long l) {
        db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + " ="+l,null);
    }

    public String getUSERname(long l1){
        db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[] {KEY_ID,KEY_USERNAME,KEY_PASSWORD,KEY_STORE},
                KEY_ID+"="+l1, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            String name = cursor.getString(1);
            return  name;
        }
        return null;
    }

    public String getPASSword(long l1){
        db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[] {KEY_ID,KEY_USERNAME,KEY_PASSWORD,KEY_STORE},
                KEY_ID+"="+l1, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            String age = cursor.getString(2);
            return  age;
        }
        return null;
    }

    public String getstoreNAME(long l1){
        db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[] {KEY_ID,KEY_USERNAME,KEY_PASSWORD,KEY_STORE},
                KEY_ID+"="+l1, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            String store = cursor.getString(3);
            return  store;
        }
        return null;
    }



    //------------------------------IMPORTANT (IN USE)---------------------------------------------
    public boolean ifUsernameExist(String username) {
        db = this.getReadableDatabase();
        boolean result = false;

        Cursor cursor = db.query(TABLE_NAME, new String[] {KEY_ID,KEY_USERNAME,KEY_PASSWORD,KEY_STORE},
                KEY_USERNAME + "='" + username + "'", null, null, null, null, null);
//        Cursor cursor = db.rawQuery("SELECT * FROM myTable WHERE column1 = "+ username, null);

        if (cursor.moveToFirst()) {
                result = true;
        }
//        db.close();
        return result;
    }

    public boolean ifPasswordExist(String password) {
        db = this.getReadableDatabase();
        boolean result = false;

        Cursor cursor = db.query(TABLE_NAME, new String[] {KEY_ID,KEY_USERNAME,KEY_PASSWORD,KEY_STORE},
                KEY_PASSWORD + "='" + password + "'", null, null, null, null, null);

        if (cursor.moveToFirst()) {
            result = true;
        }
        return result;
    }


    public String ifStoreExist(String username) {
        db = this.getReadableDatabase();
        boolean result = false;

        Cursor cursor = db.query(TABLE_NAME, new String[] {KEY_ID,KEY_USERNAME,KEY_PASSWORD,KEY_STORE},
                KEY_USERNAME + "='" + username + "'", null, null, null, null, null);

        String storename = "";
        if (cursor.moveToFirst()) {
            storename = cursor.getString(3);

//            if(!storename.equals("")) {
//                result = true;
//            }
        }
        return storename;
    }



    //------------------------------IMPORTANT (IN USE)---------------------------------------------
    public String[] getCredentials() {
        db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE2_CRED, new String[] {KEY_USERNAME,KEY_STORE},
                null, null, null, null, null);

        String storename[] = {"",""};
        if (cursor.moveToFirst()) {
            storename[0] = cursor.getString(0);
            storename[1] = cursor.getString(1);
            return storename;
        }

        return null;
    }

    // code to add to Credentials
    public long addToCred(String username, String store) {
        db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, username); // userName
        values.put(KEY_STORE, store); // Store Name

        return db.insert(TABLE2_CRED, null, values);
    }

    // code to update the single employee
    public void updateCred(String name, String store, String ref) {
        db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, name); // Employee Name
        values.put(KEY_STORE, store); // Employee City

        db.update(TABLE2_CRED, values,KEY_USERNAME + "='" + ref + "'", null);
        db.close();
    }
    //------------------------------IMPORTANT (IN USE)---------------------------------------------
}
