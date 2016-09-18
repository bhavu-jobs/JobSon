package com.example.bhavinjobanputra.jobson;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Log_In extends AppCompatActivity {

    Button Log_In_Btn;
    TextView sign_up_text;
    EditText email_input;
    EditText password_input;
    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;
    TextView forgotpass;
    int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() != null){
            Intent main_activity = new Intent(Log_In.this,Main_Activity.class);
            startActivity(main_activity);
        }
        setContentView(R.layout.activity_log__in);
        Log_In_Btn = (Button) findViewById(R.id.log_in_btn);
        sign_up_text =(TextView) findViewById(R.id.sign_up);
        email_input = (EditText) findViewById(R.id.email_input);
        password_input= (EditText) findViewById(R.id.password_input);
        forgotpass = (TextView) findViewById(R.id.forgetpass);

        //Login Method
        Log_In_Btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                login_method();
            }
        });

        //Sign_up method
        sign_up_text.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent sign_up = new Intent(getApplicationContext(),Sign_Up.class);
               // startActivityForResult(intent,REQUEST_SIGNUP);
                startActivity(sign_up);
            }
        });

        forgotpass.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent forgot = new Intent(Log_In.this,Forgot_Pass.class);
                startActivity(forgot);
            }
        });

    }

    String email;
    String password;

    public void login_method(){
        Log.d("Login_in", "Login");

        if(!validate()){
            onloginfailed();
            return;
        }

        Log_In_Btn.setEnabled(false);
        email = email_input.getText().toString();
        password = password_input.getText().toString();
        Log.d("Login",email);
        firebaseAuth = FirebaseAuth.getInstance();
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(Log_In.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if(!task.isSuccessful()){
                    onloginfailed_incorrect();
                }
                else
                    onloginSuccess();
            }
        });
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

    public void onloginSuccess() {

        Toast.makeText(getBaseContext(), "Login Successfull", Toast.LENGTH_LONG).show();
        Log_In_Btn.setEnabled(true);
        Intent main_activity = new Intent(getApplicationContext(),Main_Activity.class);
        startActivity(main_activity);
    }

    public boolean validate(){
        boolean VALID = true;
        email = email_input.getText().toString();
        password = password_input.getText().toString();

        if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            email_input.setError("Enter a valid Email Address");
            VALID = false;
        }else{
            email_input.setError(null);
        }

        if(password.isEmpty() || password.length()<4 || password.length() >10){
            password_input.setError("Password must be between 4 to 10 Characters");
            VALID = false;
        }else{
            password_input.setError(null);
        }

        return VALID;
    }

    public void onloginfailed(){
        Toast.makeText(getBaseContext(),"Login Failed Please Try Again!!",Toast.LENGTH_LONG).show();
        Log_In_Btn.setEnabled(true);
    }

    public void onloginfailed_incorrect(){
        Toast.makeText(getBaseContext(),"Username or password is invalid.",Toast.LENGTH_SHORT).show();
        Log_In_Btn.setEnabled(true);
    }
}
