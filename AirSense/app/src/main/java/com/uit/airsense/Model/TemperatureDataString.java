package com.uit.airsense.Model;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

public class TemperatureDataString {
    public String value;
    public TemperatureDataString(JsonObject data)
    {
        this.value = data.get("value").getAsString();
    }

}
