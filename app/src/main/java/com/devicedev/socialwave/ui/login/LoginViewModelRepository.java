package com.devicedev.socialwave.ui.login;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.devicedev.socialwave.data.room.MyDatabase;
import com.devicedev.socialwave.data.room.dao.UserDao;
import com.devicedev.socialwave.data.room.entities.UserEntity;
import com.devicedev.socialwave.data.api.responses.UserTokenResponse;
import com.devicedev.socialwave.data.utils.ServiceGenerator;
import com.devicedev.socialwave.data.api.clients.UserClient;
import com.devicedev.socialwave.ui.utils.ViewModelResponse;
import com.devicedev.socialwave.data.utils.ErrorUtils;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModelRepository {

    private static final String TAG = LoginViewModelRepository.class.getSimpleName();

    private ViewModelResponse responseCallback;

    private MutableLiveData<UserTokenResponse> userTokenResponseMutableLiveData;

    private UserClient userClient;


    /* ROOM */

    private MyDatabase myDatabase;

    private UserDao userDao;

    private LiveData<UserEntity> lastUser;


    public LoginViewModelRepository(Application application, ViewModelResponse response) {

        this.responseCallback = response;

        userClient = ServiceGenerator.getInstance().getUserClient();

        userTokenResponseMutableLiveData = new MutableLiveData<>();

        myDatabase = MyDatabase.getInstance(application.getApplicationContext());

        userDao = myDatabase.userDao();

        lastUser = userDao.get();


    }

    public LiveData<UserEntity> getLastUser() {
        return lastUser;
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

                UserEntity userEntityResponse = userTokenResponse.getUserEntity();

                if(lastUser.getValue() != null)
                    delete(lastUser.getValue());

                userEntityResponse.setId(null);

                insert(userTokenResponse.getUserEntity());

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



    public void insert(UserEntity user) {
        new InsertUserTask(userDao).execute(user);
    }

    public void update(UserEntity user) {
        new UpdateUserTask(userDao).execute(user);
    }

    public void delete(UserEntity user) {
        new DeleteUserTask(userDao).execute(user);
    }

    private class InsertUserTask extends AsyncTask<UserEntity, Void, Void> {

        private UserDao userDao;

        public InsertUserTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(UserEntity... users) {
            userDao.insert(users[0]);

            return null;
        }
    }

    private class UpdateUserTask extends AsyncTask<UserEntity, Void, Void> {

        private UserDao userDao;

        public UpdateUserTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(UserEntity... users) {
            userDao.update(users[0]);

            return null;
        }
    }

    private class DeleteUserTask extends AsyncTask<UserEntity, Void, Void> {

        private UserDao userDao;

        public DeleteUserTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(UserEntity... users) {
            userDao.delete(users[0]);

            return null;
        }
    }

}
