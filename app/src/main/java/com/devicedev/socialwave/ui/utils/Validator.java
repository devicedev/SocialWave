package com.devicedev.socialwave.ui.utils;

import android.util.Patterns;
import android.widget.EditText;

public class Validator {


    public static class Login {

        public static boolean email(EditText emailEditText, String email) {

            boolean success = true;

            String error = null;

            if (email.isEmpty()) {

                error = "The email field is required!";

                success = false;

            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

                error = "The email address is not valid!";

                success = false;
            }
            emailEditText.setError(error);

            return success;
        }

        public static boolean password(EditText passwordEditText, String password) {

            boolean success = true;

            String error = null;

            if (password.isEmpty()) {

                error = "The password field is required!";

                success = false;

            }
            passwordEditText.setError(error);

            return success;
        }

    }

    public static class Register {

        public static boolean password(EditText passwordEditText, String password) {

            boolean success = true;

            String error = null;

            if (password.isEmpty()) {
                error = "The password field is required!";
            } else if (!PasswordPatterns.DIGIT.matcher(password).matches()) {
                error = "The password must contain at least 1 digit!";
            } else if (!PasswordPatterns.LOWER_CASE.matcher(password).matches()) {
                error = "The password must contain at least 1 lower case character!";
            } else if (!PasswordPatterns.UPPER_CASE.matcher(password).matches()) {
                error = "The password must contain at least 1 upper case character!";
            } else if (!PasswordPatterns.WHITE_SPACES.matcher(password).matches()) {
                error = "The password must not contain white spaces!";
            } else if (!PasswordPatterns.MIN.matcher(password).matches()) {
                error = "The password must contain at least " + PasswordPatterns.MIN_CHARACTERS + " characters!";
            } else if (!PasswordPatterns.MAX.matcher(password).matches()) {
                error = "The password must not be long than " + PasswordPatterns.MAX_CHARACTERS + " characters!";
            }

            passwordEditText.setError(error);

            return success;
        }

    }


}
