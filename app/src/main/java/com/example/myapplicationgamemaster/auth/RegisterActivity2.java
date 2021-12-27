package com.example.myapplicationgamemaster.auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplicationgamemaster.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class RegisterActivity2 extends AppCompatActivity {
    private ProgressBar ProgressBar;
     private EditText inputName,inputEmail,inputPhone,inputPassword;
     private Button signUp;
     private FirebaseAuth firebaseAuth ;
     private FirebaseFirestore firestore;
     public Button button;
     TextView alreadyhaveaccount;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        alreadyhaveaccount=findViewById(R.id.alreadyhaveaccount);
        alreadyhaveaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity2.this,LoginActivity.class));
            }
        });


        // findView
        inputName = findViewById(R.id.inputName);
        inputEmail = findViewById(R.id.inputEmail);
        inputPhone = findViewById(R.id.inputPhone);
        inputPassword = findViewById(R.id.inputPassword);
        ProgressBar = findViewById(R.id.progress_circular);
        getSupportActionBar().hide();



        // firebase
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        // onClick
        signUp = findViewById(R.id.signUp);
        signUp.setOnClickListener(v -> {
            validationData();
        });

    }
    private void validationData(){
        String name = inputName.getText().toString().trim();
        String email = inputEmail.getText().toString().trim();
        String phone = inputPhone.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();

        if (name.isEmpty()){
            Toast.makeText(RegisterActivity2.this, R.string.error_name_register,Toast.LENGTH_SHORT).show();
            inputName.requestFocus();
            return;
        }

        if (email.isEmpty()){
            Toast.makeText(RegisterActivity2.this, "Please Add your email",Toast.LENGTH_SHORT).show();
            inputEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(RegisterActivity2.this, "Please Add Valid email",Toast.LENGTH_SHORT).show();
            inputEmail.requestFocus();
            return;

        }

        if (phone.isEmpty()){
            Toast.makeText(RegisterActivity2.this, "Please Add your Phone",Toast.LENGTH_SHORT).show();
            inputPhone.requestFocus();
            return;
        }
        if (phone.length() < 11){
            Toast.makeText(RegisterActivity2.this, "Phone Should be 11 Character",Toast.LENGTH_SHORT).show();
            inputPhone.requestFocus();
            return;

        }
        if (password.isEmpty()){
            Toast.makeText(RegisterActivity2.this, "Please Add your Password",Toast.LENGTH_SHORT).show();
            inputPassword.requestFocus();
            return;
        }
        if (password.length() < 6){
            Toast.makeText(RegisterActivity2.this, "Please Add your Password",Toast.LENGTH_SHORT).show();
            inputPassword.requestFocus();
            return;

        }

        register(email,password,name,phone);

    }

    private void  register(String email, String password ,String name,String phone) {
        ProgressBar.setVisibility(View.VISIBLE);

        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){

                        // send data to firestore
                        sendData(email,password,phone,name);

                    } else {
                        // handle error
                        ProgressBar.setVisibility(View.GONE);
                        Toast.makeText(RegisterActivity2.this, "Error"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();


                    }
                });
    }

    private void sendData(String email, String password, String phone, String name) {
        long tsLong = System.currentTimeMillis()/1000;
        String timestamp = Long.toString(tsLong);

        HashMap<String,Object> map = new HashMap<>();
        map.put("email", email);
        map.put("password", password);
        map.put("name", name);
        map.put("phone", phone);
        map.put("timestamp",timestamp);
        map.put("isDelete", false);


        // send data

        firestore.collection("users")
                .document()
                .set(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        Toast.makeText(RegisterActivity2.this,"Done",Toast.LENGTH_SHORT).show();
                        ProgressBar.setVisibility(View.GONE);

                        startActivity(new Intent(RegisterActivity2.this, LoginActivity.class));
                        finish();
                    }
                })
                .addOnFailureListener(e -> {
                    ProgressBar.setVisibility(View.GONE);
                    Toast.makeText(RegisterActivity2.this,"Error"+e.getMessage(),Toast.LENGTH_SHORT).show();

                });

    }
}