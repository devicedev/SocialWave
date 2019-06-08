package com.devicedev.socialwave.ui.main.fragments.Profile;

import android.app.Application;
import android.os.SystemClock;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.devicedev.socialwave.data.room.entities.UserEntity;
import com.devicedev.socialwave.ui.utils.ViewModelResponse;

public class ProfileViewModel extends AndroidViewModel {

    private LiveData<UserEntity> userEntityLiveData;

    private ProfileViewModelRepository repository;

    public ProfileViewModel(String token, @NonNull Application application, ViewModelResponse response) {
        super(application);

        repository = new ProfileViewModelRepository(token, application, response);

        userEntityLiveData = repository.getUser();

    }

    public void update(OnUpdateUserListener onUpdateUser) {
        repository.store(onUpdateUser);
    }

    public LiveData<UserEntity> getUserEntityLiveData() {
        return userEntityLiveData;
    }


    public interface OnUpdateUserListener{
        void onUpdateUser();
    }
}
