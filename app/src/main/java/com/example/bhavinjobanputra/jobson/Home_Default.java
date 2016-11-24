package com.example.bhavinjobanputra.jobson;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Home_Default extends Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener
{

    private SliderLayout sliderLayout;
    RecyclerView recyclerView;
    private Item_Adapter item_adapter;
    private List<Item_list> item_list = new ArrayList<>();
    String url = "http://192.168.137.1/Jobson/files.php";
    String files[];
    String image_url;
    String new_url = "http://192.168.137.1/Jobson/tp.php";
    String product_id;
    String price;
    String brand;
    String size;
    String category;
    String image="http://192.168.137.1/Jobson/photos/";
    String slideImageUrl = "http://192.168.137.1/Jobson/images/";
    static ImageLoader imageLoader;
    public Home_Default() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home__default, container, false);
        sliderLayout = (SliderLayout) view.findViewById(R.id.slider);
        imageLoader = MySingleton.getInstance(getContext()).getImageLoader();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        files = response.split(" ");
                        changePictures(files);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        MySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);

        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(3000);
        sliderLayout.addOnPageChangeListener(this);

        recyclerView = (RecyclerView) view.findViewById(R.id.rview);
        item_adapter = new Item_Adapter(this,item_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(item_adapter);
        preparedata();
        return view;
    }

    public void preparedata()
    {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(new_url,null,new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response)
                {
                    try
                    {
                        JSONArray jsonArray = response.getJSONArray("products");
                        for(int i=0;i<jsonArray.length();i++)
                        {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            product_id = jsonObject.getString("product_id");
                            category = jsonObject.getString("category");
                            size = jsonObject.getString("size");
                            brand = jsonObject.getString("brand_name");
                            price = jsonObject.getString("price");
                            image_url = jsonObject.getString("image_url");
                            Item_list il = new Item_list(image+image_url,product_id,category,size,brand,price,imageLoader);
                            item_list.add(il);
                            item_adapter.notifyDataSetChanged();

                        }
                    }
                    catch (JSONException e)
                    {
                        Toast.makeText(getContext(),"Error in JSON",Toast.LENGTH_LONG).show();
                        Log.d("Here",e.getMessage());
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error)
                {
                    Toast.makeText(getContext(),"Error in Volley",Toast.LENGTH_LONG).show();
                    Log.d("Here",error.getMessage());
                }
            });

            MySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
    }

    public void changePictures(String files[])
    {
        for(int i=0;i<files.length;i++)
        {
            TextSliderView textSliderView = new TextSliderView(getActivity());
            textSliderView.image(slideImageUrl+files[i]).setOnSliderClickListener(this);
            sliderLayout.addSlider(textSliderView);
        }
    }
    public void onStop(){
        super.onStop();
        sliderLayout.stopAutoCycle();
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }
}
