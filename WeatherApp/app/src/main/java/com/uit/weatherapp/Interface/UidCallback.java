package com.uit.weatherapp.Interface;

public interface UidCallback {
    void onSuccess(String uid);
    void onFailure(String errorMessage);
}
