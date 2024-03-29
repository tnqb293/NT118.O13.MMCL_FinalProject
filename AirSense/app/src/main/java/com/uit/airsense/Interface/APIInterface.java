package com.uit.airsense.Interface;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.uit.airsense.Model.Attribute;
import com.uit.airsense.Model.Map;
import com.uit.airsense.Model.RequestPostRealmUser;
import com.uit.airsense.Model.Token;
import com.uit.airsense.Model.UidUser;
import com.uit.airsense.Model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface APIInterface {
    @FormUrlEncoded
    @POST("https://uiot.ixxc.dev/auth/realms/master/protocol/openid-connect/token")
    Call<Token> getTokenAccess(@Field("client_id") String clientId, @Field("username") String username, @Field("password") String password, @Field("grant_type") String type);
    @POST("api/master/user/master/users")
    Call<UidUser> postRealmUserInterface(@Body RequestPostRealmUser user);
    @Headers("Content-Type: application/json")
    @PUT("api/master/user/master/userRoles/{userId}")
    Call<String> setRoles(@Path("userId") String userId, @Body JsonArray body);
    @Headers("Content-Type: application/json")
    @PUT("api/master/user/master/userRealmRoles/{userId}")
    Call<String> setRealmRoles(@Path("userId") String userId, @Body JsonArray body);
    @Headers("Content-Type: application/json")
    @PUT("api/master/user/master/reset-password/{userId}")
    Call<String> updatePassword(@Path("userId") String userId, @Body JsonObject body);
    @GET("api/master/map")
    Call<Map> getMap();
    @GET("api/master/asset/5zI6XqkQVSfdgOrZ1MyWEf")
    Call<Attribute> getTemperatureData();
    @GET("api/master/asset/6iWtSbgqMQsVq8RPkJJ9vo")
    Call<Attribute> getLightData();
    @Headers("Content-Type: application/json")
    @POST("api/master/asset/datapoint/5zI6XqkQVSfdgOrZ1MyWEf/attribute/temperature")
    Call<JsonArray> getChartTemperatureData(@Body JsonObject body);
    @Headers("Content-Type: application/json")
    @POST("api/master/asset/datapoint/5zI6XqkQVSfdgOrZ1MyWEf/attribute/humidity")
    Call<JsonArray> getChartHumidityData(@Body JsonObject body);
    @Headers("Content-Type: application/json")
    @POST("api/master/asset/datapoint/5zI6XqkQVSfdgOrZ1MyWEf/attribute/windSpeed")
    Call<JsonArray> getChartWindSpeedData(@Body JsonObject body);
    @Headers("Content-Type: application/json")
    @POST("api/master/asset/datapoint/5zI6XqkQVSfdgOrZ1MyWEf/attribute/rainfall")
    Call<JsonArray> getChartRainfallData(@Body JsonObject body);
    @GET("api/master/user/user")
    Call<User> getInformationUser();
    @Headers("Content-Type: application/json")
    @POST("api/master/user/query")
    Call<JsonArray> listUser(@Body JsonObject body);
}
