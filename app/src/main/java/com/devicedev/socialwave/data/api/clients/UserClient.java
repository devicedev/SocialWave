package com.devicedev.socialwave.data.api.clients;

import com.devicedev.socialwave.data.room.entities.UserEntity;
import com.devicedev.socialwave.data.api.responses.UserTokenResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserClient {

    String HEADER_KEY = "x-token: ";

    @GET("users")
    Call<UserEntity> get(@Header(HEADER_KEY) String token);

    @POST("users/login")
    Call<UserTokenResponse> login(@Body UserEntity userEntity);

    @POST("users/register")
    Call<UserTokenResponse> register(@Body UserEntity userEntity);

    @GET("users/logout")
    Call<Void> logout(@Header(HEADER_KEY) String token);

}
