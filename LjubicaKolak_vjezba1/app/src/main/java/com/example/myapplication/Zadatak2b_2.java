package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Zadatak2b_2 extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zadatak2b2);

        textView = findViewById(R.id.textView3);
        Intent intent = getIntent();
        float result = intent.getFloatExtra("result", 0);
        textView.setText(String.valueOf(result));
    }
}