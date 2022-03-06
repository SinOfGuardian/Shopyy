package com.example.aredoweknow;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWindow;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.aredoweknow.fragments_folder.Home;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class REFRESH {
    DatabaseHandler db;
    ArrayList<GetterSetter> al = new ArrayList<>();

//    public REFRESH(){
//
//    }

    public void updateArrayList2(Context context) {

        Cursor c = null;
        al = new ArrayList<>();
        db = new DatabaseHandler(context);

        try {
            @SuppressLint("DiscouragedPrivateApi") Field field = CursorWindow.class.getDeclaredField("sCursorWindowSize");
            field.setAccessible(true);
            field.set(c, 20 * 1024 * 1024); //the 100MB is the new size
        } catch (Exception e) {
            e.printStackTrace();
        }

        c=db.getData();
        if(c.getCount()>0){
            if(c.moveToFirst()){
                do {
                    String id=c.getString(0);
                    String name=c.getString(1);
                    byte[] imagebyte=c.getBlob(2);

                    String barcode=c.getString(3);
                    String description =c.getString(4);
                    String quantity=c.getString(5);
                    String price=c.getString(6);

                    Bitmap image = BitmapFactory.decodeByteArray(imagebyte, 0 , imagebyte.length);

                    GetterSetter g1=new GetterSetter(id,name,price,image,quantity,description,barcode);
                    al.add(g1);

                }while (c.moveToNext());
            }
        }
        try {
            RecyclerView rv = Home.rv_static;
            if(rv != null){
                adapter my = new adapter(context,al);
                rv.setAdapter(my);
                Toast.makeText(context, "REFRESH", Toast.LENGTH_SHORT).show();
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
