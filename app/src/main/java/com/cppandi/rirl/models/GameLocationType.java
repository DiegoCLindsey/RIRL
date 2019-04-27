package com.cppandi.rirl.models;

import com.cppandi.rirl.R;

public enum GameLocationType {
    DUNGEON(R.string.dungeon),
    TAVERN(R.string.tavern);

    private final int name;

    GameLocationType(int name) {
        this.name = name;
    }

    public int getName() {
        return name;
    }
}
