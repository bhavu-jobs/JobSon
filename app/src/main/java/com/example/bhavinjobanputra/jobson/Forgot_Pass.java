package com.example.bhavinjobanputra.jobson;

import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Forgot_Pass extends AppCompatActivity {

    EditText email;
    Button submit;
    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot__pass);
        email = (EditText) findViewById(R.id.email_input);
        submit = (Button) findViewById(R.id.submit);
        firebaseAuth = FirebaseAuth.getInstance();
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        submit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                final String input = email.getText().toString();
                if(!validate()){
                    Toast.makeText(getBaseContext(),"Please try again.",Toast.LENGTH_SHORT).show();
                    return;
                }
                 Log.d("gayu","addhu");
                 progressBar.setVisibility(View.VISIBLE);
                 firebaseAuth.sendPasswordResetEmail(input).addOnCompleteListener(new OnCompleteListener<Void>() {
                 @Override
                 public void onComplete(@NonNull Task<Void> task) {
                      if(task.isSuccessful()){
                          Toast.makeText(Forgot_Pass.this, "We have sent you the link to reset the password please reset it and login again.", Toast.LENGTH_SHORT).show();
                          Intent signin = new Intent(Forgot_Pass.this,Log_In.class);
                          startActivity(signin);
                      }
                      else{
                           Toast.makeText(Forgot_Pass.this, "Failed to send the link beacuase of some error please try again later.", Toast.LENGTH_SHORT).show();
                      }
                      progressBar.setVisibility(View.GONE);
                      }
                 });
            }
        });
    }

    public boolean validate(){
        String input = email.getText().toString();
        if(input.isEmpty() ||  !android.util.Patterns.EMAIL_ADDRESS.matcher(input).matches()){
            email.setError("Enter proper email address.");
            return false;
        }
        else {
            email.setError(null);
            return true;
        }
    }
}
