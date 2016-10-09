package com.example.bhavinjobanputra.jobson;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by BhaVin Jobanputra on 08-10-2016.
 */
public class Item_Adapter extends RecyclerView.Adapter<Item_Adapter.MyViewHolder> {

    private List<Item_list> itemList;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView i_name,price,seller;
        public ImageView i_image;

        public MyViewHolder(View view){
            super(view);
            i_image = (ImageView) view.findViewById(R.id.icon);
            i_name = (TextView) view.findViewById(R.id.p_name);
            price = (TextView) view.findViewById(R.id.price);
            seller = (TextView) view.findViewById(R.id.seller);
         }
    }

    public Item_Adapter(List<Item_list> itemList){
        this.itemList = itemList;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_row, parent, false);

        return new MyViewHolder(itemView);
    }

    public void onBindViewHolder(MyViewHolder holder,int position){
        Item_list i_list = itemList.get(position);
        holder.i_name.setText(i_list.getI_name());
        holder.price.setText(i_list.getPrice());
        holder.seller.setText(i_list.getSeller());
        holder.i_image.setImageResource(i_list.getImage_id());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
