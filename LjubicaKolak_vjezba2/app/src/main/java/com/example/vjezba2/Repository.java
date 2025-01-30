package com.example.vjezba2;

import com.google.gson.annotations.SerializedName;

public class Repository {
    @SerializedName("name")
    private String name;
    @SerializedName("owner")
    private Owner owner;
    @SerializedName("stargazers_count")
    private Integer stargazers_count;

    public Repository(Owner owner, String name, Integer stargazers_count) {
        this.owner = owner;
        this.name = name;
        this.stargazers_count = stargazers_count;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStargazers_count() {
        return stargazers_count;
    }

    public void setStargazers_count(Integer stargazers_count) {
        this.stargazers_count = stargazers_count;
    }

}
