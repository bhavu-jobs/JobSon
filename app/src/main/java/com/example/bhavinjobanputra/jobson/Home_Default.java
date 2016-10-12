package com.example.bhavinjobanputra.jobson;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Home_Default extends Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener{

    private SliderLayout sliderLayout;
    RecyclerView recyclerView;
    private Item_Adapter item_adapter;
    private List<Item_list> item_list = new ArrayList<>();


    public Home_Default() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home__default, container, false);
        sliderLayout = (SliderLayout) view.findViewById(R.id.slider);
        HashMap<String,Integer> image_map = new HashMap<String,Integer>();
        image_map.put("T-109",R.drawable.t);
        image_map.put("T-105",R.drawable.t_one);
        image_map.put("T-93",R.drawable.t_two);

        for(String name : image_map.keySet()){
            TextSliderView textSliderView = new TextSliderView(getActivity());
            textSliderView.image(image_map.get(name)).setOnSliderClickListener(this);
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle().putString("extra",name);
            sliderLayout.addSlider(textSliderView);
        }

        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(3000);
        sliderLayout.addOnPageChangeListener(this);

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
