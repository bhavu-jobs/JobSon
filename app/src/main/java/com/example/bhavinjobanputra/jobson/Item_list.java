package com.example.bhavinjobanputra.jobson;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by BhaVin Jobanputra on 08-10-2016.
 */
public class Item_list {

    private String product_id,category,size,brand,price;
    private int image;




    public Item_list(int image, String product_id, String category, String size, String brand, String price)
    {
        this.image = image;
        this.category = "Category: "+category;
        this.price = "Price : " + price;
        this.brand ="Brand : " + brand;
        this.size= "Size: "+size;
        this.product_id= "Job_"+product_id;
    }

    public int getImage()
    {
        return image;
    }

    public void setImage(int image)
    {
        this.image = image;
    }

    public String getProduct_id(){return  product_id;}

    public void setProduct_id(String product_id){this.product_id = product_id;}

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
        Log.d("sfksk",this.price);
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size= size;
        Log.d("sfksk",this.price);
    }
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
        Log.d("sfksk",this.price);
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
        Log.d("sfksk",this.price);
    }


}
