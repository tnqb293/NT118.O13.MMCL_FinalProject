package com.uit.airsense.Model;

import com.google.gson.JsonObject;

public class Light {
    public JsonObject brightness;
    public JsonObject colourTemperature;
    public JsonObject email;
    public JsonObject onOff;
    public Light(JsonObject data)
    {
        this.brightness = data.get("brightness").getAsJsonObject();
        this.colourTemperature = data.get("colourTemperature").getAsJsonObject();
        this.email = data.get("email").getAsJsonObject();
        this.onOff = data.get("onOff").getAsJsonObject();
    }
}
