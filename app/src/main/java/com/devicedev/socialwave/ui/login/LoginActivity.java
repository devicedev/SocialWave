package com.devicedev.socialwave.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.devicedev.socialwave.R;
import com.devicedev.socialwave.data.api.responses.UserTokenResponse;
import com.devicedev.socialwave.data.room.entities.UserEntity;
import com.devicedev.socialwave.ui.utils.SharedPreferencesTokenUtils;
import com.devicedev.socialwave.ui.utils.ViewModelResponse;
import com.devicedev.socialwave.ui.main.MainActivity;
import com.devicedev.socialwave.ui.register.RegisterActivity;
import com.devicedev.socialwave.ui.utils.TokenUtils;
import com.devicedev.socialwave.ui.utils.Validator;

import es.dmoral.toasty.Toasty;

public class LoginActivity extends AppCompatActivity implements ViewModelResponse {
    private static final String TAG = LoginActivity.class.getSimpleName();

    public static final String EXTRA_TOKEN = "com.devicedev.socialwave.ui.login.EXTRA_TOKEN";

    private static final Integer SHOW_SPLASH_SCREEN = 1 * 1000;

    public static final String PREFERENCES = "Social_Wave";

    public static final String TOKEN_KEY = "jwt_token";




    private ConstraintLayout rootLayout;

    private ConstraintSet constraintSetLogin = new ConstraintSet();

    private ProgressBar progressBar;

    private Button loginButton;

    private Button signUpButton;

    private Button forgetPasswordButton;

    private EditText emailEditText;

    private EditText passwordEditText;


    private LoginViewModel loginViewModel;

    private static String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


        token = SharedPreferencesTokenUtils.get(getSharedPreferences(LoginActivity.PREFERENCES, MODE_PRIVATE));

//        BUG isValid
        final Boolean tokenIsValid = TokenUtils.isValid(token);
        Log.d(TAG, "onCreate: "+tokenIsValid);

        if(!tokenIsValid){
            loginViewModel = ViewModelProviders.of(this,new LoginViewModelFactory(getApplication(),this)).get(LoginViewModel.class);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(tokenIsValid.booleanValue()){

                    startMainActivity();

                } else {

                    initialize();

                    showLoginScreen();

                    toggleProgressBar(View.INVISIBLE);

                    loginViewModel.getUserTokenResponse().observe(LoginActivity.this, new Observer<UserTokenResponse>() {
                        @Override
                        public void onChanged(UserTokenResponse userTokenResponse) {
                            if(userTokenResponse == null)
                                return;

                            toggleProgressBar(View.INVISIBLE);

                            token = userTokenResponse.getToken();

                            SharedPreferencesTokenUtils.save(getSharedPreferences(LoginActivity.PREFERENCES, MODE_PRIVATE),token);

                            Log.d(TAG, "onChanged: "+SharedPreferencesTokenUtils.get(getSharedPreferences(LoginActivity.PREFERENCES,MODE_PRIVATE)));


                            startMainActivity();


                        }
                    });
                    loginViewModel.getUserEntityLiveData().observe(LoginActivity.this, new Observer<UserEntity>() {
                        @Override
                        public void onChanged(UserEntity userEntity) {
                            Log.d(TAG, "onChanged: " + userEntity);
                            if(userEntity == null)
                                return;
                            Log.d(TAG, "onChanged: " + userEntity.getId());
                        }
                    });

                    loginButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            String email = emailEditText.getText().toString().trim();

                            String password = passwordEditText.getText().toString().trim();


                            if(!Validator.Login.email(emailEditText,email) || !Validator.Login.password(passwordEditText,password)){
                                return;
                            }
                            toggleProgressBar(View.VISIBLE);




                            loginViewModel.login(email,password);

                        }
                    });

                    signUpButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            startRegisterActivity();

                        }
                    });



                }

            }
        },SHOW_SPLASH_SCREEN);



    }

    @Override
    public void onSuccess(String message) {
        Toasty.success(this,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(String message) {
        Toasty.error(this,message,Toast.LENGTH_SHORT).show();

        toggleProgressBar(View.INVISIBLE);

    }

    private void startMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);

        intent.putExtra(EXTRA_TOKEN,token);

        startActivity(intent);

        finish();

    }
    private void startRegisterActivity(){
        Intent intent = new Intent(this, RegisterActivity.class);

        startActivity(intent);

        finish();

    }
    private void initialize(){
        rootLayout = findViewById(R.id.rootLayout);

        constraintSetLogin.clone(this,R.layout.activity_start_login);

        progressBar = findViewById(R.id.progressBar);

        loginButton = findViewById(R.id.loginButton);

        signUpButton = findViewById(R.id.signUpButton);

        forgetPasswordButton = findViewById(R.id.forgetPasswordButton);

        emailEditText = findViewById(R.id.emailEditText);

        passwordEditText = findViewById(R.id.passwordEditText);


    }
    private void showLoginScreen(){
        TransitionManager.beginDelayedTransition(rootLayout);

        constraintSetLogin.applyTo(rootLayout);

    }
    private void toggleProgressBar(int visibility){

        progressBar.setVisibility(visibility);

        if(visibility == View.VISIBLE){

            loginButton.setEnabled(false);

        } else {
            loginButton.setEnabled(true);

        }


    }

}
