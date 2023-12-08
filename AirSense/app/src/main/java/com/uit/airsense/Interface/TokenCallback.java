package com.uit.airsense.Interface;

public interface TokenCallback {
    void onSuccess(String token);
    void onFailure(String errorMessage);
}
