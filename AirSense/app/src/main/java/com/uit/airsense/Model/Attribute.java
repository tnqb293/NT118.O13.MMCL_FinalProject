package com.uit.airsense.Model;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

public class Attribute {
    @SerializedName("name")
    public String name;
    @SerializedName("attributes")
    public JsonObject attribute;
}
