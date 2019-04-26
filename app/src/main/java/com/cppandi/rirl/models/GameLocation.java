package com.cppandi.rirl.models;

public class GameLocation extends Location {
    private GameLocationType type;
    private Float radius;

    public GameLocation() {
        super();
    }

    public GameLocationType getType() {
        return type;
    }

    public void setType(GameLocationType type) {
        this.type = type;
    }

    public Float getRadius() {
        return radius;
    }

    public void setRadius(Float radius) {
        this.radius = radius;
    }
}
