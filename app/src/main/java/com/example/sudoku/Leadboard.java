package com.example.sudoku;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class Leadboard extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseUser user;
    EditText editUsername;
    Button buttonConnect;
    ArrayList<RecycleModule> recycleModules = new ArrayList<>();
    String[] recycleScores = new String[]{"156","320","450","650","655"};
    /*int[] recycleImages = {R.drawable.avatar_png,R.drawable.avatar_png,R.drawable.avatar_png,
            R.drawable.avatar_png,R.drawable.avatar_png};*/

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_leadboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        auth = FirebaseAuth.getInstance();
        editUsername = findViewById(R.id.editTextUserName);
        buttonConnect = findViewById(R.id.userLabel);
        user = auth.getCurrentUser();
        if(user != null){
            buttonConnect.setText("Logout");
            buttonConnect.setOnClickListener(v -> {
                auth.signOut();
                Log.d("hamma", "user sign out: ");
                buttonConnect.setText("Connect");
            });
        }
        else {
            buttonConnect.setOnClickListener(v -> {
                String name = String.valueOf(editUsername.getText());
                Intent i = new Intent(getApplicationContext(), Register.class);
                startActivity(i);
                finish();
                Log.d("hamma", "onCreate: "+ name);
            });
        }



        RecyclerView recyclerView = findViewById(R.id.myRecyclerView);

        setRecycleModules();

        RV_adapter adapter = new RV_adapter(this,recycleModules);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onResume() {
        super.onResume();
        auth = FirebaseAuth.getInstance();
        editUsername = findViewById(R.id.editTextUserName);
        buttonConnect = findViewById(R.id.userLabel);
        user = auth.getCurrentUser();
        if(user != null){
            buttonConnect.setText("Logout");
            buttonConnect.setOnClickListener(v -> {
                auth.signOut();
                Log.d("hamma", "user sign out: ");
                buttonConnect.setText("Connect");
            });
        } else {
            buttonConnect.setOnClickListener(v -> {
                String name = String.valueOf(editUsername.getText());
                Intent i = new Intent(getApplicationContext(), Register.class);
                startActivity(i);
                finish();
                Log.d("hamma", "onCreate: "+name);
            });
        }
    }

    private void setRecycleModules(){
        String[] userNames = getResources().getStringArray(R.array.userNames);
        for(int i=0;i<userNames.length;i++){
            recycleModules.add(new RecycleModule(userNames[i],recycleScores[i]));
        }
    }
}