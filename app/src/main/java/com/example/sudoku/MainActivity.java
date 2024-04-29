package com.example.sudoku;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    FirebaseFirestore firebaseFirestore;
    Button dialogBtnReplay, dialogBtnHome;
    static Dialog dialog;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.custom_dialog_box);

        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(getDrawable(R.drawable.custom_dialog_background));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);

        dialogBtnReplay = dialog.findViewById(R.id.dialog_button_replay);
        dialogBtnHome = dialog.findViewById(R.id.dialog_button_home);

        dialogBtnReplay.setOnClickListener(v -> dialog.dismiss());

        dialogBtnHome.setOnClickListener(v -> {
            dialog.dismiss();
            Intent i = new Intent(MainActivity.this, Home.class);
            startActivity(i);
        });

        firebaseFirestore = FirebaseFirestore.getInstance();
        Map<String,Object> user = new HashMap<>();
        user.put("firstName","Easy");
        user.put("lastName", "Tuto");
        user.put("description","Subscribe");
        firebaseFirestore.collection("users").add(user
            ).addOnSuccessListener(documentReference -> Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show()
                ).addOnFailureListener(e -> Toast.makeText(getApplicationContext(),"Failure",Toast.LENGTH_SHORT).show());

        Log.d("hamma", "onCreate: ");
    }

    public static void showDialog(){
        dialog.show();
        TextView gameScore = dialog.findViewById(R.id.gameScore);
        String string = (String) gameScore.getText() + GameView.score;
        gameScore.setText(string);
    }

}