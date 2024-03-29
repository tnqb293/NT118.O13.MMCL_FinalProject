package com.uit.weatherapp.Interface;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.uit.weatherapp.model.Device;
import com.uit.weatherapp.model.Map;
import com.uit.weatherapp.model.RequestPostRealmUser;
import com.uit.weatherapp.model.ResponsePostRealmUser;
import com.uit.weatherapp.model.TokenAccess;
import com.uit.weatherapp.model.User;

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
    //Call<Token> getToken(@Field("grant_type") String type, @Field("code") String code, @Field("client_id") String client, @Field("redirect_uri") String redirect);
    Call<TokenAccess> getTokenAccess(@Field("client_id") String clientId, @Field("username") String username, @Field("password") String password, @Field("grant_type") String type);
    @GET("api/master/asset/5zI6XqkQVSfdgOrZ1MyWEf")
    Call<Device> getData();
    @POST("api/master/user/master/users")
    Call<ResponsePostRealmUser> postRealmUserInterface(@Body RequestPostRealmUser user);
    @Headers("Content-Type: application/json")
    @PUT("api/master/user/master/userRoles/{userId}")
    Call<String> setRoles(@Path("userId") String userId, @Body JsonArray body);
    @Headers("Content-Type: application/json")
    @PUT("api/master/user/master/userRealmRoles/{userId}")
    Call<String> setRealmRoles(@Path("userId") String userId, @Body JsonArray body);
    @Headers("Content-Type: application/json")
    @PUT("api/master/user/master/reset-password/{userId}")
    Call<String> updatePassword(@Path("userId") String userId, @Body JsonObject body);
    @Headers("Content-Type: application/json")
    @POST("api/master/user/query")
    Call<JsonArray> listUser(@Body JsonObject body);
    @GET("api/master/map")
    Call<Map> getMap();
    @GET("api/master/user/user")
    Call<User> getInformationUser();
    @PUT("/user/{realm}/reset-password/{userId}")
    Call<String> getResetPassword(@Path("userId") String userId, @Body JsonObject data);
}
