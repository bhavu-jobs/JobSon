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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Wall_Clocks extends Fragment
{
    String url= "http://192.168.137.1/Jobson/category.php";
    RecyclerView recyclerView;
    private Item_Adapter item_adapter;
    private List<Item_list> item_list = new ArrayList<>();
    TextView textView;
    String title;
    int choice;
    String product_id;
    String price;
    String brand;
    String size;
    String category;
    String image;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_wall__clocks,container,false);
        title = getArguments().getString("title");
        choice = getArguments().getInt("Choice");
        switch (choice)
        {
            case 1: url=url+"?category=Wall%20Clock";
                break;
            case 2 : url=url+"?category=Family%20Photo%20Frame";break;
            case 3 : url=url+"?category=Mandir";break;
            case 4 : url=url+"?category=Lighting%20Frames";break;
        }
        //Toast.makeText(getContext(),url,Toast.LENGTH_SHORT).show();
        textView = (TextView) view.findViewById(R.id.textView);
        textView.setText(title);
        recyclerView = (RecyclerView) view.findViewById(R.id.rview);
        item_adapter = new Item_Adapter(this,item_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new Divider(getActivity(),Divider.VERTICAL_LIST));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(item_adapter);
        preparedata();

        return view;
    }

    public void preparedata()
    {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url,null,new Response.Listener<JSONObject>()
        {

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

                        //image = jsonObject.getString("image_url");

                        Item_list il = new Item_list(R.drawable.logo,product_id,category,size,brand,price);
                        item_list.add(il);
                    }
                    item_adapter.notifyDataSetChanged();
                }
                catch (JSONException e)
                {
                    Toast.makeText(getContext(),"Error in JSON",Toast.LENGTH_LONG).show();
                    Log.d("Here ly",e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(getContext(),"Error in Volley",Toast.LENGTH_LONG).show();
                Log.d("Volley",error.getMessage());
            }
        });

        MySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
    }
}
