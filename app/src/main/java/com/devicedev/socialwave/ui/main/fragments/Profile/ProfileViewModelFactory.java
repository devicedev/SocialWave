package com.devicedev.socialwave.ui.main.fragments.Profile;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.devicedev.socialwave.ui.utils.ViewModelResponse;

public class ProfileViewModelFactory implements ViewModelProvider.Factory {

    private String token;

    private Application application;

    private ViewModelResponse response;

    public ProfileViewModelFactory(String token, Application application, ViewModelResponse response) {
        this.token = token;
        this.application = application;
        this.response = response;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ProfileViewModel(token,application,response);
    }
}
