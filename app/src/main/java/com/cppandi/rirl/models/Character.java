package com.cppandi.rirl.models;

public class Character {
    private String id;
    private String user_id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Character(String user_id, String name) {
        this.user_id = user_id;
        this.name = name;
    }

    public Character() {
    }
}
