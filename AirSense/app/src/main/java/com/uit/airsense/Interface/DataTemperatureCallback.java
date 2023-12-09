package com.uit.airsense.Interface;

public interface DataTemperatureCallback {
    void onSuccess(float temperature, int humidity,
                   float windSpeed, int windDirection,
                   float rainfall, String place, String manufacture, String nameDevice);
    void onFailure(String errorMessage);
}
