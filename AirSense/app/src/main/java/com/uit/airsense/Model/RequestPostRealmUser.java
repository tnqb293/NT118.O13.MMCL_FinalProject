package com.uit.airsense.Model;

public class RequestPostRealmUser {

    public String email;
    public boolean enabled;
    public boolean serviceAccount;
    public String username;

    public RequestPostRealmUser(String email, String username) {
        this.email = email;
        this.username = username;

        this.enabled = true;
        this.serviceAccount = false;

    }
}
