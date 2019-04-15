package com.cppandi.rirl.models;

class Location {
    private Float lat;
    private Float lang;
    private String title;

    public Location() {
    }

    public Location(Float lat, Float lang, String title) {
        this.lat = lat;
        this.lang = lang;
        this.title = title;
    }

    public Float getLat() {
        return lat;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    public Float getLang() {
        return lang;
    }

    public void setLang(Float lang) {
        this.lang = lang;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
