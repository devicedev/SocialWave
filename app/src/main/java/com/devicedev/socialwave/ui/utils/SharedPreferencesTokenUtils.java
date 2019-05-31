package com.devicedev.socialwave.ui.utils;

import android.content.SharedPreferences;

import com.devicedev.socialwave.ui.login.LoginActivity;
import com.devicedev.socialwave.ui.main.MainActivity;

public class SharedPreferencesTokenUtils {

    public static void save(SharedPreferences sharedPreferences,String token) {
        sharedPreferences
                .edit()
                .putString(LoginActivity.TOKEN_KEY, token)
                .apply();

    }
    public static String get(SharedPreferences sharedPreferences){
        return sharedPreferences.getString(LoginActivity.TOKEN_KEY, null);

    }
}
