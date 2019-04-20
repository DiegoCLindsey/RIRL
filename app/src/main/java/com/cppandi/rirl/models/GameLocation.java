package com.cppandi.rirl.models;

public class GameLocation extends Location {
    private GameLocationType type;
    private Float radius;

    public GameLocation() {
        super();
    }

    public GameLocation(Float lat, Float lang, String title, GameLocationType type, Float radius) {
        super(lat, lang, title);
        this.type = type;
        this.radius = radius;
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
