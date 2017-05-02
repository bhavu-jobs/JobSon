package com.example.bhavinjobanputra.jobson;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.UiThread;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okio.BufferedSink;

public class UserData extends Fragment {

    EditText emailAddress, name, mobile, address;
    Button button;
    String name_of_customer = "";
    String mobile_num = "";
    String email = "";
    String userAddress = "";
    String order = "http://192.168.22.1/Jobson/order.php";
    User user;
    ProgressDialog progressDialog;

    public UserData() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_data, container, false);
        name = (EditText) view.findViewById(R.id.name);
        mobile = (EditText) view.findViewById(R.id.mobile);
        address = (EditText) view.findViewById(R.id.address);
        button = (Button) view.findViewById(R.id.place);
        emailAddress = (EditText) view.findViewById(R.id.emailAddress);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");
        myRef.orderByChild("email").equalTo(FirebaseAuth.getInstance().getCurrentUser().getEmail()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                user = dataSnapshot.getValue(User.class);
                emailAddress.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                name.setText(user.name);
                mobile.setText(user.mobileNumber);
                address.setText(user.address);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(getContext());
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Processing...");
                progressDialog.show();
                name_of_customer = String.valueOf(name.getText());
                mobile_num = String.valueOf(mobile.getText());
                userAddress = address.getText().toString();
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                email = user.getEmail();
                final String order = "http://192.168.22.1/Jobson/order.php";
                button.setEnabled(false);
                placeOrder();
            }
        });
        return view;
    }

    public void placeOrder() {
        Thread myThread = new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                RequestBody formBody = new FormBody.Builder()
                        .add("email", email)
                        .add("mobile", mobile_num)
                        .add("address", userAddress)
                        .add("name", name_of_customer)
                        .build();
                okhttp3.Request rq = new okhttp3.Request.Builder().url(order).post(formBody).build();
                try {
                    okhttp3.Response response = client.newCall(rq).execute();
                    final String strResponse = response.body().string();
                    if (strResponse != null) {
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.hide();
                                Toast.makeText(getContext(), "Your Order Has Been Placed!", Toast.LENGTH_SHORT).show();
                                button.setEnabled(true);
                                android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
                                fragmentManager.beginTransaction().replace(R.id.default_content, new Home_Default()).commit();
                            }
                        });
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        myThread.start();
    }
}