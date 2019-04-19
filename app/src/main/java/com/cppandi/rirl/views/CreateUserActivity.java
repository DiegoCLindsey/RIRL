package com.cppandi.rirl.views;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.cppandi.rirl.R;
import com.cppandi.rirl.controllers.UserService;
import com.cppandi.rirl.models.User;
import com.cppandi.rirl.utils.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class CreateUserActivity extends AppCompatActivity {
    boolean doubleBackToExitPressedOnce = false;
    Button sendButton;
    TextInputEditText userNameInput;
    UserService userService;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        userService = UserService.getInstance();

        // Actions - Buttons
        sendButton = findViewById(R.id.sendButton);
        setButtonListeners();
        userNameInput = findViewById(R.id.userNameInput);
        progressBar = findViewById(R.id.userProgressBar);
    }

    private void setButtonListeners() {
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendButton.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                final User auxUser = new User(userService.getFirebaseUser().getUid(), userNameInput.getText().toString());
                FirebaseFirestore.getInstance().collection("users").add(
                        auxUser).
                addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            userService.setAppUser(auxUser);
                            setResult(RESULT_OK);
                            finish();
                        } else {
                            setResult(Constants.RESULT_FAILED);
                            finish();
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            setResult(RESULT_CANCELED);
            finish();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

}
