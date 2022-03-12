package com.example.aredoweknow.other_class;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.CursorWindow;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;
import com.example.aredoweknow.databases_folder.DatabaseHandler;
import com.example.aredoweknow.fragments_folder.Home;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class REFRESH {
    DatabaseHandler db;
    ArrayList<GetterSetter> al = new ArrayList<>();
    SearchView sv;

    public void updateArrayList2(Context context) {
        SharedPreferences sf = context.getSharedPreferences("Shopyy", Context.MODE_PRIVATE);
        String final_un = sf.getString("final_username", "");

        Cursor c = null;
        al = new ArrayList<>();
        db = new DatabaseHandler(context, final_un);

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
            SearchView sv = Home.sv_static;

            if (rv != null){
                adapter my = new adapter(context,al);
                rv.setAdapter(my);

                sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        my.getFilter().filter(query);
                        return false;
                    }
                    @Override
                    public boolean onQueryTextChange(String newText) {
                        my.getFilter().filter(newText);
                        return false;
                    }
                });
            }

//            Toast.makeText(context, "REFRESH", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
        }

    }

}
