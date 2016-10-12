package com.example.bhavinjobanputra.jobson;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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

public class Main_Activity extends AppCompatActivity{

    int i = 0;
    Toolbar toolbar;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    FirebaseAuth auth;
    FirebaseAuth.AuthStateListener authListener;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.default_content, new Home_Default()).commit();
        }
        setToolbar();
        auth = FirebaseAuth.getInstance();
        authListener = new FirebaseAuth.AuthStateListener() {
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                Log.d("options", "akhu");
                if (user == null) {
                    Log.d("options", "puru");
                    Intent out = new Intent(Main_Activity.this, Log_In.class);
                    startActivity(out);
                    finish();
                }
            }
        };
        navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(MenuItem menuitem) {
                navigation_selection(menuitem);
            //    Log.d("NAc", "select");
                return true;
            }
        });
    }

    public void navigation_selection(MenuItem menuItem) {

        Fragment fragment = null;
        Class fragmentclass;
        switch (menuItem.getItemId()) {
            case R.id.wallclocks:
                fragmentclass = Wall_Clocks.class;
                break;
            case R.id.home:
                fragmentclass = Home_Default.class;
                break;
            case R.id.familyff:
                fragmentclass = Wall_Clocks.class;
                break;
            case R.id.mandir:
                fragmentclass = Wall_Clocks.class;
                break;
            case R.id.lighting:
                fragmentclass = Wall_Clocks.class;
                break;
            default:
                fragmentclass = Home_Default.class;
        }

        try{
            fragment = (Fragment) fragmentclass.newInstance();
            Bundle bundle = new Bundle();
            bundle.putString("title",menuItem.getTitle().toString());
            fragment.setArguments(bundle);
        }catch (Exception E){
            E.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.default_content, fragment).addToBackStack(menuItem.getTitle().toString()).commit();
        setTitle(menuItem.getTitle());
        drawerLayout.closeDrawers();
    }


    public void setToolbar() {
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
        getMenuInflater().inflate(R.menu.items, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                Log.d("burger", "akhu");
                return true;
            case R.id.cart:
                Intent orderPage = new Intent(this, Order_Page.class);
                startActivity(orderPage);
                return true;
            case R.id.sign_out:
                AlertDialog.Builder alert = new AlertDialog.Builder(Main_Activity.this);
                alert.setTitle("Are you sure want to Logout?");
                alert.setIcon(R.drawable.ic_alert);
                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        auth.signOut();
                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alert.show();

                Log.d("options", "Addhu");

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment currentFragment = fm.findFragmentById(R.id.default_content);
        if(currentFragment instanceof Home_Default) {
            if (i == 0) {
                Toast.makeText(getBaseContext(), "Press Again to exit", Toast.LENGTH_SHORT).show();
                i++;

            } else {
                moveTaskToBack(true);
                i++;
            }
        }
        else{
            super.onBackPressed();
            i = 0;
        }
    }

    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
        //sliderLayout.stopAutoCycle();
    }
}


