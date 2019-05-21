package com.cppandi.rirl.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.cppandi.rirl.R;
import com.cppandi.rirl.models.Game;
import com.cppandi.rirl.utils.DBQuery;

public class JoinGameActivity extends AppCompatActivity {

    private Button joinGame;
    private String game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_game);

        joinGame = findViewById(R.id.joinGameButton);
        game = getIntent().getExtras().getString("game_id");
        joinGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                joinGame();
            }
        });

        DBQuery<Game> gameQuery= new DBQuery<>(game,"games");
        Game gameObject = gameQuery.getResponse();
        joinGame.setText(gameObject.getTitle());
    }

    private void joinGame(){
        Intent intent = new Intent();
        Bundle extras = new Bundle();
        extras.putString("game_id", game);
        intent.putExtras(extras);
        setResult(RESULT_OK, intent);
        finish();
    }




}

