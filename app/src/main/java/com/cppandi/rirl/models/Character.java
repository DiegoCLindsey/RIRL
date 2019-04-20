package com.cppandi.rirl.models;

import com.google.firebase.firestore.DocumentReference;

public class Character {
    private String id;
    private DocumentReference user;
    private String name;

    public Character() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DocumentReference getUser() {
        return user;
    }

    public void setUser(DocumentReference user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
