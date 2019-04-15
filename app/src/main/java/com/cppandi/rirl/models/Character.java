package com.cppandi.rirl.models;

public class Character {
    private String id;
    private String game_id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return game_id;
    }

    public void setUser_id(String game_id) {
        this.game_id = game_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Character(String game_id, String name) {
        this.game_id = game_id;
        this.name = name;
    }

    public Character() {
    }
}
