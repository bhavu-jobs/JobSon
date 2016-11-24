package com.example.bhavinjobanputra.jobson;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Description_Item extends AppCompatActivity {
    ImageView img;
    TextView p_name;
    TextView p_size;
    TextView price;
    TextView desc;
    Button order;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description__item);
        final String id = getIntent().getExtras().getString("product");
        img = (ImageView) findViewById(R.id.logo);
        p_name = (TextView) findViewById(R.id.p_name);
        p_size = (TextView) findViewById(R.id.p_size);
        price = (TextView) findViewById(R.id.p_price);
        desc = (TextView) findViewById(R.id.p_desc);
        order = (Button) findViewById(R.id.add);
        p_name.setText(id);

        order.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                Intent i = new Intent(getBaseContext(),Main_Activity.class);
                i.putExtra("cart",1);
                startActivity(i);
            }
        });
    }


}
