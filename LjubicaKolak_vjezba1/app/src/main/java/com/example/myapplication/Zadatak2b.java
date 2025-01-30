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

public class Zadatak2b extends AppCompatActivity {

    EditText editText1;
    EditText editText2;
    EditText editText3;
    TextView textView;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zadatak2b);

        Spinner spinner = (Spinner) findViewById(R.id.spinner3);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.spinner3,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        editText1 = findViewById(R.id.editTextNumber3);
        editText2 = findViewById(R.id.editTextNumber4);
        button = findViewById(R.id.button2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String numberText1 = editText1.getText().toString();
                String numberText2 = editText2.getText().toString();
                float number1 = Float.parseFloat(numberText1);
                float number2 = Float.parseFloat(numberText2);
                String operator = spinner.getSelectedItem().toString();

                if (operator.equals("+")) {
                    printResult(number1 + number2);
                }
                else if (operator.equals("-")) {
                    printResult(number1 - number2);
                }
                else if (operator.equals("*")) {
                    printResult(number1 * number2);
                }
                else if (operator.equals("/")) {
                    if (number2 == 0) {
                        CharSequence message = "Nije dozvoljeno dijeliti s nulom!";
                        int duration = Toast.LENGTH_SHORT;
                        Toast.makeText(Zadatak2b.this, message, duration).show();
                    }
                    else {
                        printResult(number1 / number2);
                    }
                }
            }});
    }
    public void printResult(float result){
        Intent intent = new Intent(this,Zadatak2b_2.class);
        intent.putExtra("result", result);
        startActivity(intent);
    }
}