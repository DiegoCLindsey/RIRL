package com.cppandi.rirl.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.cppandi.rirl.R;
import com.cppandi.rirl.models.Game;
import com.cppandi.rirl.utils.DBquerys;
import com.cppandi.rirl.models.Player;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class JoinGameActivity extends AppCompatActivity {

    private Button joinGame;
    private Game game;
    private RecyclerView rvPlayers;
    private ProgressBar progressBar;
    private List<Player> players;
    private FirebaseFirestore db;
    private String gameID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_game);

        joinGame = findViewById(R.id.joinGameButton);
        gameID = getIntent().getExtras().getString("game_id");

        DBquerys<Game> query = new DBquerys<>(gameID,"games");

        game = query.getResponse();

        joinGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                joinGame();
            }
        });
        progressBar = findViewById(R.id.progressBar4);


        db = FirebaseFirestore.getInstance();

    }

    private void joinGame(){
        Intent intent = new Intent();
        Bundle extras = new Bundle();
        extras.putString("game_id", gameID);
        intent.putExtras(extras);
        setResult(RESULT_OK, intent);
        finish();
    }


}

