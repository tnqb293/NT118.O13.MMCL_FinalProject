package com.uit.airsense.Model;

import com.google.gson.JsonObject;

public class TemperatureDataInt {
    public int value;
    public TemperatureDataInt(JsonObject data)
    {
        this.value = data.get("value").getAsInt();
    }
}
