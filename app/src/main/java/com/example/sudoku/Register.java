package com.example.sudoku;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {

    private FirebaseAuth myAuth;
    TextInputEditText editEmail,editPassword;
    Button buttonReg;
    ProgressBar progressBar;
    TextView loginNow;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Log.d("hamma", "onCreate: Register");
        editEmail = findViewById(R.id.email);
        editPassword = findViewById(R.id.password);
        buttonReg = findViewById(R.id.registerButton);
        progressBar = findViewById(R.id.progressBar);
        loginNow = findViewById(R.id.loginNow);

        loginNow.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), Login.class);
            startActivity(i);
        });

        buttonReg.setOnClickListener(v -> {
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

            myAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Intent intent = new Intent(getApplicationContext(),Leadboard.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
                            Log.d("yacine", "failed");
                            Toast.makeText(Register.this, "Authentication failed.",
                                    Toast.LENGTH_LONG).show();
                        }
                    });
        });

        myAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = myAuth.getCurrentUser();
        if (currentUser != null) {
            Toast.makeText(this,"User already exist",Toast.LENGTH_LONG).show();
            finish();
        }
    }


}