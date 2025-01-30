package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;

public class Zadatak2a extends AppCompatActivity {

    EditText editText1;
    EditText editText2;
    EditText editText3;
    TextView textView;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zadatak2a);

        editText1 = findViewById(R.id.editTextNumber3);
        editText2 = findViewById(R.id.editTextNumber4);
        editText3 = findViewById(R.id.editTextTextPersonName);
        button = findViewById(R.id.button2);
        textView = findViewById(R.id.textView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String operator = editText3.getText().toString();
                String numberText1 = editText1.getText().toString();
                String numberText2 = editText2.getText().toString();
                int number1 = Integer.parseInt(numberText1);
                int number2 = Integer.parseInt(numberText2);

                if (operator.equals("+")) {
                    textView.setText(String.valueOf(number1 + number2));
                }
                else if (operator.equals("-")) {
                    textView.setText(String.valueOf(number1 - number2));
                }
                else if (operator.equals("*")) {
                    textView.setText(String.valueOf(number1 * number2));
                }
                else if (operator.equals("/")) {
                    if (number2 == 0) {
                        CharSequence message = "Nije dozvoljeno dijeliti s nulom!";
                        int duration = Toast.LENGTH_SHORT;
                        Toast.makeText(Zadatak2a.this, message, duration).show();
                    }
                    else {
                        float result = (float) number1 / number2;
                        textView.setText(String.valueOf(result));
                    }
                }
                else {
                    CharSequence message = "Krivi unos! Trebate unijeti neki od operatora: +, -, *, /!";
                    int duration = Toast.LENGTH_SHORT;
                    Toast.makeText(Zadatak2a.this, message, duration).show();
                }
            }
        });
    }
}
