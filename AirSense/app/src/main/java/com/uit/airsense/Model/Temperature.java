package com.uit.airsense.Model;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

public class Temperature {
    public JsonObject rainfall;
    public JsonObject manufacturer;
    public JsonObject place;
    public JsonObject humidity;
    public JsonObject temperature;
    public JsonObject windDirection;
    public JsonObject windSpeed;
    public Temperature(JsonObject data)
    {
        this.rainfall = data.get("rainfall").getAsJsonObject();
        this.place = data.get("place").getAsJsonObject();
        this.manufacturer = data.get("manufacturer").getAsJsonObject();
        this.temperature = data.get("temperature").getAsJsonObject();
        this.humidity = data.get("humidity").getAsJsonObject();
        this.windDirection = data.get("windDirection").getAsJsonObject();
        this.windSpeed = data.get("windSpeed").getAsJsonObject();
    }

}
