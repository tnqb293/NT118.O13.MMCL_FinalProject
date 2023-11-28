package com.uit.weatherapp.model;


import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("id")
    public String id;
    @SerializedName("firstName")
    public String firstName;
    @SerializedName("lastName")
    public String lastName;
    @SerializedName("email")
    public String email;
    @SerializedName("enabled")
    public String enabled;
    @SerializedName("createdOn")
    public String createdOn;
    @SerializedName("serviceAccount")
    public Boolean serviceAccount;
    @SerializedName("username")
    public String username;
}
