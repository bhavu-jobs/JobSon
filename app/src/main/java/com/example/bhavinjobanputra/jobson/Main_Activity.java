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
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Main_Activity extends AppCompatActivity {

    int i=0;
    Toolbar toolbar;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    FirebaseAuth auth;
    FirebaseAuth.AuthStateListener authListener;
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
    }
}
