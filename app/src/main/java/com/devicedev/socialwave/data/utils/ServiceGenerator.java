package com.devicedev.socialwave.data.utils;

import com.devicedev.socialwave.data.api.clients.UserClient;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    //    WIFI
    private static final String BASE_URL = "http://192.168.2.185:5000/api/";
//    private static final String BASE_URL = "http://192.168.43.137:5000/api/";

    private static ServiceGenerator instance;

    private static HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build())
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = builder.build();


    private static UserClient userClient;

    private ServiceGenerator() {

        userClient = retrofit.create(UserClient.class);

    }



    public UserClient getUserClient(){
        return userClient;
    }

    public static ServiceGenerator getInstance(){
        if(instance == null){
            instance = new ServiceGenerator();
        }
        return instance;
    }

    public static Retrofit getRetrofit() {
        return retrofit;
    }

}
