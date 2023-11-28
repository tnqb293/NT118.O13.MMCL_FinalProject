package com.uit.weatherapp.Interface;



public interface TokenCallback {
    void onSuccess(String token);
    void onFailure(String errorMessage);
}
