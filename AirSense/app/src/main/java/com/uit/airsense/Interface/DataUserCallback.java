package com.uit.airsense.Interface;

public interface DataUserCallback {
    void onSuccess(String id, String username, String email, long createOn);
    void onFailure(String errorMessage);
}
