package com.uit.airsense.Model;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("id")
    public String id;
    @SerializedName("email")
    public String email;
    @SerializedName("createdOn")
    public long createOn;
    @SerializedName("username")
    public String username;
}
