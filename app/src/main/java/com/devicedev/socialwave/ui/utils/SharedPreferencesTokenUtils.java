package com.devicedev.socialwave.ui.utils;

import android.content.SharedPreferences;


public class SharedPreferencesTokenUtils {

    public static final String PREFERENCES = "Social_Wave";

    public static final String TOKEN_KEY = "jwt_token";



    public static void save(SharedPreferences sharedPreferences,String token) {
        sharedPreferences
                .edit()
                .putString(TOKEN_KEY, token)
                .apply();

    }
    public static String get(SharedPreferences sharedPreferences){
        return sharedPreferences.getString(TOKEN_KEY, null);

    }
}
