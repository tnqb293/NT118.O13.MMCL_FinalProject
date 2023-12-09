package com.uit.airsense.Model;

import com.google.gson.JsonObject;

public class ApiDataString {
    public String value;
    public ApiDataString(JsonObject data)
    {
        this.value = data.get("value").getAsString();
    }

}
