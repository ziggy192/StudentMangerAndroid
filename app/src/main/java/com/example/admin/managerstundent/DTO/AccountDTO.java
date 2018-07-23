package com.example.admin.managerstundent.DTO;

import com.google.gson.annotations.SerializedName;

public class AccountDTO {
    @SerializedName("UserId")
    private String userId;
    @SerializedName("Password")
    private String password;

    public AccountDTO(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
