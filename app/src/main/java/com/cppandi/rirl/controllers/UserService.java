package com.cppandi.rirl.controllers;

import com.cppandi.rirl.models.User;
import com.google.firebase.auth.FirebaseUser;

public class UserService {
    private static UserService sSoleInstance;

    private FirebaseUser firebaseUser;
    private User appUser;

    private UserService() {
    }  //private constructor.

    public static UserService getInstance() {
        if (sSoleInstance == null) { //if there is no instance available... create new one
            sSoleInstance = new UserService();
        }

        return sSoleInstance;
    }

    public FirebaseUser getFirebaseUser() {
        return firebaseUser;
    }

    public void setFirebaseUser(FirebaseUser firebaseUser) {
        this.firebaseUser = firebaseUser;
    }

    public User getAppUser() {
        return appUser;
    }

    public void setAppUser(User appUser) {
        this.appUser = appUser;
    }
}
