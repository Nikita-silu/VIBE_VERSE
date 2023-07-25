package com.example.vibeverse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

public class registrartion extends AppCompatActivity {
    EditText name,username,password,reenterpass;
    TextView signup,login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_registrartion);
        name=findViewById(R.id.editTextTextPassword3);
        username=findViewById(R.id.editTextTextPassword4);
        password=findViewById(R.id.editTextTextPassword5);
        reenterpass=findViewById(R.id.editTextTextPassword6);
        signup=findViewById(R.id.signup1);
        login=findViewById(R.id.login1);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(registrartion.this,login.class);
                startActivity(intent);
                finish();
            }
        });
    }
}