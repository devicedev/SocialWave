package com.devicedev.socialwave.ui.register;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.util.Date;

import com.devicedev.socialwave.R;
import com.devicedev.socialwave.data.api.responses.UserTokenResponse;
import com.devicedev.socialwave.ui.main.MainActivity;
import com.devicedev.socialwave.ui.utils.SharedPreferencesTokenUtils;
import com.devicedev.socialwave.ui.utils.Validator;
import com.devicedev.socialwave.ui.utils.ViewModelResponse;

import java.util.Calendar;

import es.dmoral.toasty.Toasty;

public class RegisterActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, ViewModelResponse {
    private static final String TAG = RegisterActivity.class.getSimpleName();

    private EditText nameEditText;

    private EditText emailEditText;

    private EditText passwordEditText;

    private TextView birthdayTextView;

    private RadioGroup genderRadioGroup;

    private RadioButton maleRadioButton;

    private RadioButton femaleRadioButton;

    private Button signUpButton;

    private Button backButton;

    private ProgressBar progressBar;


    private RegisterViewModel registerViewModel;

    private String token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initialize();

        registerViewModel = ViewModelProviders.of(this, new RegisterViewModelFactory(getApplication(), this)).get(RegisterViewModel.class);

        birthdayTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();

                calendar.add(Calendar.YEAR, -getApplicationContext().getResources().getInteger(R.integer.min_age));

                int day = calendar.get(Calendar.DAY_OF_MONTH);

                int month = calendar.get(Calendar.MONTH);

                int year = calendar.get(Calendar.YEAR);


                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        RegisterActivity.this,
                        R.style.DatePickerDialogApp,
                        RegisterActivity.this,
                        year, month, day);

                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());


                datePickerDialog.show();
            }
        });


        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                toggleProgressBar(View.VISIBLE);

                String name = nameEditText.getText().toString().trim();

                String email = emailEditText.getText().toString().trim();

                String password = passwordEditText.getText().toString().trim();

                String gender = null;

                String birthday = birthdayTextView.getText().toString();


                if (maleRadioButton.isChecked() && !femaleRadioButton.isChecked()) {

                    gender = "male";

                } else if (femaleRadioButton.isChecked() && !maleRadioButton.isChecked()) {

                    gender = "female";

                }
                if (

                        !Validator.Register.name(nameEditText, name) |
                        !Validator.Register.email(emailEditText, email) |
                        !Validator.Register.password(passwordEditText, password) |
                        !Validator.Register.gender(RegisterActivity.this, gender) |
                        !Validator.Register.birthday(RegisterActivity.this, birthday, getApplicationContext().getResources().getInteger(R.integer.min_age))
                ) {
                    toggleProgressBar(View.INVISIBLE);

                    return;
                }

                registerViewModel.register(name,email,password,gender,birthday);


            }
        });

        registerViewModel.getUserTokenResponse().observe(this, new Observer<UserTokenResponse>() {
            @Override
            public void onChanged(UserTokenResponse userTokenResponse) {
                if(userTokenResponse == null)
                    return;

                toggleProgressBar(View.INVISIBLE);

                token = userTokenResponse.getToken();

                SharedPreferencesTokenUtils.save(getSharedPreferences(SharedPreferencesTokenUtils.PREFERENCES,MODE_PRIVATE),token);

                startMainActivity();
            }
        });


    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);

        startActivity(intent);

        finish();
    }

    private void initialize() {

        nameEditText = findViewById(R.id.nameEditText);

        emailEditText = findViewById(R.id.emailEditText);

        passwordEditText = findViewById(R.id.passwordEditText);

        birthdayTextView = findViewById(R.id.birthdayTextView);

        genderRadioGroup = findViewById(R.id.genderRadioGroup);

        maleRadioButton = findViewById(R.id.maleRadioButton);

        femaleRadioButton = findViewById(R.id.femaleRadioButton);

        signUpButton = findViewById(R.id.signUpButton);

        backButton = findViewById(R.id.backButton);

        progressBar = findViewById(R.id.progressBar);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    private void toggleProgressBar(int visibility) {

        progressBar.setVisibility(visibility);

        if (visibility == View.VISIBLE) {

            signUpButton.setEnabled(false);

        } else {
            signUpButton.setEnabled(true);

        }


    }

    @Override
    public void onSuccess(String message) {
        Toasty.success(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(String message) {
        Toasty.error(this, message, Toast.LENGTH_SHORT).show();
        toggleProgressBar(View.INVISIBLE);

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        birthdayTextView.setText(String.format("%04d-%02d-%02d", year, ++month, dayOfMonth));

    }
}
