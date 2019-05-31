package com.devicedev.socialwave.data.utils;

import android.util.Log;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

public class ErrorUtils {
    private static final String TAG = ErrorUtils.class.getSimpleName();


    public static Error parseError(Response<?> response) {
        Converter<ResponseBody, Error> converter =
                ServiceGenerator.getRetrofit()
                        .responseBodyConverter(Error.class, new Annotation[0]);

        Error error;

        try {
            error = converter.convert(response.errorBody());
        } catch (IOException e) {
            error = new Error("Internal server error");
        }

        return error;
    }
    public static class Error {

        private String message;

        public Error(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}
