package com.example.vibeverse;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ActionMode;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class registrartion extends AppCompatActivity {
    EditText email,username,password,reenterpass;
    TextView signup,login;
    CircleImageView profileimg;
    String emailPattern="[a-zA-z0-9._-]+@[a-z]+\\.+[a-z]+";
    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseStorage storage;
    Uri imageURI;
    String imageuri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_registrartion);
        email = findViewById(R.id.editTextTextPassword3);
        username = findViewById(R.id.editTextTextPassword4);
        password = findViewById(R.id.editTextTextPassword5);
        reenterpass = findViewById(R.id.editTextTextPassword6);
        signup = findViewById(R.id.signup1);
        login = findViewById(R.id.login1);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(registrartion.this, login.class);
                startActivity(intent);
                finish();
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emaile=email.getText().toString();
                String usernamee=username.getText().toString();
                String passworde=password.getText().toString();
                String reenterpasse=reenterpass.getText().toString();
                String status="Hey I'm Using This Application";
                if(TextUtils.isEmpty(emaile)|| TextUtils.isEmpty(usernamee)|| TextUtils.isEmpty(passworde)||TextUtils.isEmpty(reenterpasse)){
                    Toast.makeText(registrartion.this,"Please Enter Valid information",Toast.LENGTH_SHORT).show();
                } else if (!emaile.matches(emailPattern)) {
                  email.setError("Type a valid email here");
                } else if (password.length()<6) {
                    password.setError("password must be greater than 6 character");
                } else if (!password.equals(reenterpasse)) {
                    password.setError("Password doesn't match");

                }else{
                    auth.createUserWithEmailAndPassword(emaile,passworde).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                         if (task.isSuccessful())  {
                             String id=task.getResult().getUser().getUid();
                             DatabaseReference reference=database.getReference().child("user").child(id);
                             StorageReference storageReference=storage.getReference().child("Upload").child(id);
                             if(imageURI!=null){
                                 storageReference.putFile(imageURI).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                     @Override
                                     public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                        if (task.isSuccessful()){
                                            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {
                                                  imageuri=uri.toString();
                                                  User user =new User(id,emaile,usernamee,passworde,reenterpasse,imageuri,status);
                                                  reference.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                      @Override
                                                      public void onComplete(@NonNull Task<Void> task) {
                                                         if (task.isSuccessful()){
                                                             Intent intent=new Intent(registrartion.this,MainActivity.class);
                                                             startActivity(intent);
                                                             finish();
                                                         }else{
                                                             Toast.makeText(registrartion.this,"Error in Creating the user",Toast.LENGTH_SHORT).show();
                                                         }
                                                      }
                                                  });
                                                }
                                            });
                                        }
                                     }
                                 });
                             }

                         }
                        }
                    });
                }

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==10){
            if (data!=null){
                imageURI=data.getData();
                profileimg.setImageURI(imageURI);

            }
        }
    }
}


