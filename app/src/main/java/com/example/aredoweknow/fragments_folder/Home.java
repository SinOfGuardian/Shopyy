package com.example.aredoweknow.fragments_folder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.CursorWindow;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aredoweknow.databases_folder.DatabaseHandler;
import com.example.aredoweknow.other_class.GetterSetter;
import com.example.aredoweknow.R;
import com.example.aredoweknow.other_class.adapter;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class Home extends Fragment {
    RecyclerView rv;
    String id;
    String name;
    String price;
    String quantity;
    byte[] imagebyte;
    String description;
    String barcode;

    Cursor c;

//    ArrayList<GetterSetter> al = new ArrayList<>();
    DatabaseHandler mydb;

    Button click;
    private Intent intent;
    public static RecyclerView rv_static;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rv_static = rv;
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        rv = view.findViewById(R.id.recycleViewLinear);
        rv_static = rv;
        rv.setHasFixedSize(true);
        RecyclerView.LayoutManager gridLayoutManager = new LinearLayoutManager(getContext());
        //gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rv.setLayoutManager(gridLayoutManager);


//        SharedPreferences sf = getContext().getSharedPreferences("Shopyy",  Context.MODE_PRIVATE);
//        boolean needRefresh = sf.getBoolean("refresh", false);
//        Toast.makeText(getContext(), String.valueOf(needRefresh), Toast.LENGTH_SHORT).show();

     //   if (needRefresh) {
            //---Show Items
            Handler handler = new Handler();
            handler.postAtTime(new Runnable() {
                @Override
                public void run() {
//                    SharedPreferences.Editor editor = sf.edit();
//                    editor.putBoolean("refresh", false);
//                    editor.apply();
                    updateArrayList();
                    displayITEMS();
                }
            }, 1);
       // }else {

        //}



//        Toast.makeText(getContext(), "Welcome to Home", Toast.LENGTH_SHORT).show();
    return view;
    }

    ArrayList<GetterSetter> al = new ArrayList<>();

    private void updateArrayList() {
        SharedPreferences sf = getContext().getSharedPreferences("Shopyy",  Context.MODE_PRIVATE);
        String final_un = sf.getString("final_username", "");

        al = new ArrayList<>();
        mydb = new DatabaseHandler(getContext(), final_un);

        try {
            @SuppressLint("DiscouragedPrivateApi") Field field = CursorWindow.class.getDeclaredField("sCursorWindowSize");
            field.setAccessible(true);
            field.set(c, 20 * 1024 * 1024); //the 100MB is the new size
        } catch (Exception e) {
            e.printStackTrace();
        }

        c=mydb.getData();
        if(c.getCount()>0){
            if(c.moveToFirst()){
                do {
                    id=c.getString(0);
                    name=c.getString(1);
                    imagebyte=c.getBlob(2);

                    barcode=c.getString(3);
                    description =c.getString(4);
                    quantity=c.getString(5);
                    price=c.getString(6);

                    Bitmap image = BitmapFactory.decodeByteArray(imagebyte, 0 , imagebyte.length);

                    GetterSetter g1=new GetterSetter(id,name,price,image,quantity,description,barcode);
                    al.add(g1);

                    System.out.println(c.getCount() + "---> " + id + " " + name + " " + price + " " + quantity  );

                }while (c.moveToNext());
            }
        }

        displayITEMS();
    }


    public void displayITEMS() {
        adapter my = new adapter(getContext(),al);
        rv.setAdapter(my);
    }

}
