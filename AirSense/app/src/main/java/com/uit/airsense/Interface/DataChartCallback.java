package com.uit.airsense.Interface;

import com.google.gson.JsonArray;

public interface DataChartCallback {
    void onSuccess(JsonArray data);
    void onFailure(String errorMessage);
}
