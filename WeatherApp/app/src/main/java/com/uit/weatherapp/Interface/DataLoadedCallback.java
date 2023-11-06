package com.uit.weatherapp.Interface;

import com.uit.weatherapp.model.WeatherData;

public interface DataLoadedCallback {
    void onDataLoaded(WeatherData weatherData);
}
