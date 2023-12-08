package com.uit.airsense.Interface;

import com.uit.airsense.Model.TemperatureDataFloat;
import com.uit.airsense.Model.TemperatureDataString;

public interface DataTemperatureCallback {
    void onSuccess(TemperatureDataFloat temperature, TemperatureDataFloat humidity,
                   TemperatureDataFloat windSpeed, TemperatureDataFloat windDirection,
                   TemperatureDataFloat rainfall, TemperatureDataString place, TemperatureDataString manufacture, String nameDevice);
    void onFailure(String errorMessage);
}
