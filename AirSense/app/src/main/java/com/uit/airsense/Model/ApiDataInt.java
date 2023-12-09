package com.uit.airsense.Model;

import com.google.gson.JsonObject;

public class ApiDataInt {
    public int value;
    public ApiDataInt(JsonObject data)
    {
        this.value = data.get("value").getAsInt();
    }
}
