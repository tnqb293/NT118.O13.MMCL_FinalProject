package com.uit.airsense.Interface;

import com.uit.airsense.Model.TemperatureDataFloat;
import com.uit.airsense.Model.TemperatureDataInt;
import com.uit.airsense.Model.TemperatureDataString;

public interface DataTemperatureCallback {
    void onSuccess(float temperature, int humidity,
                   float windSpeed, int windDirection,
                   float rainfall, String place, String manufacture, String nameDevice);
    void onFailure(String errorMessage);
}
