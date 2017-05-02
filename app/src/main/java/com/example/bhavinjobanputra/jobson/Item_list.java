package com.example.bhavinjobanputra.jobson;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by BhaVin Jobanputra on 08-10-2016.
 */
public class Item_list
{

    private String product_id,category,size,brand,price,image_url;
    private ImageLoader imageLoader;



    public Item_list(String image, String product_id, String category, String size, String brand, String price,ImageLoader imageLoader)
    {
        this.image_url = image;
        this.category = "Category: "+category;
        this.price = "Price : \u20B9" + price + "/carton";
        this.brand ="Brand : " + brand;
        this.size= "Size: "+size;
        this.product_id= "Job_"+product_id;
        this.imageLoader = imageLoader;
    }

    public String getImage()
    {
        return image_url;
    }
    public void setImage(String image)
    {
        this.image_url = image;
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
    public ImageLoader getImageLoader()
    {
        return imageLoader;
    }

    public void setImageLoader(ImageLoader imageLoader)
    {
        this.imageLoader = imageLoader;
    }


}
