package com.cppandi.rirl.controllers;

import com.google.firebase.auth.FirebaseUser;

public class UserService {
    private static UserService sSoleInstance;

    private FirebaseUser user;
    private String userName;

    private UserService() {
    }  //private constructor.

    public static UserService getInstance() {
        if (sSoleInstance == null) { //if there is no instance available... create new one
            sSoleInstance = new UserService();
        }

        return sSoleInstance;
    }

    public FirebaseUser getUser() {
        return user;
    }

    public void setUser(FirebaseUser user) {
        this.user = user;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
