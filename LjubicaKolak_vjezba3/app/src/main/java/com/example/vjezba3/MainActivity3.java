package com.example.vjezba3;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.HandlerCompat;
import androidx.room.Room;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class MainActivity3 extends AppCompatActivity {

    private EditText noteTitleEditText;
    private EditText noteDescriptionEditText;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private Handler handler = HandlerCompat.createAsync(Looper.getMainLooper());
    AppDatabase db;
    Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "zabiljeske").build();
        int noteId = getIntent().getIntExtra("note_id", -1);

        noteTitleEditText = findViewById(R.id.editName);
        noteDescriptionEditText = findViewById(R.id.editDescription);
        Button saveButton = findViewById(R.id.editButton);
        Button deleteButton = findViewById(R.id.deleteButton);

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                note = db.noteDao().getNoteById(noteId);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (note != null) {
                            noteTitleEditText.setText(note.getName());
                            noteDescriptionEditText.setText(note.getDescription());
                        }
                    }
                });
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String updatedTitle = noteTitleEditText.getText().toString();
                String updatedDescription = noteDescriptionEditText.getText().toString();

                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        if (note != null) {
                            note.setName(updatedTitle);
                            note.setDescription(updatedDescription);
                            db.noteDao().update(note);
                        }
                        Intent intent = new Intent(MainActivity3.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = null;
                if (note != null) {
                    builder = new AlertDialog.Builder(MainActivity3.this);
                    builder.setMessage("Are you sure you want delete this note ?");
                    builder.setTitle("Alert !");
                    builder.setCancelable(true); //da se zatvori nakon klikanja
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            executorService.execute(new Runnable() {
                                @Override
                                public void run() {
                                    db.noteDao().delete(note);
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(MainActivity3.this, "Note is deleted", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(MainActivity3.this, MainActivity.class);
                                            startActivity(intent);
                                        }
                                    });
                                }
                            });
                        }
                    });
                    builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
                        dialog.cancel();
                    });
                    builder.show();
                }
            }
        });

    }}
