package com.uit.airsense.Interface;

public interface DataLightCallback {
    void onSuccess(int brightness, int colourTemperature, String email, boolean onOff, String nameDevice);
    void onFailure(String errorMessage);

}
