package com.uit.weatherapp.model;

import com.google.gson.annotations.SerializedName;

public class RequestPostRealmUser {
    public String realm;
    public String realmId;
    public String id;
    public String email;
    public boolean enabled;
    public String createdOn;
    public boolean serviceAccount;
    public String username;

    public RequestPostRealmUser(String email, String username) {
        this.email = email;
        this.username = username;

        this.enabled = true;
        this.serviceAccount = false;

    }
}
