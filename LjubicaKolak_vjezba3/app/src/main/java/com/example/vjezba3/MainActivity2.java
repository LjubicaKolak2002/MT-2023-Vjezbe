package com.example.vjezba3;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.HandlerCompat;
import androidx.room.Room;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity2 extends AppCompatActivity {
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private Handler handler = HandlerCompat.createAsync(Looper.getMainLooper());

    EditText name, description;
    Button button;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "zabiljeske").build();
        name = findViewById(R.id.name);
        description = findViewById(R.id.description);
        button = findViewById(R.id.addNote);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String noteName = name.getText().toString();
                String noteDescription = description.getText().toString();

                if (TextUtils.isEmpty(noteName) || TextUtils.isEmpty(noteDescription)) {

                    Toast.makeText(MainActivity2.this, "You must input name and description", Toast.LENGTH_SHORT).show();
                }
                else {
                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            Note note = new Note(noteName, noteDescription);
                            db.noteDao().insertAll(note);
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    MainActivity2.this.finish();
                                }
                            });
                        }
                    });
                }
            }
        });
    }
}


