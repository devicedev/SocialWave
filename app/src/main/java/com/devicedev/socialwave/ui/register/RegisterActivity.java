package com.devicedev.socialwave.ui.register;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.devicedev.socialwave.R;

public class RegisterActivity extends AppCompatActivity {

    private EditText nameEditText;

    private EditText emailEditText;

    private EditText passwordEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);



    }
}
