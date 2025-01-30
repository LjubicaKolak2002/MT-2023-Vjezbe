package com.example.vjezba2;

import com.google.gson.annotations.SerializedName;

public class Owner {
    @SerializedName("avatar_url")
    private String avatar_url;

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }
}
