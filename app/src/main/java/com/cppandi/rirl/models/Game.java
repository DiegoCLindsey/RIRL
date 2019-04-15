package com.cppandi.rirl.models;

import java.util.Date;
import java.util.List;

public class Game {
    private String id;
    private String master_id;
    private String title;
    private GameState state;
    private Date creation_date;
    private Date finish_date;
    private List<String> character_ids;
    private Location creation_location;
    private List<GameLocation> locations;

    public Game(String master_id, String title, GameState state, Date creation_date, Date finish_date, List<String> character_ids, Location creation_location, List<GameLocation> locations) {
        this.master_id = master_id;
        this.title = title;
        this.state = state;
        this.creation_date = creation_date;
        this.finish_date = finish_date;
        this.character_ids = character_ids;
        this.creation_location = creation_location;
        this.locations = locations;
    }

    public Game() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMaster_id() {
        return master_id;
    }

    public void setMaster_id(String master_id) {
        this.master_id = master_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public Date getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(Date creation_date) {
        this.creation_date = creation_date;
    }

    public Date getFinish_date() {
        return finish_date;
    }

    public void setFinish_date(Date finish_date) {
        this.finish_date = finish_date;
    }

    public List<String> getCharacters() {
        return character_ids;
    }

    public void setCharacters(List<String> character_ids) {
        this.character_ids = character_ids;
    }

    public Location getCreation_location() {
        return creation_location;
    }

    public void setCreation_location(Location creation_location) {
        this.creation_location = creation_location;
    }

    public List<GameLocation> getLocations() {
        return locations;
    }

    public void setLocations(List<GameLocation> locations) {
        this.locations = locations;
    }
// TODO private List<Event> events;

}
