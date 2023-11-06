package com.uit.weatherapp.API;

import com.uit.weatherapp.GlobalVars;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    public static String UserToken = "";
    public static String rainfall ="";
    public static String temperature = "";
    public static String humidity = "";
    public static String windSpeed = "";
    public static OkHttpClient getUnsafeOkHttpClient() {
        try {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();

            //Log
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(interceptor);

            //Bear token
            builder.addInterceptor(chain -> {
                Request newRequest = chain.request()
                        .newBuilder()
                        .addHeader("Authorization", "Bearer " + UserToken)
                        .build();

                return chain.proceed(newRequest);
            });

            return builder.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public Retrofit getClient() {
        OkHttpClient client = getUnsafeOkHttpClient();
        return new Retrofit.Builder()
                .baseUrl(GlobalVars.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }
    public Retrofit getTokenAccess() {
        OkHttpClient client = getUnsafeOkHttpClient();
        return new Retrofit.Builder()
                .baseUrl(GlobalVars.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
