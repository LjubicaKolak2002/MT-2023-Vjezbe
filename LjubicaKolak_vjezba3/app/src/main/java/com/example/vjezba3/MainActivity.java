package com.example.vjezba3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.HandlerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private Handler handler = HandlerCompat.createAsync(Looper.getMainLooper());
    AppDatabase db;
    FloatingActionButton buttonAdd;
    RecyclerView recyclerView;
    CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "zabiljeske").build();

        buttonAdd = findViewById(R.id.addNewNote);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewNote();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                //pozadinski thread
                List<Note> notes = db.noteDao().getAll();
                handler.post(new Runnable() {
                    // Glavna (UI) nit
                    public void run() {
                        adapter = new CustomAdapter(notes, new CustomAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(int position) {
                                int selectedNoteId = notes.get(position).getId();
                                openMainActivity3(selectedNoteId);
                            }
                        });
                        recyclerView.setAdapter(adapter); //nakon dohvacanja
                    }
                });
            }
        });
    }

    private void openMainActivity3(int noteId) {
        Intent intent = new Intent(MainActivity.this, MainActivity3.class);
        intent.putExtra("note_id", noteId);
        startActivity(intent);
    }

    public void addNewNote() {
        Intent intent = new Intent(MainActivity.this, MainActivity2.class);
        startActivity(intent);
    }
}
