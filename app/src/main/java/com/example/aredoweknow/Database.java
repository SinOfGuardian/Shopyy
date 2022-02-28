package com.example.aredoweknow;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "shopyy_db";
    private static final String TABLE_NAME = "account";
    private static final String KEY_ID = "id";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_STORE = "storename";


    SQLiteDatabase db;
    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_USERNAME + " TEXT,"
                + KEY_PASSWORD + " TEXT,"
                + KEY_STORE + " TEXT " + ")";
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


    // code to add the new employee
    public long addaccount(String userNM, String passWD, String storeNM) {
        db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, userNM); // userName
        values.put(KEY_PASSWORD, passWD); // password
        values.put(KEY_STORE, storeNM); // Store Name

       return db.insert(TABLE_NAME, null, values);
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
    //USENAME LOGIN
    public Boolean ifUsernameExist(String username) {
        db = this.getReadableDatabase();
        Boolean result = false;

        Cursor cursor = db.query(TABLE_NAME, new String[] {KEY_USERNAME},
                KEY_USERNAME + "=" + username, null, null, null, null, null);

        if (cursor != null) {
            result = true;
        }
        return result;
    }
    //PASSWORD LOGIN
    public Boolean ifPasswordExist(String password) {
        db = this.getReadableDatabase();
        Boolean result = false;

        Cursor cursor = db.query(TABLE_NAME, new String[] {KEY_USERNAME},
                KEY_PASSWORD + "=" + password, null, null, null, null, null);

        if (cursor != null) {
            result = true;
        }
        return result;
    }


    public boolean ifUsernameExist(String username) {
        db = this.getReadableDatabase();
        boolean result = false;

        Cursor cursor = db.query(TABLE_NAME, new String[] {KEY_USERNAME},
                KEY_USERNAME + "=" + username, null, null, null, null, null);

        if (cursor != null) {
            result = true;
        }
        return result;
    }

    public boolean ifPasswordExist(String password) {
        db = this.getReadableDatabase();
        boolean result = false;

        Cursor cursor = db.query(TABLE_NAME, new String[] {KEY_PASSWORD},
                KEY_PASSWORD + "=" + password, null, null, null, null, null);

        if (cursor != null) {
            result = true;
        }
        return result;
    }

}
