package com.cppandi.rirl.models;

public class Player extends User {
    private String playerName;

    public Player() {
    }

    public Player(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerName(){
        return this.playerName;
    }

    public void setPlayerName(String newPlayerName){
        this.playerName = newPlayerName;
    }
}
