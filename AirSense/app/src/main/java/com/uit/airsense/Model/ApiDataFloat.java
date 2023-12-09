package com.uit.airsense.Model;

import com.google.gson.JsonObject;

public class ApiDataFloat {
    public float value;
    public ApiDataFloat(JsonObject data)
    {
        this.value = data.get("value").getAsFloat();
    }

}
