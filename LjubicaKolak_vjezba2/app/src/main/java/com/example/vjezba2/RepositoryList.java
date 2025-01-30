package com.example.vjezba2;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class RepositoryList {
    @SerializedName(value = "items")
    List<Repository> items;

    public RepositoryList() {
        this.items = new ArrayList<Repository>();
    }

}
