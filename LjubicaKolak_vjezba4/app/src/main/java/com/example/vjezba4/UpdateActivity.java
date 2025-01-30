package com.example.vjezba4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class UpdateActivity extends AppCompatActivity {

    Button save, delete;
    EditText godina, predavac, naziv;
    FirebaseDatabase mDatabase;
    DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        godina = findViewById(R.id.editGodina);
        predavac = findViewById(R.id.editPredavac);
        naziv = findViewById(R.id.editNaziv);
        save = findViewById(R.id.button);
        delete = findViewById(R.id.buttonDelete);

        Intent intent = getIntent();
        int position = intent.getIntExtra("position", -1);
        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mDatabase.getReference("predmeti").child(String.valueOf(position));

        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Course selectedCourse = snapshot.getValue(Course.class);
                    if (selectedCourse != null) {
                        fillFields(selectedCourse);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateData();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference dref = FirebaseDatabase.getInstance().getReference("predmeti");
                dref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot childSnapshot: snapshot.getChildren()) {
                            String key = childSnapshot.getKey();
                            if (key.equals(mDatabaseReference.getKey())) {
                                mDatabaseReference.getRef().removeValue();
                            }
                            else if(key.compareTo(mDatabaseReference.getKey()) > 0) {
                                int newKey = Integer.parseInt(key) - 1;
                                DatabaseReference newRef = dref.child(String.valueOf(newKey));
                                newRef.setValue(childSnapshot.getValue());
                                childSnapshot.getRef().removeValue();
                            }
                        }
                        Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(UpdateActivity.this, "Error while deleting", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
    }

    private void fillFields(Course course) {
        naziv.setText(course.getIme());
        godina.setText(String.valueOf(course.getGodina()));
        predavac.setText(course.getPredavac());
    }

    private void updateData() {
        String nazivValue = naziv.getText().toString();
        String godinaValue = godina.getText().toString();
        String predavacValue = predavac.getText().toString();

        HashMap<String, Object> courseData = new HashMap<>();
        courseData.put("ime", nazivValue);
        courseData.put("godina", Integer.parseInt(godinaValue));
        courseData.put("predavac", predavacValue);

        mDatabaseReference.updateChildren(courseData).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(UpdateActivity.this, "Error while updating", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
