package com.cppandi.rirl.models;

import java.util.ArrayList;
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
    private Integer max_characters;
    private String master_name;


    // Constructors


    public Game(String id, String master_id, String title, GameState state, Date creation_date, Date finish_date, List<String> character_ids, Location creation_location, List<GameLocation> locations, Integer max_characters, String master_name) {
        this.id = id;
        this.master_id = master_id;
        this.title = title;
        this.state = state;
        this.creation_date = creation_date;
        this.finish_date = finish_date;
        this.character_ids = character_ids;
        this.creation_location = creation_location;
        this.locations = locations;
        this.max_characters = max_characters;
        this.master_name = master_name;
    }

    public Game() {
        this.character_ids = new ArrayList<>();
        this.locations = new ArrayList<>();
        this.state = GameState.PREPARING;
        this.creation_date = new Date();
    }

    public Game(String title) {
        this.title = title;
    }

    // Auto Getter and Setters

    public String getMaster_name() {
        return master_name;
    }

    public void setMaster_name(String master_name) {
        this.master_name = master_name;
    }

    public List<String> getCharacter_ids() {
        return character_ids;
    }

    public void setCharacter_ids(List<String> character_ids) {
        this.character_ids = character_ids;
    }

    public Integer getMax_characters() {
        return max_characters;
    }

    public void setMax_characters(Integer max_characters) {
        this.max_characters = max_characters;
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
        return title != null ? title : "";
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

    // Remaining getters

    public Integer getLengthCharacters() {
        return getCharacter_ids() != null ? getCharacter_ids().size() : 0;
    }

    public String getLocationName() {
        return getCreation_location() != null ? getCreation_location().getTitle() : "";
    }
// TODO private List<Event> events;

}
