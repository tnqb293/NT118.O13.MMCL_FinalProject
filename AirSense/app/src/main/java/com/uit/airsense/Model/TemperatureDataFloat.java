package com.uit.airsense.Model;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

public class TemperatureDataFloat {
    public float value;
    public TemperatureDataFloat(JsonObject data)
    {
        this.value = data.get("value").getAsFloat();
    }

}
