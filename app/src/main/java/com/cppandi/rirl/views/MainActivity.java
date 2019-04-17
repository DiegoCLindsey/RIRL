package com.cppandi.rirl.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.cppandi.rirl.R;
import com.cppandi.rirl.controllers.GamesAdapter;
import com.cppandi.rirl.models.Game;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private static final int SIGN_IN_REQUEST_CODE = 16526;
    private static final int NEW_GAME_FORM_REQUEST_CODE = 65165;
    List<AuthUI.IdpConfig> providers;
    FirebaseUser user = null;
    ArrayList<Game> games;
    GamesAdapter adapter = new GamesAdapter(games);
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();

        // Init Providers
        providers = Arrays.asList(new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());

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

    private void setRecycleView() {
        final RecyclerView rvGames = findViewById(R.id.recycler_games);

        rvGames.setLayoutManager(new LinearLayoutManager(this));
        games = new ArrayList<>();
        final GamesAdapter gamesAdapter = new GamesAdapter(games);
        rvGames.setAdapter(gamesAdapter);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("games").limit(50).get().addOnCompleteListener(
                new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Game game = document.toObject(Game.class);
                                    games.add(game);
                                    gamesAdapter.notifyItemInserted(gamesAdapter.getItemCount());

                            }

                            } else {
                            Log.d("prueba", "Error getting documents: ", task.getException());
                        }
                    }
                }

        );
    }

    private void setButtonListeners() {
        FloatingActionButton newGameButton = findViewById(R.id.newGame);
        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(v.getContext(), NewGameForm.class), NEW_GAME_FORM_REQUEST_CODE);
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
        // Set RecycleView
        setRecycleView();
    }
}

