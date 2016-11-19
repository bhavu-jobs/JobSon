package com.example.bhavinjobanputra.jobson;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class cart extends Fragment {

    TextView textView;
    TextView price;
    RecyclerView recyclerView;
    private Item_Adapter item_adapter;
    private List<Item_list> item_list = new ArrayList<>();
    Button place;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_cart, container, false);

        textView = (TextView) view.findViewById(R.id.textView);
        price = (TextView) view.findViewById(R.id.cart_price);
        place = (Button) view.findViewById(R.id.place_order);
        recyclerView = (RecyclerView) view.findViewById(R.id.rview);
        item_adapter = new Item_Adapter(this,item_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new Divider(getActivity(),Divider.VERTICAL_LIST));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(item_adapter);
        preparedata();


        place.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                place();
            }
        });

        return view;
    }

    public void preparedata(){

    }

    public void place(){

    }

}
