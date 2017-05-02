package com.example.bhavinjobanputra.jobson;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

/**
 * Created by BhaVin Jobanputra on 08-10-2016.
 */
public class Card_Item_Adapter extends RecyclerView.Adapter<Card_Item_Adapter.MyViewHolder> {

    Fragment a;
    private List<Card_List> itemList;

    public Card_Item_Adapter(Fragment a, List<Card_List> itemList) {
        this.a = a;
        this.itemList = itemList;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card, parent, false);

        return new MyViewHolder(itemView);
    }

    public void onBindViewHolder(MyViewHolder holder, int position) {
        Card_List i_list = itemList.get(position);
        holder.prodcut_id.setText(i_list.getProduct_id());
        holder.size.setText(i_list.getSize());
        holder.brand.setText(i_list.getBrand());
        //holder.price.setText(i_list.getPrice());
        //g.d("Image here",i_list.getImage()+" ImageLoader"+i_list.getImageLoader());
        holder.i_image.setImageUrl(i_list.getImage(), i_list.getImageLoader());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView prodcut_id, size, brand;
        public NetworkImageView i_image;

        public MyViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            i_image = (NetworkImageView) view.findViewById(R.id.image);
            prodcut_id = (TextView) view.findViewById(R.id.pid);
            size = (TextView) view.findViewById(R.id.size_);
            brand = (TextView) view.findViewById(R.id.brand_);
            //price = (TextView) view.findViewById(R.id.price);
        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();
            Card_List item_list = itemList.get(position);
            Intent i = new Intent(a.getContext(), Description_Item.class);
            i.putExtra("product", item_list.getProduct_id());
            a.startActivity(i);
        }
    }
}
