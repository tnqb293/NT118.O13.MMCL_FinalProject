package com.uit.airsense.Interface;

public interface UidCallback {
    void onSuccess(String uid);
    void onFailure(String errorMessage);
}
