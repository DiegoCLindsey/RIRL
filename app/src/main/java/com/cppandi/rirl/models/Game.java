package com.cppandi.rirl.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Game {
    private String id;
    private String masterId;
    private String title;
    private GameState state;
    private Date creationDate;
    private Date finishDate;
    private List<String> characterIds;
    private Location creationLocation;
    private List<GameLocation> locations;
    private Integer maxCharacters;
    private String masterName;
    private String password;

    public Game() {
        this.characterIds = new ArrayList<>();
        this.locations = new ArrayList<>();
        this.state = GameState.PREPARING;
        this.creationDate = new Date();
    }

    public Game(String title) {
        this.title = title;
    }


    // Constructors

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Auto Getter and Setters

    public String getMasterName() {
        return masterName;
    }

    public void setMasterName(String masterName) {
        this.masterName = masterName;
    }

    public List<String> getCharacterIds() {
        return characterIds;
    }

    public void setCharacterIds(List<String> characterIds) {
        this.characterIds = characterIds;
    }

    public Integer getMaxCharacters() {
        return maxCharacters;
    }

    public void setMaxCharacters(Integer maxCharacters) {
        this.maxCharacters = maxCharacters;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMasterId() {
        return masterId;
    }

    public void setMasterId(String masterId) {
        this.masterId = masterId;
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

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public Location getCreationLocation() {
        return creationLocation;
    }

    public void setCreationLocation(Location creationLocation) {
        this.creationLocation = creationLocation;
    }

    public List<GameLocation> getLocations() {
        return locations;
    }

    public void setLocations(List<GameLocation> locations) {
        this.locations = locations;
    }

    // Remaining getters

    public Integer getLengthCharacters() {
        return getCharacterIds() != null ? getCharacterIds().size() : 0;
    }

    public String getLocationName() {
        return getCreationLocation() != null ? getCreationLocation().getTitle() : "";
    }
// TODO private List<Event> events;

}
