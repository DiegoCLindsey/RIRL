package com.cppandi.rirl.views;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.cppandi.rirl.R;
import com.cppandi.rirl.models.Character;
import com.cppandi.rirl.models.Game;
import com.cppandi.rirl.models.Player;
import com.cppandi.rirl.utils.DBQuery;
import com.google.android.gms.common.api.Response;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class JoinGameActivity extends AppCompatActivity {

    private Button joinGame;
    private String game;
    private RecyclerView recyclerPlayers;
    private ArrayList<Player> players;
    private FirebaseFirestore db;
    private ProgressBar progressBar;
    private Game gameObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_game);
        progressBar = findViewById(R.id.joinGameProgressBar);
        joinGame = findViewById(R.id.joinGameButton);
        game = getIntent().getExtras().getString("game_id");
        joinGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                joinGame();
            }
        });

        new DBQuery(game,"games"){
            @Override
            public void onResponse(){
                Game gameObject = response.toObject(Game.class);
                progressBar.setVisibility(View.GONE);
                List<DocumentReference> playerReferences = gameObject.getCharacters();
                for(DocumentReference player : playerReferences){
                    addPlayer(player);
                }
                joinGame.setText(gameObject.getTitle()+" !");

            }
        };


    }

    private void addPlayer(DocumentReference ref){

        new DBQuery(ref.getId(),"characters"){
            @Override
            public void onResponse(){
                Character chr = response.toObject(Character.class);
                String charName = chr.getName();

            }
        };
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
