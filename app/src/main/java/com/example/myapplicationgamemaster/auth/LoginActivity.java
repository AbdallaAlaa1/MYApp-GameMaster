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

import com.example.myapplicationgamemaster.MainActivity;
import com.example.myapplicationgamemaster.R;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    TextView createNewAccount;
    TextView login;
    private EditText etEmail, etPassword;
    private ProgressBar progressBar;
    private Button btnLogin;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        createNewAccount = findViewById(R.id.createNewAccount);

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        //findview
        etEmail = findViewById(R.id.loginEmail);
        etPassword = findViewById(R.id.loginPassword);
        progressBar = findViewById(R.id.loginProgress);

        //firebase
        firebaseAuth =  FirebaseAuth.getInstance();

        findViewById(R.id.signIn).setOnClickListener(v -> {
            validationData();

        });
        findViewById(R.id.createNewAccount).setOnClickListener(v -> {
          Intent intent = new Intent(LoginActivity.this,RegisterActivity2.class);
          startActivity(intent);
        });


    }

    private void validationData() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (email.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Please Add your email", Toast.LENGTH_SHORT).show();
            etEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(LoginActivity.this, "Please Add Valid email", Toast.LENGTH_SHORT).show();
            etEmail.requestFocus();
            return;

        }

        if (password.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Please Add your Password", Toast.LENGTH_SHORT).show();
            etPassword.requestFocus();
            return;
        }
        if (password.length() < 6) {
            Toast.makeText(LoginActivity.this, "Please Add your Password", Toast.LENGTH_SHORT).show();
            etPassword.requestFocus();
            return;

        }

        login(email, password);
    }

    private void login(String email, String password) {
        progressBar.setVisibility(View.VISIBLE);

        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        //ok
                        // shared p
                        getSharedPreferences("userShared",MODE_PRIVATE)
                                .edit()
                                .putBoolean("isLogin",true)
                                .apply();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();


                    }else{
                        //error
                        Toast.makeText(LoginActivity.this, "Error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                    progressBar.setVisibility(View.GONE);

                });


    }
}
