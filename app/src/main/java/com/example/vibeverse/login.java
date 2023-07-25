package com.example.vibeverse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {
    EditText username,password;
    TextView login,signup;
    FirebaseAuth auth;
    String emailPattern="[a-zA-z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);
        auth=FirebaseAuth.getInstance();
        username=findViewById(R.id.editTextTextUsername);
        password=findViewById(R.id.editTextTextPassword);
        login=findViewById(R.id.login);
        signup=findViewById(R.id.signup);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Name= username.getText().toString();
                String pass=password.getText().toString();
                if((TextUtils.isEmpty(Name))){
                    Toast.makeText(login.this,"Enter the Username",Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(pass)) {
                    Toast.makeText(login.this,"Enter the Password",Toast.LENGTH_SHORT).show();

                } else if (!Name.matches(emailPattern)) {
username.setError("Give Proper Username");
                } else if (password.length()<6) {
                 password.setError("Enter more than six character");
                }else {
                    auth.signInWithEmailAndPassword(Name,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                try{
                                    Intent intent=new Intent(login.this,MainActivity.class) ;
                                    startActivity(intent);
                                    finish();
                                }catch (Exception e){
                                    Toast.makeText(login.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            }  else {
                                Toast.makeText(login.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });
    }
}