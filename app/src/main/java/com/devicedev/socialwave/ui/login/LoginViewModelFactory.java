package com.devicedev.socialwave.ui.login;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.devicedev.socialwave.ui.utils.ViewModelResponse;

public class LoginViewModelFactory implements ViewModelProvider.Factory {

    private Application application;

    private ViewModelResponse response;

    public LoginViewModelFactory(Application application, ViewModelResponse response) {
        this.application = application;
        this.response = response;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new LoginViewModel(application,response);
    }
}
