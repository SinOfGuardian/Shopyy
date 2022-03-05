package com.example.aredoweknow;

import android.graphics.Bitmap;

public class GetterSetter {
    String id;
    String name;
    String price;
    Bitmap image;
    String quantity;


    public GetterSetter(String id, String name, String price, Bitmap image, String quan){
           this.id = id;
            this.name =  name;
            this.price = price;
            this.image = image;
            this.quantity = quan;

    }
    public String getId(){
        return id;
    }
    public String getName(){
        return name;
    }

    public String getPrice(){
        return price;
    }
    public String getQuantity(){
        return quantity;
    }
    public Bitmap getImage(){
        return image;
    }




}
