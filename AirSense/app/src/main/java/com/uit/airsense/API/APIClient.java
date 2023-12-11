package com.uit.airsense.API;
import com.uit.airsense.Model.GlobalVars;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class APIClient {
//    public static String UserToken = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJoREkwZ2hyVlJvaE5zVy1wSXpZeDBpT2lHMzNlWjJxV21sRk4wWGE1dWkwIn0.eyJleHAiOjE3MDIzODA0NDksImlhdCI6MTcwMjI5NDA0OSwianRpIjoiZjdmYTY3YmMtNzEzZi00NDZiLTgxNzUtNmZkZWUxYmNjMGQ3IiwiaXNzIjoiaHR0cHM6Ly91aW90Lml4eGMuZGV2L2F1dGgvcmVhbG1zL21hc3RlciIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiIxYmIwZTFkYy03MjYwLTRlNzAtYTkxYi0yNGEzNGFlNjBmZmUiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJvcGVucmVtb3RlIiwic2Vzc2lvbl9zdGF0ZSI6ImFhOTM5YTYwLTVjMzUtNGIyYy1hNGNmLTAwOTAwYzZlOTZmZiIsImFjciI6IjEiLCJhbGxvd2VkLW9yaWdpbnMiOlsiaHR0cHM6Ly91aW90Lml4eGMuZGV2Il0sInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJjcmVhdGUtcmVhbG0iLCJkZWZhdWx0LXJvbGVzLW1hc3RlciIsIm9mZmxpbmVfYWNjZXNzIiwidW1hX2F1dGhvcml6YXRpb24iXX0sInJlc291cmNlX2FjY2VzcyI6eyJvcGVucmVtb3RlIjp7InJvbGVzIjpbIndyaXRlOmxvZ3MiLCJ3cml0ZTphc3NldHMiLCJyZWFkIiwid3JpdGU6YWRtaW4iLCJyZWFkOm1hcCIsInJlYWQ6bG9ncyIsInJlYWQ6YXNzZXRzIiwid3JpdGU6dXNlciIsInJlYWQ6dXNlcnMiLCJ3cml0ZTpydWxlcyIsInJlYWQ6cnVsZXMiLCJyZWFkOmluc2lnaHRzIiwid3JpdGU6YXR0cmlidXRlcyIsIndyaXRlOmluc2lnaHRzIiwicmVhZDphZG1pbiJdfSwiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJwcm9maWxlIGVtYWlsIiwic2lkIjoiYWE5MzlhNjAtNWMzNS00YjJjLWE0Y2YtMDA5MDBjNmU5NmZmIiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJuYW1lIjoibWFpIGN1b25nIiwicHJlZmVycmVkX3VzZXJuYW1lIjoibWFpY3VvbmciLCJnaXZlbl9uYW1lIjoibWFpIiwiZmFtaWx5X25hbWUiOiJjdW9uZyIsImVtYWlsIjoibWFpY3Vvbmc5NzA5QGdtYWlsLmNvbSJ9.HYVwrC5l9_uJZQ2ue_QT9VEG8jTPx1fLwDCKMluBtc4BJ9vAYSIRxwuI_l-nwOfBAaoMMg_2IswuoEg8LBT_waM6H8IICiscBiitW3cUCSZElkHXGH3fggYUl_va7WFjl0K-1Vnjh4ZRbCRzl_sj6A7Bpo-bWlhVqg8TshGcSzFITqpbZ3nu6dBXqsGO5-4k5QmmvmtxqL8oYcPT3bND0BgPIaQLsFEkJZWd1pLjn5IkCWblHWP5zvBD20aiKMJmONhwrPaQll0IY7XaEjrw0kAvx824To-ebA8p2V1BjddvpsgxpoG-o7jrlJsJqntt6YHZWM36wArFK_LYZu5pWw";
    public static String UserToken = "";
    public OkHttpClient getUnsafeOkHttpClient() {
        try {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();

            // Log
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(interceptor);

            // Bear token and Content-Length
            builder.addInterceptor(chain -> {
                Request originalRequest = chain.request();
                Request.Builder requestBuilder = originalRequest.newBuilder()
                        .addHeader("Authorization", "Bearer " + UserToken)
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Accept", "application/json, text/plain, */*");

                // Check if the request has a body, and if so, calculate and set the content length
                if (originalRequest.body() != null) {
                    long contentLength = originalRequest.body().contentLength();
                    requestBuilder.addHeader("Content-Length", String.valueOf(contentLength));
                }

                Request newRequest = requestBuilder.build();
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
                .client(client)
                .build();
    }
}
