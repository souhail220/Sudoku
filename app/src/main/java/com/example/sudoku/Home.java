package com.example.sudoku;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class Home extends AppCompatActivity {

    String userName;
    TextView highestScore;
    int score;
    String[]  item = {"Easy","Medium","Hard"};
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapterItems;

    static String selectedItem;
    SQLLiteHelper myDB;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.output), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        myDB = new SQLLiteHelper(this);
        score = myDB.getHighestScore();

        highestScore = findViewById(R.id.highestScore);
        if(score > 0){
            highestScore.setText(String.valueOf(score));
        }
        EditText name = findViewById(R.id.editTextUserName);
        try {
            userName = name.getText().toString();
        } catch (Exception ignored) {
        }

        autoCompleteTextView = findViewById(R.id.autoCompleteTextView);
        adapterItems = new ArrayAdapter<>(this, R.layout.list_item, item);

        autoCompleteTextView.setAdapter(adapterItems);

        autoCompleteTextView.setOnItemClickListener((adapterView, view, position, id) -> {
            selectedItem = adapterView.getItemAtPosition(position).toString();
            Toast.makeText(Home.this,"Item" + selectedItem,Toast.LENGTH_SHORT).show();
        });



    }

    public void playActivity(View v){
        Intent i = new Intent(this, MainActivity.class);
        i.addCategory("DEFAULT");
        i.putExtra("name",userName);
        i.putExtra("score",String.valueOf(highestScore.getText()));
        startActivity(i);
    }

    public void leadboardActivity(View view){
        Intent i = new Intent(this, Leadboard.class);
        startActivity(i);
    }
}