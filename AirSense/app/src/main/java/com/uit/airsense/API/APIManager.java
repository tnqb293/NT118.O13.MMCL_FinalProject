package com.uit.airsense.API;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.uit.airsense.Interface.APIInterface;
import com.uit.airsense.Interface.TokenCallback;
import com.uit.airsense.Interface.UidCallback;
import com.uit.airsense.Model.GlobalVars;
import com.uit.airsense.Model.RequestPostRealmUser;
import com.uit.airsense.Model.Token;
import com.uit.airsense.Model.UidUser;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class APIManager {
    private static final APIClient apiClient = new APIClient();
    private static final APIInterface tokenAccess = apiClient.getTokenAccess().create(APIInterface.class);
    private static final APIInterface apiData = apiClient.getClient().create(APIInterface.class);
    public static void resetPassword(JsonObject request, String uid)
    {
        Call<String> call = apiData.updatePassword(uid, request);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful())
                {
                    Log.d("Retrofit", "Update Password Success");
                }
                else
                {
                    int errorCode = response.code();
                    Log.e("Retrofit", "Error response Update Password User. Error Code: " + errorCode);
                    try {
                        String errorBody = response.errorBody().string();
                        Log.e("Retrofit", "Error Body: " + errorBody);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("Retrofit", "Network error: " + t.getMessage());
            }
        });

    }
    public static void setRealmRoles(JsonArray request, String uid)
    {
        Call<String> call = apiData.setRealmRoles(uid, request);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful())
                {
                    Log.d("Retrofit", "Set Realm Roles Success");
                }
                else
                {
                    int errorCode = response.code();
                    Log.e("Retrofit", "Error response Put Roles User. Error Code: " + errorCode);
                    try {
                        String errorBody = response.errorBody().string();
                        Log.e("Retrofit", "Error Body: " + errorBody);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("Retrofit", "Network error: " + t.getMessage());
            }
        });
    }
    public static void setRoles(JsonArray request, String uid)
    {
        Call<String> call = apiData.setRoles(uid, request);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful())
                {
                    Log.d("Retrofit", "Put user Roles Success");
                }
                else
                {
                    int errorCode = response.code();
                    Log.e("Retrofit", "Error response Put Roles User. Error Code: " + errorCode);
                    try {
                        String errorBody = response.errorBody().string();
                        Log.e("Retrofit", "Error Body: " + errorBody);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("Retrofit", "Network error: " + t.getMessage());
            }
        });
    }
    public static void createIdUser(RequestPostRealmUser request, UidCallback callback)
    {
        Call<UidUser> call = apiData.postRealmUserInterface(request);
        call.enqueue(new Callback<UidUser>() {
            @Override
            public void onResponse(Call<UidUser> call, Response<UidUser> response) {
                if(response.isSuccessful())
                {
                    UidUser uid = response.body();
                    callback.onSuccess(uid.id);
                }
                else
                {
                    int errorCode = response.code();
                    Log.e("Retrofit", "Error response Create Id user. Error Code: " + errorCode);
                    try {
                        String errorBody = response.errorBody().string();
                        Log.e("Retrofit", "Error Body: " + errorBody);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<UidUser> call, Throwable t) {
                Log.e("Retrofit", "Network error: " + t.getMessage());
            }
        });
    }
    public static void getToken(String username, String password,TokenCallback callback)
    {
        Call<Token> call = tokenAccess.getTokenAccess(GlobalVars.clientId, username, password, GlobalVars.grant_type);
        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if (response.isSuccessful())
                {
                    Token data = response.body();
                    APIClient.UserToken = data.accessToken;
                    Log.d("Retrofit", "Token is: " + APIClient.UserToken);
                    callback.onSuccess(APIClient.UserToken);
                }
                else {
                    try {
                        String errorBody = response.errorBody().string();
                        APIClient.UserToken = "";
                        Log.e("Retrofit", "Error response: " + errorBody);
                        callback.onFailure("Error response: " + errorBody);
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                Log.e("Retrofit", "Network error: " + t.getMessage());
                callback.onFailure("Network error: " + t.getMessage());
            }
        });
    }
}
