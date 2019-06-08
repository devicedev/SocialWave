package com.devicedev.socialwave.ui.main.fragments.Profile;

import android.app.Application;
import android.os.SystemClock;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.devicedev.socialwave.R;
import com.devicedev.socialwave.data.api.clients.UserClient;
import com.devicedev.socialwave.data.api.responses.UserResponse;
import com.devicedev.socialwave.data.room.MyDatabase;
import com.devicedev.socialwave.data.room.dao.UserDao;
import com.devicedev.socialwave.data.room.entities.UserEntity;
import com.devicedev.socialwave.data.room.rooms.UserRoom;
import com.devicedev.socialwave.data.utils.ServiceGenerator;
import com.devicedev.socialwave.ui.utils.ViewModelResponse;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileViewModelRepository {

    private static final String TAG = "ProfileViewModelReposo";

    private ViewModelResponse responseCallback;

    private UserClient userClient;

    private String token;

    /* Room */

    private UserRoom userRoom;

    private LiveData<UserEntity> user;

    public ProfileViewModelRepository(String token, Application application, ViewModelResponse responseCallback) {

        this.token = token;

        this.responseCallback = responseCallback;

        userClient = ServiceGenerator.getInstance().getUserClient();

        MyDatabase myDatabase = MyDatabase.getInstance(application.getApplicationContext());

        userRoom = new UserRoom(myDatabase.userDao());

        user = userRoom.get();

    }

    public LiveData<UserEntity> getUser() {
        return user;
    }

    public void store(final ProfileViewModel.OnUpdateUserListener onUpdateUser) {

        Call<UserResponse> call = userClient.get(token);

        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (!response.isSuccessful()) {

                    responseCallback.onError(R.string.err_internal_server_error);

                    return;
                }

                UserEntity updatedUser = response.body().getUserEntity();

                if (user.getValue() != null) {
                    userRoom.delete(user.getValue());
                }

                updatedUser.setUpdatedAt(System.currentTimeMillis());

                userRoom.insert(updatedUser);

                if (onUpdateUser != null)
                    onUpdateUser.onUpdateUser();

                responseCallback.onSuccess(R.string.sing_up);


            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {

                if (onUpdateUser != null)
                    onUpdateUser.onUpdateUser();

                t.printStackTrace();
                if (t instanceof IOException) {

                    responseCallback.onError(R.string.err_no_network);

                } else {

                    responseCallback.onError(R.string.err_conversion_error);

                }
            }
        });

    }
}
