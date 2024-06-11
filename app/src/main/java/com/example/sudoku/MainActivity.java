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

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");

        String str = intent.getStringExtra("score");
        assert str != null;
        if(str.equals("_ _ _")){
            highestScore = 2000;
        }else {
            highestScore = Integer.parseInt(str);
        }

        dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.custom_dialog_box);

        gameScore = dialog.findViewById(R.id.gameScore);

        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(getDrawable(R.drawable.custom_dialog_background));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);

        dialogBtnHome = dialog.findViewById(R.id.dialog_button_home);

        dialogBtnHome.setOnClickListener(v -> {
            try (SQLLiteHelper myDB = new SQLLiteHelper(MainActivity.this)) {
                Log.d("souha", "onCreate: dataBase");
                int x = Integer.parseInt(String.valueOf(gameScore.getText()));
                if (x <= highestScore && name != null) {
                    highestScore = x;
                    myDB.addUser(1, name, highestScore);
                    Log.d("souha", "onCreate: added score"+x);
                }
            }

            dialog.dismiss();
            Intent i = new Intent(MainActivity.this, Home.class);
            startActivity(i);
        });

    }

    public static void showDialog(){
        dialog.show();
        gameScore.setText(String.valueOf(GameView.score));
    }

}