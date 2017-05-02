package com.example.bhavinjobanputra.jobson;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class cart extends Fragment {

    TextView totalTextView;
    RecyclerView recyclerView;
    Button place;
    String new_url = "http://192.168.22.1/Jobson/cart.php?email=";
    String product_id;
    String price;
    String brand;
    String size;
    String category;
    String image_url;
    String image = "http://192.168.22.1/Jobson/photos/";
    String order = "http://192.168.22.1/Jobson/order.php?email=";
    String name;
    String email;
    int total = 0;
    ImageLoader imageLoader;
    private Item_Adapter item_adapter;
    private List<Item_list> item_list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_cart, container, false);

        totalTextView = (TextView) view.findViewById(R.id.total);
        place = (Button) view.findViewById(R.id.place_order);
        recyclerView = (RecyclerView) view.findViewById(R.id.rview);
        item_adapter = new Item_Adapter(this,item_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration divider = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST);
        recyclerView.addItemDecoration(divider);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(item_adapter);

        imageLoader = MySingleton.getInstance(getContext()).getImageLoader();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        name = user.getDisplayName();
        email = user.getEmail();
        preparedata();



        place.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                Log.d("Clicked", "Here");
                android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.default_content, new UserData()).commit();
            }

        });
        return view;
    }

    public void preparedata() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(new_url + email, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("products");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        product_id = jsonObject.getString("product_id");
                        category = jsonObject.getString("category");
                        size = jsonObject.getString("size");
                        brand = jsonObject.getString("brand_name");
                        price = jsonObject.getString("price");
                        total += Integer.parseInt(price);
                        image_url = jsonObject.getString("image_url");
                        Item_list il = new Item_list(image + image_url, product_id, category, size, brand, price, imageLoader);
                        item_list.add(il);
                        item_adapter.notifyDataSetChanged();
                    }
                    totalTextView.setText("Total:₹ " + total);
                } catch (JSONException e) {
                    Toast.makeText(getContext(), "No Items In The Cart!", Toast.LENGTH_LONG).show();
                    Log.d("Here", e.getMessage());
                } catch (NumberFormatException ne) {
                    total = 1212;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "No Items In The Cart", Toast.LENGTH_LONG).show();
                Log.d("Here", error.getMessage());
            }
        });

        MySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
    }



}
