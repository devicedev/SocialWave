package com.devicedev.socialwave.ui.utils;

import android.util.Log;
import android.util.Patterns;
import android.widget.EditText;

import com.devicedev.socialwave.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Validator {
    private static final String TAG = Validator.class.getSimpleName();


    public static class Login {

        public static boolean email(EditText emailEditText, String email) {

            String error = null;

            if (email.isEmpty()) {

                error = "The email field is required!";

            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

                error = "The email address is not valid!";

            }
            emailEditText.setError(error);

            return error == null;
        }

        public static boolean password(EditText passwordEditText, String password) {

            String error = null;

            if (password.isEmpty()) {

                error = "The password field is required!";

            }
            passwordEditText.setError(error);

            return error == null;
        }

    }

    public static class Register {


        public static boolean name(EditText nameEditText, String name) {

            String error = null;

            if (name.isEmpty()) {

                error = "The name field is required!";

            }
            nameEditText.setError(error);

            return error == null;
        }

        public static boolean email(EditText emailEditText, String email) {

            String error = null;

            if (email.isEmpty()) {

                error = "The email field is required!";

            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

                error = "The email address is not valid!";

            }
            emailEditText.setError(error);

            return error == null;
        }

        public static boolean password(EditText passwordEditText, String password) {

            String error = null;

            if (password.isEmpty()) {
                error = "The password field is required!";
            } else if (!PasswordPatterns.DIGIT.matcher(password).find()) {
                error = "The password must contain at least 1 digit!";
            } else if (!PasswordPatterns.LOWER_CASE.matcher(password).find()) {
                error = "The password must contain at least 1 lower case character!";
            } else if (!PasswordPatterns.UPPER_CASE.matcher(password).find()) {
                error = "The password must contain at least 1 upper case character!";
//            } else if (!PasswordPatterns.WHITE_SPACES.matcher(password).matches()) {
//                error = "The password must not contain white spaces!";
            } else if (!PasswordPatterns.MIN.matcher(password).matches()) {
                error = "The password must contain at least " + PasswordPatterns.MIN_CHARACTERS + " characters!";
            } else if (!PasswordPatterns.MAX.matcher(password).matches()) {
                error = "The password must not be longer than " + PasswordPatterns.MAX_CHARACTERS + " characters!";
            }

            passwordEditText.setError(error);

            return error == null;
        }

        public static boolean birthday(ViewModelResponse response, String birthday,int minAge) {

            String error = null;

            if (birthday.isEmpty() || birthday.equals("Enter birthday...")) {

                error = "The birthday field is required!";

            } else {
                Calendar calendar = Calendar.getInstance();

                calendar.add(Calendar.YEAR, -minAge);
                try {
                    if (new SimpleDateFormat("yyyy-MM-dd").parse(birthday).after(calendar.getTime())) {
                        error = "You must be at least " + minAge + " years old to register on our platform!";
                    }

                } catch (ParseException e) {

                    error = "Invalid birthday!";

                    Log.d(TAG, "birthday: parseException");

                    e.printStackTrace();
                }
            }
            boolean success = error == null;

            if (!success) {

                response.onError(error);

            }

            return success;
        }

        public static boolean gender(ViewModelResponse response, String gender) {
            String error = null;

            if (gender == null) {

                error = "The gender field is required!";

            }

            boolean success = error == null;

            if (!success) {

                response.onError(error);
            }

            return success;

        }


    }


}
