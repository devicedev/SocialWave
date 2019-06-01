package com.devicedev.socialwave.ui.register;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.devicedev.socialwave.data.api.clients.UserClient;
import com.devicedev.socialwave.data.api.responses.UserTokenResponse;
import com.devicedev.socialwave.data.room.MyDatabase;
import com.devicedev.socialwave.data.room.entities.UserEntity;
import com.devicedev.socialwave.data.utils.ErrorUtils;
import com.devicedev.socialwave.data.utils.ServiceGenerator;
import com.devicedev.socialwave.data.room.rooms.UserRoom;
import com.devicedev.socialwave.ui.utils.ViewModelResponse;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterViewModelRepository {

    private ViewModelResponse responseCallback;

    private MutableLiveData<UserTokenResponse> userTokenResponseMutableLiveData;

    private UserClient userClient;


    /* Room */

    private UserRoom userRoom;

    private LiveData<UserEntity> lastUser;


    public RegisterViewModelRepository(Application application,ViewModelResponse responseCallback) {

        this.responseCallback = responseCallback;

        userClient = ServiceGenerator.getInstance().getUserClient();

        userTokenResponseMutableLiveData = new MutableLiveData<>();


        MyDatabase myDatabase = MyDatabase.getInstance(application.getApplicationContext());

        userRoom = new UserRoom(myDatabase.userDao());

        lastUser = userRoom.get();

    }

    public LiveData<UserTokenResponse> getUserTokenResponse(){
        return userTokenResponseMutableLiveData;
    }

    public void register(UserEntity userEntity){

        Call<UserTokenResponse> call = userClient.register(userEntity);

        call.enqueue(new Callback<UserTokenResponse>() {
            @Override
            public void onResponse(Call<UserTokenResponse> call, Response<UserTokenResponse> response) {

                if(!response.isSuccessful()){

                    responseCallback.onError(ErrorUtils.parseError(response).getMessage());

                    return;
                }

                UserTokenResponse userTokenResponse = response.body();

                UserEntity registeredUser = userTokenResponse.getUserEntity();

                if(lastUser.getValue() != null){
                    userRoom.delete(lastUser.getValue());
                }

                userRoom.insert(registeredUser);

                userTokenResponseMutableLiveData.postValue(userTokenResponse);

                responseCallback.onSuccess("Successful register");

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
