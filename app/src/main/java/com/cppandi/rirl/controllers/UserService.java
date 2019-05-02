package com.cppandi.rirl.controllers;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.cppandi.rirl.R;
import com.cppandi.rirl.models.User;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

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

    public DocumentReference getDocumentReference() {
        return FirebaseFirestore.getInstance().collection("users").document(firebaseUser.getUid());
    }

    public static class activity_join_game extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_join_game);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.joinGameButton);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
        }
    }
}
