package com.uit.weatherapp.API;

import android.util.Log;

import com.google.gson.JsonObject;
import com.uit.weatherapp.GlobalVars;
import com.uit.weatherapp.Interface.APIInterface;
import com.uit.weatherapp.Interface.DataLoadedCallback;
import com.uit.weatherapp.model.Device;
import com.uit.weatherapp.model.TokenAccess;
import com.uit.weatherapp.model.WeatherData;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class APIManager {
    public APIManager()  {

    }
//    public static String rainfall = "default";
    private static final APIClient apiClient = new APIClient();
    private static final APIInterface token_access =apiClient.getTokenAccess().create(APIInterface.class);
    private static final APIInterface data = apiClient.getClient().create(APIInterface.class);

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
                    Log.d("Retrofit", "Value is: " + APIClient.rainfall + " - "  + APIClient.windSpeed + " - " + APIClient.temperature + " - " + APIClient.humidity);
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
    public static void getToken()
    {
        Call<TokenAccess> call = token_access.getTokenAccess(GlobalVars.clientId, GlobalVars.username, GlobalVars.password,GlobalVars.grant_type);
        call.enqueue(new Callback<TokenAccess>() {
            @Override
            public void onResponse(Call<TokenAccess> call, Response<TokenAccess> response) {
                if (response.isSuccessful())
                {
                    TokenAccess data = response.body();
//                    Log.d("Retrofit", "Token is: " + data.access_token);
                    APIClient.UserToken = data.access_token;
                    Log.d("Retrofit", "Token is: " + APIClient.UserToken);
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
            public void onFailure(Call<TokenAccess> call, Throwable t) {
                Log.e("Retrofit", "Network error: " + t.getMessage());

            }
        });
    }
}
