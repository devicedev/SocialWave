package com.devicedev.socialwave.data.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.devicedev.socialwave.data.room.entities.UserEntity;

@Dao
public interface UserDao {

    @Query("SELECT * FROM users ORDER BY id DESC LIMIT 1")
    LiveData<UserEntity> get();

    @Insert
    void insert(UserEntity userEntity);

    @Update
    void update(UserEntity userEntity);

    @Delete
    void delete(UserEntity userEntity);



}
