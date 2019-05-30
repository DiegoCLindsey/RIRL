package com.cppandi.rirl.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.cppandi.rirl.R;
import com.cppandi.rirl.models.Game;
import com.cppandi.rirl.utils.DBQuery;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import android.util.Log;
import android.widget.Toast;


import java.util.List;

public class JoinGameActivity extends AppCompatActivity {

    private Button joinGame;
    private String game;
    private List<DocumentReference> players;
    private ProgressBar progressBar;
    private Game gameObj;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Load Game ID and initialize
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_game);
        game = getIntent().getExtras().getString("game_id");
        context = this;
        // Load Game data
        new DBQuery(game,"games"){
            @Override
            public void onResponse(){

                Game gameObject = response.toObject(Game.class);
                progressBar = findViewById(R.id.joinGameProgressBar);
                gameObj = gameObject;
                players = gameObj.getCharacters();

                // GAME LOADED, now:

                // Check if current user is already in
                if(false){ //checkUserInGame()
                    join();
                }else{
                    // The game accepts more users, set button for join
                    if(!checkGameIsFull()){
                        progressBar.setVisibility(View.GONE);
                        joinGame = findViewById(R.id.joinGameButton);
                        joinGame.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                addPlayer();
                                join();
                            }
                        });
                    // The game is full, sorry ¯\_(ツ)_/¯
                    }else{
                        Toast.makeText(context, "La partida está llena", Toast.LENGTH_LONG).show();
                        setResult(RESULT_OK, new Intent());
                        finish();
                    }
                }

            }
        };

    }

    private Boolean checkGameIsFull(){
        Toast.makeText(context, gameObj.getMaxCharacters().toString(), Toast.LENGTH_LONG).show();
        return gameObj.getMaxCharacters()<gameObj.getLengthCharacters();
    }

    private Boolean checkUserInGame(){
        for(DocumentReference player : players){
            Log.d("USUARIO","UID: "+FirebaseAuth.getInstance().getCurrentUser().getUid());
            Log.d("DBUSER","PLAYER: "+player.getId());
            if(FirebaseAuth.getInstance().getCurrentUser().getUid().equals(player.getId())) {
                return true;
            }
        }
        return false;
    }

    private void addPlayer(){
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        new DBQuery(uid,"users"){
            @Override
            public void onResponse(){
                if(response != null){
                    players.add(response.getReference());
                    gameObj.setCharacters(players);
                    try {
                        FirebaseFirestore.getInstance().collection("games").document(game).set(gameObj);
                    }catch (Exception e){
                    }
                }
            }
        };
    }


    private void join(){
            Intent intent = new Intent();
            Bundle extras = new Bundle();
            extras.putString("game_id", game);
            intent.putExtras(extras);
            setResult(RESULT_OK, intent);
            finish();
    }

}
