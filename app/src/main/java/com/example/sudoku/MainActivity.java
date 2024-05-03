package com.example.sudoku;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    Button dialogBtnHome;
    static Dialog dialog;
    @SuppressLint("StaticFieldLeak")
    public static TextView gameScore;
    static int highestScore;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("souha", "onCreate: souha");
        gameScore = dialog.findViewById(R.id.gameScore);
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");

        highestScore = Integer.parseInt(intent.getStringExtra("score"));

        dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.custom_dialog_box);

        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(getDrawable(R.drawable.custom_dialog_background));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);

        dialogBtnHome = dialog.findViewById(R.id.dialog_button_home);

        dialogBtnHome.setOnClickListener(v -> {
            SQLLiteHelper myDB = new SQLLiteHelper(MainActivity.this);
            int x = Integer.parseInt(String.valueOf(gameScore));
            if (x<highestScore){
                highestScore = x;
                myDB.addUser(1,name,highestScore);
            }
            dialog.dismiss();
            Intent i = new Intent(MainActivity.this, Home.class);
            startActivity(i);
        });

    }

    public static void showDialog(){
        dialog.show();
        String string = (String) gameScore.getText() + GameView.score;
        gameScore.setText(string);
    }

}