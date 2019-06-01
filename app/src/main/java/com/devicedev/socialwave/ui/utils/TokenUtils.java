package com.devicedev.socialwave.ui.utils;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class TokenUtils {
    private static final String TAG = TokenUtils.class.getSimpleName();
    


    public static boolean isValid(String token) {
        if (token == null) {
            return false;
        }

        try {
            long exp = Integer.parseInt(new JSONObject(JWTUtils.get(token, 1)).getString("exp"));

            long now = System.currentTimeMillis() / 1000L;

            if (exp <= now)
                return false;

        } catch (JSONException e) {

            e.printStackTrace();

            return false;
        }

        return true;

    }

}
