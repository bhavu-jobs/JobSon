package com.example.bhavinjobanputra.jobson;

import android.util.Log;
import android.widget.ImageView;

/**
 * Created by BhaVin Jobanputra on 08-10-2016.
 */
public class Item_list {

    private String i_name,seller,price;
    private int image_id;


    public int getImage_id() {
        return image_id;
    }

    public void setImage_id(int image_id) {
        this.image_id = image_id;
    }

    public Item_list(int image_id, String i_name, String price, String seller){
        this.i_name = i_name;
        this.image_id = image_id;

        this.price = "Price : " + price;
        this.seller ="Seller : " + seller;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
        Log.d("sfksk",this.price);
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getI_name() {
        return i_name;
    }

    public void setI_name(String i_name) {
        this.i_name = i_name;
    }
}
