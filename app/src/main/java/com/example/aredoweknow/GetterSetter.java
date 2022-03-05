package com.example.aredoweknow;

import android.graphics.Bitmap;

public class GetterSetter {
    String id;
    String name;
    String price;
    Bitmap image;
    String quantity;
    String description;
    String barcode;


    public GetterSetter(String id, String name, String price, Bitmap image, String quan, String desc, String barc){
           this.id = id;
           this.name =  name;
           this.price = price;
           this.image = image;
           this.quantity = quan;
           this.description = desc;
           this.barcode = barc;
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
    public String getDescription() {
        return description;
    }
    public String getBarcode() {
        return barcode;
    }

}
