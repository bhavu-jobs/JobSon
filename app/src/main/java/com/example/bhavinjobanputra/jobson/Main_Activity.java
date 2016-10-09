package com.example.bhavinjobanputra.jobson;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main_Activity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    int i=0;
    Toolbar toolbar;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    FirebaseAuth auth;
    FirebaseAuth.AuthStateListener authListener;
    private SliderLayout sliderLayout;
    RecyclerView recyclerView;
    private Item_Adapter item_adapter;
    private List<Item_list> item_list = new ArrayList<>();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_);
        setToolbar();
        auth = FirebaseAuth.getInstance();
        authListener = new FirebaseAuth.AuthStateListener(){
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth){
                FirebaseUser user = firebaseAuth.getCurrentUser();
                Log.d("options","akhu");
                if(user == null){
                    Log.d("options","puru");
                    Intent out = new Intent(Main_Activity.this,Log_In.class);
                    startActivity(out);
                    finish();
                }
            }
        };
        navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            public boolean onNavigationItemSelected(MenuItem menuitem){
                switch (menuitem.getItemId()){

                }
                drawerLayout.closeDrawers();
                return true;
            }
        });

        sliderLayout = (SliderLayout) findViewById(R.id.slider);
        HashMap<String,Integer> image_map = new HashMap<String,Integer>();
        image_map.put("T-109",R.drawable.t);
        image_map.put("T-105",R.drawable.t_one);
        image_map.put("T-93",R.drawable.t_two);

        for(String name : image_map.keySet()){
            TextSliderView textSliderView = new TextSliderView(this);
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

        recyclerView = (RecyclerView) findViewById(R.id.rview);
        item_adapter = new Item_Adapter(item_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new Divider(this,LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(item_adapter);
        preparedata();

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
        il = new Item_list(R.drawable.ic_cart,"T-118","99.00","Bhavani Pvt.ltd");
        item_list.add(il);


        item_adapter.notifyDataSetChanged();
    }


    public void setToolbar(){
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("JOBSON");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.items,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                Log.d("burger","akhu");
                return true;
            case R.id.cart:
                Intent orderPage = new Intent(this, Order_Page.class);
                startActivity(orderPage);
                return true;
            case R.id.sign_out:
                AlertDialog.Builder alert = new AlertDialog.Builder(Main_Activity.this);
                alert.setTitle("Are you sure want to Logout?");
                alert.setIcon(R.drawable.ic_alert);
                alert.setPositiveButton("YES",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog,int which){
                        auth.signOut();
                    }
                });
                alert.setNegativeButton("No",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog,int which){
                        dialog.cancel();
                    }
                });
                alert.show();

                Log.d("options","Addhu");

        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onBackPressed() {
        if(i%2==0){
            Toast.makeText(getBaseContext(),"Press Again to exit",Toast.LENGTH_SHORT).show();
            i++;

        }
        else{
            moveTaskToBack(true);
            i++;
        }
    }

    public void onStart(){
        super.onStart();
        auth.addAuthStateListener(authListener);
    }
    public void onStop(){
        super.onStop();
        if(authListener != null){
            auth.removeAuthStateListener(authListener);
        }
        sliderLayout.stopAutoCycle();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

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
}
