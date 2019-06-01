package com.devicedev.socialwave.ui.login;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.devicedev.socialwave.data.room.MyDatabase;
import com.devicedev.socialwave.data.room.rooms.UserRoom;
import com.devicedev.socialwave.data.room.entities.UserEntity;
import com.devicedev.socialwave.data.api.responses.UserTokenResponse;
import com.devicedev.socialwave.data.utils.ServiceGenerator;
import com.devicedev.socialwave.data.api.clients.UserClient;
import com.devicedev.socialwave.ui.utils.ViewModelResponse;
import com.devicedev.socialwave.data.utils.ErrorUtils;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModelRepository {

    private static final String TAG = LoginViewModelRepository.class.getSimpleName();

    private ViewModelResponse responseCallback;

    private MutableLiveData<UserTokenResponse> userTokenResponseMutableLiveData;

    private UserClient userClient;


    /* ROOM */

    private UserRoom userRoom;

    private LiveData<UserEntity> lastUser;


    public LoginViewModelRepository(Application application, ViewModelResponse response) {

        this.responseCallback = response;

        userClient = ServiceGenerator.getInstance().getUserClient();

        userTokenResponseMutableLiveData = new MutableLiveData<>();

        MyDatabase myDatabase = MyDatabase.getInstance(application.getApplicationContext());

        userRoom = new UserRoom(myDatabase.userDao());

        lastUser = userRoom.get();


    }

    public LiveData<UserTokenResponse> getUserTokenResponse() {
        return userTokenResponseMutableLiveData;
    }

    public void login(final UserEntity userEntity){

        Call<UserTokenResponse> call = userClient.login(userEntity);

        call.enqueue(new Callback<UserTokenResponse>() {
            @Override
            public void onResponse(Call<UserTokenResponse> call, Response<UserTokenResponse> response) {
                if(!response.isSuccessful()){

                    responseCallback.onError(ErrorUtils.parseError(response).getMessage());

                    return;
                }
                UserTokenResponse userTokenResponse = response.body();

                UserEntity userEntity = userTokenResponse.getUserEntity();

                if(lastUser.getValue() != null)
                    userRoom.delete(lastUser.getValue());

                userRoom.insert(userEntity);

                userTokenResponseMutableLiveData.postValue(userTokenResponse);

                responseCallback.onSuccess("Successful login");


            }

            @Override
            public void onFailure(Call<UserTokenResponse> call, Throwable t) {
                t.printStackTrace();
                if(t instanceof IOException){

                    responseCallback.onError("No network");

                } else {

                    responseCallback.onError("Conversion error");

                }
            }
        });
    }



}
