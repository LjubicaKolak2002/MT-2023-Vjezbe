package com.example.vjezba3;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "zabiljeske")
public class Note {

    public Note(String name, String description){
        this.name = name;
        this.description = description;
    }


    @PrimaryKey (autoGenerate = true)
    public  int nid;

    @ColumnInfo (name="note_name")
    public String name;

    @ColumnInfo(name="note_description")
    public String description;

    public int getId() {
        return nid;
    }

    public String getName() { return name;};
    public String getDescription() {return description;}
    void setName(String name) {
        this.name = name;
    }

    void setDescription(String description) {
        this.description = description;
    }
}
