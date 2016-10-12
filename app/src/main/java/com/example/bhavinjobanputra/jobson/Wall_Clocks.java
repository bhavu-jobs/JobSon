package com.example.bhavinjobanputra.jobson;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Wall_Clocks extends Fragment {


    RecyclerView recyclerView;
    private Item_Adapter item_adapter;
    private List<Item_list> item_list = new ArrayList<>();
    TextView textView;
    String title;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_wall__clocks,container,false);
        title = getArguments().getString("title");
        textView = (TextView) view.findViewById(R.id.textView);
        textView.setText(title);
        recyclerView = (RecyclerView) view.findViewById(R.id.rview);
        item_adapter = new Item_Adapter(item_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new Divider(getActivity(),LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(item_adapter);
        preparedata();

        return view;
    }

    public void preparedata(){
        Item_list il = new Item_list(R.drawable.ic_cart,"T-113","98.00","Bhavani Pvt.ltd");
        item_list.add(il);
        il = new Item_list(R.drawable.ic_cart,"T-113","98.00","Bhavani Pvt.ltd");
        item_list.add(il);
        il = new Item_list(R.drawable.ic_cart,"T-114","99.00","Bhavani Pvt.ltd");
        item_list.add(il);
        il = new Item_list(R.drawable.ic_cart,"T-115","99.00","Bhavani Pvt.ltd");
        item_list.add(il);
        il = new Item_list(R.drawable.ic_cart,"T-116","99.00","Bhavani Pvt.ltd");
        item_list.add(il);
        il = new Item_list(R.drawable.ic_cart,"T-117","99.00","Bhavani Pvt.ltd");
        item_list.add(il);
        il = new Item_list(R.drawable.ic_cart,"T-118    ","99.00","Bhavani Pvt.ltd");
        item_list.add(il);
        item_adapter.notifyDataSetChanged();
    }
}
