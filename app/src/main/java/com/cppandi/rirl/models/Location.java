package com.cppandi.rirl.models;

class Location {
    private Float lat;
    private Float lon;
    private String title;

    public Location() {
    }

    public Location(Float lat, Float lon, String title) {
        this.lat = lat;
        this.lon = lon;
        this.title = title;
    }

    public Float getLat() {
        return lat;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    public Float getLang() {
        return lon;
    }

    public void setLang(Float lon) {
        this.lon = lon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
