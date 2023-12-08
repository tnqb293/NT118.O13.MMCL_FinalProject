package com.uit.airsense.Model;

import com.google.gson.annotations.SerializedName;

public class Temperature {
    @SerializedName("name")
    public String name;
    @SerializedName("rainfall")
    public TemperatureDataFloat rainfall;
    @SerializedName("manufacturer")
    public TemperatureDataString manufacturer;
    @SerializedName("place")
    public TemperatureDataString place;
    @SerializedName("humidity")
    public TemperatureDataFloat humidity;
    @SerializedName("temperature")
    public TemperatureDataFloat temperature;
    @SerializedName("windDirection")
    public TemperatureDataFloat windDirection;
    @SerializedName("windSpeed")
    public TemperatureDataFloat windSpeed;
}
