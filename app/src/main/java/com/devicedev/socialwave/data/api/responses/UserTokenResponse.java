package com.devicedev.socialwave.data.api.responses;

import com.devicedev.socialwave.data.room.entities.UserEntity;
import com.google.gson.annotations.SerializedName;

public class UserTokenResponse {

    @SerializedName("user")
    private UserEntity userEntity;

    private String token;

    public UserTokenResponse(UserEntity userEntity, String token) {
        this.userEntity = userEntity;
        this.token = token;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
