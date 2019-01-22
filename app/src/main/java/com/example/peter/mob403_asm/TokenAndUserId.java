package com.example.peter.mob403_asm;

public class TokenAndUserId {
    private String token, userId, userName;

    public TokenAndUserId(String token, String userId, String userName) {
        this.token = token;
        this.userId = userId;
        this.userName = userName;
    }

    public String getToken() {
        return token;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }
}
