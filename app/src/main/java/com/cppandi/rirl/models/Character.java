package com.cppandi.rirl.models;

public class Character {
    private String id;
    private String gameId;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return gameId;
    }

    public void setUser_id(String game_id) {
        this.gameId = game_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Character(String gameId, String name) {
        this.gameId = gameId;
        this.name = name;
    }

    public Character() {
    }
}
