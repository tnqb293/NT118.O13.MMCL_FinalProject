package com.uit.weatherapp.model;
import com.google.gson.annotations.SerializedName;


public class DataListUser {
    @SerializedName("realm")
    private String realm;

    @SerializedName("realmId")
    private String realmId;

    @SerializedName("id")
    private String id;

    @SerializedName("email")
    private String email;

    @SerializedName("enabled")
    private boolean enabled;

    @SerializedName("createdOn")
    private long createdOn;

    @SerializedName("serviceAccount")
    private boolean serviceAccount;

    @SerializedName("username")
    private String username;

    // Getters and Setters

    public String getRealm() {
        return realm;
    }

    public void setRealm(String realm) {
        this.realm = realm;
    }

    public String getRealmId() {
        return realmId;
    }

    public void setRealmId(String realmId) {
        this.realmId = realmId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public long getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(long createdOn) {
        this.createdOn = createdOn;
    }

    public boolean isServiceAccount() {
        return serviceAccount;
    }

    public void setServiceAccount(boolean serviceAccount) {
        this.serviceAccount = serviceAccount;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
