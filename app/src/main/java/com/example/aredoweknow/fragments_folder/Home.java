package com.example.aredoweknow.fragments_folder;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.CursorWindow;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aredoweknow.DatabaseHandler;
import com.example.aredoweknow.GetterSetter;
import com.example.aredoweknow.R;
import com.example.aredoweknow.adapter;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class Home extends Fragment {
    RecyclerView rv;
    String id;
    String name;
    String price;
    String quantity;
    byte[] imagebyte;

    Cursor c;
    DatabaseHandler datahandle;

    ArrayList<GetterSetter> al = new ArrayList<>();
    DatabaseHandler mydb;

    Button click;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        rv = view.findViewById(R.id.recycleViewLinear);
        rv.setHasFixedSize(true);
        RecyclerView.LayoutManager gridLayoutManager = new LinearLayoutManager(getContext());
        //gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rv.setLayoutManager(gridLayoutManager);

        //---Show Items
        displayITEMS();

    return view;
}

    private void displayITEMS() {
        mydb = new DatabaseHandler(getContext());
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
                    price=c.getString(5);
                    quantity=c.getString(4);
                    imagebyte=c.getBlob(2);

                    Bitmap image = BitmapFactory.decodeByteArray(imagebyte, 0 , imagebyte.length);

                    GetterSetter g1=new GetterSetter(id,name,price,image,quantity);
                    al.add(g1);

                    System.out.println(c.getCount() + "---> " + id + " " + name + " " + price + " " + quantity);
                }while (c.moveToNext());
            }
        }

        adapter my = new adapter(getContext(),al);

        rv.setAdapter(my);
    }

}
