package com.example.sudoku;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    private FirebaseAuth myAuth;
    TextInputEditText editEmail,editPassword;
    Button buttonLog;
    ProgressBar progressBar;
    TextView registerNow;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        myAuth = FirebaseAuth.getInstance();

        editEmail = findViewById(R.id.emailLogin);
        editPassword = findViewById(R.id.passwordLogin);
        buttonLog = findViewById(R.id.loginButton);
        progressBar = findViewById(R.id.progressBarLogin);
        registerNow = findViewById(R.id.registerNow);

        registerNow.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), Register.class);
            startActivity(i);
        });

        buttonLog.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            String email,password;
            email = String.valueOf(editEmail.getText());
            password = String.valueOf(editPassword.getText());

            if(TextUtils.isEmpty(email)){
                Toast.makeText(this,"Enter Email",Toast.LENGTH_LONG).show();
                return;
            }
            if(TextUtils.isEmpty(password)){
                Toast.makeText(this,"Enter Password",Toast.LENGTH_LONG).show();
                return;
            }

            loginUser(email,password);
        });
    }

    private void loginUser(String emailText, String passwordText) {
        myAuth.signInWithEmailAndPassword(emailText,passwordText).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                Intent intent = new Intent(getApplicationContext(),Leadboard.class);
                startActivity(intent);
                finish();
            }
            else {
                Toast.makeText(Login.this, "Authentication failed.",
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}