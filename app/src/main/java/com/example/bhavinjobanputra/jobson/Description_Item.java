package com.example.bhavinjobanputra.jobson;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.StrictMode;
import android.support.annotation.IntegerRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

public class Description_Item extends AppCompatActivity {
    NetworkImageView img;
    TextView p_id;
    TextView p_size;
    TextView price;
    TextView desc;
    String image_url;
    String image = "http://192.168.22.1/Jobson/photos/";
    String addcart = "http://192.168.22.1/Jobson/insertcart.php?pid=";
    Button order;
    String id;
    String url = "http://192.168.22.1/Jobson/single_product.php?pid=";
    ImageLoader imageLoader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description__item);
        id = getIntent().getExtras().getString("product");
        id = id.substring(4);
        img = (NetworkImageView) findViewById(R.id.logo);
        p_id = (TextView) findViewById(R.id.p_id);
        p_size = (TextView) findViewById(R.id.p_size);
        price = (TextView) findViewById(R.id.p_price);
        desc = (TextView) findViewById(R.id.p_desc);
        order = (Button) findViewById(R.id.add);
        imageLoader = MySingleton.getInstance(getApplicationContext()).getImageLoader();
        p_id.setText("Product Id:" + id);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url + id, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response)
            {
                try {
                    p_size.setText("Size:" + response.getString("size"));
                    price.setText("Price:₹" + response.getString("price") + "/carton");
                    desc.setText("Desctription\n" + response.getString("description"));
                    image_url = response.getString("image_url");
                    img.setImageUrl(image + image_url, imageLoader);
                } catch (JSONException e) {

                    Toast.makeText(getApplicationContext(), "Error in JSON", Toast.LENGTH_SHORT).show();
                    Log.d("here", e.getMessage());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error in VOLLEY", Toast.LENGTH_SHORT).show();
                Log.d("here", error.getMessage());
            }
        });
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String email = user.getEmail();
//Yo
        order.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                StringRequest stringRequest = new StringRequest(addcart + id + "&userid=" + email, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "Product Added to Cart", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Sorry!Try Again", Toast.LENGTH_SHORT).show();
                    }
                });

                MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
            }
        });
    }

    public void finish(View v) {
        this.finish();
    }


}
