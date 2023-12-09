package com.uit.airsense.Model;

import com.google.gson.JsonObject;

public class ApiDataBoolean {
    public boolean value;
    public ApiDataBoolean(JsonObject data)
    {
        this.value = data.get("value").getAsBoolean();
    }
}
