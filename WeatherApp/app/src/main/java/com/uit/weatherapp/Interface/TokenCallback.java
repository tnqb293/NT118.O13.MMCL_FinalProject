package com.uit.weatherapp.Interface;

import com.uit.weatherapp.model.User;

public interface TokenCallback {
    void onSuccess(String token);
    void onFailure(String errorMessage);
}
