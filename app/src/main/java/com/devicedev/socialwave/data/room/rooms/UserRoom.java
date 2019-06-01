package com.devicedev.socialwave.data.room.rooms;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.devicedev.socialwave.data.room.dao.UserDao;
import com.devicedev.socialwave.data.room.entities.UserEntity;

public class UserRoom {

    private UserDao userDao;

    public UserRoom(UserDao userDao) {
        this.userDao = userDao;
    }

    public LiveData<UserEntity> get() {
        return userDao.get();
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
