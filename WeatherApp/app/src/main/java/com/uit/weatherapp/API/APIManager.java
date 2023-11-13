package com.uit.weatherapp.API;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.uit.weatherapp.GlobalVars;
import com.uit.weatherapp.Interface.APIInterface;
import com.uit.weatherapp.Interface.DataLoadedCallback;
import com.uit.weatherapp.Interface.ListUserCallback;
import com.uit.weatherapp.Interface.TokenCallback;
import com.uit.weatherapp.Interface.UidCallback;
import com.uit.weatherapp.model.Device;
import com.uit.weatherapp.model.RequestPostRealmUser;
import com.uit.weatherapp.model.ResponsePostRealmUser;
import com.uit.weatherapp.model.TokenAccess;
import com.uit.weatherapp.model.WeatherData;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class APIManager {
    public APIManager()  {

    }

    private static final APIClient apiClient = new APIClient();
    private static final APIInterface token_access =apiClient.getTokenAccess().create(APIInterface.class);
    private static final APIInterface data = apiClient.getClient().create(APIInterface.class);
    private static final APIInterface postRealmUser = apiClient.getClient().create(APIInterface.class);
    private static final APIInterface putUserRoles = apiClient.getClient().create(APIInterface.class);
    private static final APIInterface putRealmRoles =  apiClient.getClient().create(APIInterface.class);
    private static final APIInterface putResetPassword =  apiClient.getClient().create(APIInterface.class);
    private static final APIInterface listUser = apiClient.getClient().create(APIInterface.class);
    public static void listUserFn(JsonObject request, ListUserCallback callback)
    {
        Call<JsonArray> call = listUser.listUser(request);
        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                JsonArray data = response.body();
                callback.onSuccess(data);
                Log.d("Retrofit", "Call List User Success " + data);
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {

            }
        });
    }
    public static void resetPassword(JsonObject request, String userId)
    {
        Call<String> call = putResetPassword.updatePassword(userId, request);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful())
                {
                    Log.d("Retrofit", "Update Password Success");
                }
                else {
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

            }
        });
    }
    public static void setRealmRoles(JsonArray request, String userId)
    {
        Call<String> call = putRealmRoles.setRealmRoles(userId, request);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful())
                {
                    Log.d("Retrofit", "Set Realm Roles Success");
                }
                else {
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
    public static void setRoles(JsonArray request, String userId) {
        Call<String> call = putUserRoles.setRoles(userId, request);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Log.d("Retrofit", "Put user Roles Success");
                    Log.d("Retrofit", "User ID: " + userId);
                } else {
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
        Call<ResponsePostRealmUser> call = postRealmUser.postRealmUserInterface(request);
        call.enqueue(new Callback<ResponsePostRealmUser>() {
            @Override
            public void onResponse(Call<ResponsePostRealmUser> call, Response<ResponsePostRealmUser> response) {
                if(response.isSuccessful())
                {
                    ResponsePostRealmUser responsePostRealmUser = response.body();
                    String uid = responsePostRealmUser.id;
                    callback.onSuccess(uid);
                    Log.d("Retrofit", "Response: " + uid);
                }
                else {
                    try {
                        String errorBody = response.errorBody().string();
                        Log.e("Retrofit", "Error response Create User: " + errorBody);
                        callback.onFailure("Error response: " + errorBody);
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponsePostRealmUser> call, Throwable t) {
                Log.e("Retrofit", "Network error: " + t.getMessage());
            }
        });
    }
    public static void getData(final DataLoadedCallback callback)
    {
        Call<Device> call = data.getData();
        call.enqueue(new Callback<Device>() {
            @Override
            public void onResponse(Call<Device> call, Response<Device> response) {
                if (response.isSuccessful())
                {
                    Device device = response.body();
//                    DataWeather dataWeather = new DataWeather(device);

                    JsonObject jsonObject = device.attributes;
                    JsonObject jObj = jsonObject.getAsJsonObject("rainfall");
                    String rainfall = jObj.get("value").toString();
                    jObj = jsonObject.getAsJsonObject("humidity");
                    String humidity = jObj.get("value").toString();
                    jObj = jsonObject.getAsJsonObject("temperature");
                    String temperature = jObj.get("value").toString();
                    jObj = jsonObject.getAsJsonObject("windSpeed");
                    String windSpeed = jObj.get("value").toString();
                    WeatherData weatherData = new WeatherData(humidity, temperature, rainfall, windSpeed);
                    callback.onDataLoaded(weatherData);
//                    Log.d("Retrofit", "Value is: " + APIClient.rainfall + " - "  + APIClient.windSpeed + " - " + APIClient.temperature + " - " + APIClient.humidity);
                }
                else {
                    try {
                        String errorBody = response.errorBody().string();
                        Log.e("Retrofit", "Error response1: " + errorBody);
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Device> call, Throwable t) {
                Log.e("Retrofit", "Network error: " + t.getMessage());

            }
        });
    }
    public static void getToken(String username, String password, TokenCallback callback)
    {
        Call<TokenAccess> call = token_access.getTokenAccess(GlobalVars.clientId, username, password, GlobalVars.grant_type);
        call.enqueue(new Callback<TokenAccess>() {
            @Override
            public void onResponse(Call<TokenAccess> call, Response<TokenAccess> response) {
                if (response.isSuccessful())
                {
                    TokenAccess data = response.body();
//                    Log.d("Retrofit", "Token is: " + data.access_token);
                    APIClient.UserToken = data.access_token;
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
            public void onFailure(Call<TokenAccess> call, Throwable t) {
                Log.e("Retrofit", "Network error: " + t.getMessage());
                callback.onFailure("Network error: " + t.getMessage());
            }
        });
    }
}
