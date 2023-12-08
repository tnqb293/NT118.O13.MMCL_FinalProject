package com.uit.airsense.API;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.uit.airsense.Interface.APIInterface;
import com.uit.airsense.Interface.DataTemperatureCallback;
import com.uit.airsense.Interface.TokenCallback;
import com.uit.airsense.Interface.UidCallback;
import com.uit.airsense.Model.GlobalVars;
import com.uit.airsense.Model.Map;
import com.uit.airsense.Model.RequestPostRealmUser;
import com.uit.airsense.Model.Temperature;
import com.uit.airsense.Model.TemperatureDataFloat;
import com.uit.airsense.Model.TemperatureDataString;
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
    public static void getTemperature(DataTemperatureCallback callback)
    {
        Call<Temperature> call = apiData.getTemperatureData();
        call.enqueue(new Callback<Temperature>() {
            @Override
            public void onResponse(Call<Temperature> call, Response<Temperature> response) {
                if(response.isSuccessful())
                {
                    Temperature data = response.body();
                    TemperatureDataFloat humidity = data.humidity;
                    TemperatureDataFloat temperature = data.temperature;
                    TemperatureDataFloat rainfall = data.rainfall;
                    TemperatureDataFloat windDirection = data.windDirection;
                    TemperatureDataFloat windSpeed = data.windSpeed;
                    TemperatureDataString manufacturer = data.manufacturer;
                    TemperatureDataString place = data.place;
                    String nameDevice = data.name;
                    callback.onSuccess(temperature, humidity, windSpeed, windDirection, rainfall, place, manufacturer, nameDevice);
                    Log.d("Retrofit", "Call data Temperature Success");
                }
                else
                {
                    try {
                        String errorBody = response.errorBody().string();
                        callback.onFailure(errorBody);
                        Log.e("Retrofit", "Error response: " + errorBody);
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Temperature> call, Throwable t) {
                Log.e("Retrofit", "Network error: " + t.getMessage());
            }
        });
    }
    public static void getMap() {
        Call<Map> call = apiData.getMap();
        call.enqueue(new Callback<Map>() {
            @Override
            public void onResponse(Call<Map> call, Response<Map> response) {
                if (response.isSuccessful())
                {
                    Map.setMapObj(response.body());
                    Log.d("Retrofit", "Call Map Success ");
                }
                else {
                    try {
                        String errorBody = response.errorBody().string();
                        Log.e("Retrofit", "Error response: " + errorBody);
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<Map> call, Throwable t) {
                Log.e("Retrofit", "Network error: " + t.getMessage());
            }
        });

    }
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
