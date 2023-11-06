package com.uit.weatherapp.Interface;

import com.uit.weatherapp.model.Device;
import com.uit.weatherapp.model.TokenAccess;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIInterface {
    @FormUrlEncoded
    @POST("https://uiot.ixxc.dev/auth/realms/master/protocol/openid-connect/token")
    //Call<Token> getToken(@Field("grant_type") String type, @Field("code") String code, @Field("client_id") String client, @Field("redirect_uri") String redirect);
    Call<TokenAccess> getTokenAccess(@Field("client_id") String clientId, @Field("username") String username, @Field("password") String password, @Field("grant_type") String type);
    @GET("api/master/asset/5zI6XqkQVSfdgOrZ1MyWEf")
    Call<Device> getData();
}
