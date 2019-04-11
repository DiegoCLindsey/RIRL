package com.cppandi.rirl;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class MainLoginActivity extends AppCompatActivity {

    private static final int SIGN_IN_REQUEST_CODE = 16556;
    List<AuthUI.IdpConfig> providers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);
        FirebaseApp.initializeApp(this);
        // Init Providers

        providers = Arrays.asList(new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        showSignInOptions();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SIGN_IN_REQUEST_CODE) {

            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == RESULT_OK) {

                // GET USER
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                // SHOW EMAIL

                Toast.makeText(this, user.getEmail(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, response.getError().getMessage(), Toast.LENGTH_LONG).show();
            }
        }

    }

    private void showSignInOptions() {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders((providers))
                        .setTheme(R.style.SignInTheme)
                        .build()
                , SIGN_IN_REQUEST_CODE);
    }

}
