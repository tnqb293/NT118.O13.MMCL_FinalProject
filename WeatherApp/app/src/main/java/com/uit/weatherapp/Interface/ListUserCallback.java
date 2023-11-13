package com.uit.weatherapp.Interface;

import com.google.gson.JsonArray;

public interface ListUserCallback {
    void onSuccess(JsonArray listUser);
    void onFailure(String errorMessage);
}
