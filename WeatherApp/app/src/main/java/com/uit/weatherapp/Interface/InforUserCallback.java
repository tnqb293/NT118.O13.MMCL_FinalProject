package com.uit.weatherapp.Interface;

import com.uit.weatherapp.model.User;

public interface InforUserCallback {
    void onSuccess(User user);
    void onFailure(String errorMessage);

}
