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
    public void setId(){
        this.id = id;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getPrice(){
        return price;
    }
    public void setPrice(String price){
        this.price = price;
    }

    public String getQuantity(){
        return quantity;
    }
    public void setQuantity(String quan){
        this.quantity = quan;
    }

    public Bitmap getImage(){
        return image;
    }




}
