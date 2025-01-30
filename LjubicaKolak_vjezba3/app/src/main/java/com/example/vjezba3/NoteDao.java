package com.example.vjezba3;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDao {

    @Query("SELECT * FROM zabiljeske")
    List<Note> getAll();

    @Insert
    void insertAll(Note... notes);

    @Delete
    void delete(Note note);

    @Update
    void update(Note note);

    @Query("SELECT * FROM zabiljeske WHERE nid = :noteId")
    Note getNoteById(int noteId);


}
