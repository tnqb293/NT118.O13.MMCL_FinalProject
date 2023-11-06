package com.uit.weatherapp.model;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

public class Device {
    @SerializedName("attributes")
    public JsonObject attributes;
}
