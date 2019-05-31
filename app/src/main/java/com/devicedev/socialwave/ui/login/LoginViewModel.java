package com.devicedev.socialwave.ui.login;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.devicedev.socialwave.data.room.entities.UserEntity;
import com.devicedev.socialwave.data.api.responses.UserTokenResponse;
import com.devicedev.socialwave.ui.utils.ViewModelResponse;

public class LoginViewModel extends AndroidViewModel {
    private static final String TAG = LoginViewModel.class.getSimpleName();


    private LoginViewModelRepository repository;

    private LiveData<UserTokenResponse> userTokenResponse;

    private LiveData<UserEntity> userEntityLiveData;


    public LoginViewModel(@NonNull Application application, ViewModelResponse response) {
        super(application);

        repository = new LoginViewModelRepository(application,response);

        userTokenResponse = repository.getUserTokenResponse();

        userEntityLiveData = repository.getLastUser();


    }

    public LiveData<UserEntity> getUserEntityLiveData() {
        return userEntityLiveData;
    }

    public LiveData<UserTokenResponse> getUserTokenResponse() {
        return userTokenResponse;
    }

    public void login(String email, String password){

        repository.login(new UserEntity(email,password));


    }

}

