package com.example.bhavinjobanputra.jobson;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Sign_Up extends AppCompatActivity {

    Button Sign_Up_Btn;
    TextView Log_in_text;
    EditText email_input;
    EditText password_input;
    EditText user_name;
    ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    int i=0;

    //On Create
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign__up);
        Sign_Up_Btn = (Button) findViewById(R.id.sign_up_btn);
        Log_in_text = (TextView) findViewById(R.id.signin_link);
        email_input = (EditText) findViewById(R.id.user_email_id);
        user_name = (EditText) findViewById(R.id.user_name);
        password_input = (EditText) findViewById(R.id.user_password);

        Sign_Up_Btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                signup();
            }
        });

        Log_in_text.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent login = new Intent(Sign_Up.this,Log_In.class);
                startActivity(login);
            }
        });
    }

    public void signup(){
        Log.d("Sign Up ","Inn Sign Up Method");

        if(!validate()){
            onSignupFailed();
            return;
        }

        Sign_Up_Btn.setEnabled(false);
        firebaseAuth = FirebaseAuth.getInstance();

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

       //String name = user_name.getText().toString();
        String email = email_input.getText().toString();
        String password = password_input.getText().toString();
        progressBar.setVisibility(View.VISIBLE);

        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(Sign_Up.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if(!task.isSuccessful()){
                    Toast.makeText(getBaseContext(),"Authentication Failed please try again",Toast.LENGTH_SHORT).show();
                    Sign_Up_Btn.setEnabled(true);
                }
                else{
                    onsignupsuccess();
                }
            }
        });
    }

    public boolean validate(){
        String name = user_name.getText().toString();
        String email = email_input.getText().toString();
        String password = password_input.getText().toString();
        boolean valid = true;

        if(name.isEmpty()){
            user_name.setError("Name can't be null.");
            valid=false;
        }
        else
            user_name.setError(null);

        if(email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            email_input.setError("Enter proper email address");
            valid=false;
        }
        else
            email_input.setError(null);

        if(password.isEmpty() || password.length() < 3 || password.length() > 10){
            password_input.setError("Password must be of length between 3 and 10");
            valid = false;
        }
        else
            password_input.setError(null);

        return valid;
    }

    public void onSignupFailed(){
        Toast.makeText(getBaseContext(),"Please enter proper details..",Toast.LENGTH_SHORT).show();
        Sign_Up_Btn.setEnabled(true);
    }

    public void onsignupsuccess(){
        Toast.makeText(getBaseContext(),"Sign Up success",Toast.LENGTH_SHORT).show();
        Intent signup_activity = new Intent(Sign_Up.this,Main_Activity.class);
        startActivity(signup_activity);
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
}
