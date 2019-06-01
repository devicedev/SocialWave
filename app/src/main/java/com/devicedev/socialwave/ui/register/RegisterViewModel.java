package com.devicedev.socialwave.ui.register;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.devicedev.socialwave.data.api.responses.UserTokenResponse;
import com.devicedev.socialwave.data.room.entities.UserEntity;
import com.devicedev.socialwave.ui.utils.ViewModelResponse;

public class RegisterViewModel extends AndroidViewModel {
    private static final String TAG = RegisterViewModel.class.getSimpleName();


    private RegisterViewModelRepository repository;

    private LiveData<UserTokenResponse> userTokenResponse;

    public RegisterViewModel(@NonNull Application application, ViewModelResponse response) {
        super(application);

        repository = new RegisterViewModelRepository(application, response);

        userTokenResponse = repository.getUserTokenResponse();

    }

    public LiveData<UserTokenResponse> getUserTokenResponse() {
        return userTokenResponse;
    }

    public void register(String name, String email, String password, String gender, String birthday) {

        repository.register(new UserEntity(name,email,password,gender,birthday));

    }


}
