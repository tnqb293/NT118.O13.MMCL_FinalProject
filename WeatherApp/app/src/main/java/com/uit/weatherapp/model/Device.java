package com.uit.weatherapp.model;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Device {
    @SerializedName("attributes")
    public JsonObject attributes;

}
