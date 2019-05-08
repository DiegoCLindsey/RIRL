package com.cppandi.rirl.controllers;

import com.cppandi.rirl.models.Game;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class GameService {
    private static GameService sSoleInstance;

    private String gameID;
    private Game game;

    private GameService() {
    }

    public static GameService getInstance() {
        if (sSoleInstance == null) { //if there is no instance available... create new one
            sSoleInstance = new GameService();
        }

        return sSoleInstance;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public DocumentReference getGameDocumentReference() {
        return FirebaseFirestore.getInstance().collection("games").document(gameID);
    }

}
