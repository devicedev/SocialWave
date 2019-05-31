package com.devicedev.socialwave.data.room;

import android.app.Application;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.devicedev.socialwave.R;
import com.devicedev.socialwave.data.room.dao.UserDao;
import com.devicedev.socialwave.data.room.entities.UserEntity;

@Database(entities = {UserEntity.class}, version = 1, exportSchema = false)
public abstract class MyDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "social_wave";

    private static MyDatabase instance;

    public abstract UserDao userDao();


    public static synchronized MyDatabase getInstance(Context context) {

        if (instance == null) {
            instance = Room.databaseBuilder(context, MyDatabase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
