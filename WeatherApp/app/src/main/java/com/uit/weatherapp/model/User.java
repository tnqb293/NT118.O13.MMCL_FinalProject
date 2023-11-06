package com.uit.weatherapp.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {
    public String username;
    public String email;
    public String userId;
    public User() {

    }
    public User(String username, String email, String userId)
    {
        this.username = username;
        this.email = email;
        this.userId = userId;
    }
}
