package com.example.myapplicationgamemaster.auth;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplicationgamemaster.R;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity2 extends AppCompatActivity {
    private ProgressBar ProgressBar;
     private EditText inputName,inputEmail,inputPhone,inputPassword;
     private Button btnRegister;
     private FirebaseAuth firebaseAuth ;
     public Button button;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        // findView
        inputName = findViewById(R.id.inputName);
        inputEmail = findViewById(R.id.inputEmail);
        inputPhone = findViewById(R.id.inputPhone);
        inputPassword = findViewById(R.id.inputPassword);
        ProgressBar = findViewById(R.id.progress_circular);
        getSupportActionBar().hide();


        // firebase
        firebaseAuth = FirebaseAuth.getInstance();

        // onClick
        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(v -> {
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

         signUp(email, password);

    }

    private void  signUp(String email, String Password) {
        ProgressBar.setVisibility(View.VISIBLE);

        firebaseAuth.createUserWithEmailAndPassword(email,Password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        ProgressBar.setVisibility(View.GONE);
                        Toast.makeText(RegisterActivity2.this, "User Created Successfully",Toast.LENGTH_SHORT).show();

                    } else {
                        // handle error
                        ProgressBar.setVisibility(View.GONE);
                        Toast.makeText(RegisterActivity2.this, "Error"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();


                    }
                });
    }
}