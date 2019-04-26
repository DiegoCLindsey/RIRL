package com.cppandi.rirl.models;

import com.cppandi.rirl.utils.Constants;

public class GameLocation extends Location {
    private GameLocationType type;
    private Double radius;

    public GameLocation() {
        super();
        this.radius = Constants.DEFAULT_RADIUS;
    }

    public GameLocationType getType() {
        return type;
    }

    public void setType(GameLocationType type) {
        this.type = type;
    }

    public Double getRadius() {
        return radius;
    }

    public void setRadius(Double radius) {
        this.radius = radius;
    }
}
