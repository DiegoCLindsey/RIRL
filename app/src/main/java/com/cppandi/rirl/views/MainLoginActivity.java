package com.cppandi.rirl.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cppandi.rirl.R;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Arrays;
import java.util.List;

public class MainLoginActivity extends AppCompatActivity {

    private static final int SIGN_IN_REQUEST_CODE = 16556;
    List<AuthUI.IdpConfig> providers;
    FirebaseUser user = null;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();

        // Init Providers
        providers = Arrays.asList(new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());

        // Init Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Actions - Buttons
        setButtonListeners();

        // User Control
        user = mAuth.getCurrentUser();
        if (user == null) {
            showSignInOptions();
        } else {
            afterSignIn();
        }

    }

    private void setButtonListeners() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Button logoutButton = findViewById(R.id.button2);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                showSignInOptions();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SIGN_IN_REQUEST_CODE) {

            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == RESULT_OK) {
                // GET USER
                user = mAuth.getCurrentUser();
                // SHOW EMAIL
                Toast.makeText(this, user.getEmail(), Toast.LENGTH_SHORT).show();
                afterSignIn();
            } else {
                if (response != null) {
                    Toast.makeText(this, response.getError().getMessage(), Toast.LENGTH_LONG).show();

                }
                showSignInOptions();
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

    private void afterSignIn() {
        TextView textView = findViewById(R.id.textView);
        textView.setText(user.getEmail());

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("characters")
                .document("asd")
                .collection("items")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("Prueba documentos", document.getId() + " => " + document.getData().entrySet());

                            }
                        }
                    }
                });

    }
}
