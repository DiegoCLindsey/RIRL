package com.cppandi.rirl.models;

import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Date;
import java.util.List;

public class Game {
    private String id;
    private String master_id;
    private String title;
    private GameState state;
    private Date creation_date;
    private Date finish_date;
    private List<Character> characters;
    private MarkerOptions creation_location;
    // TODO private List<Location> locations;
    // TODO private List<Event> events;

}
