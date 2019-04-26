package com.cppandi.rirl.models;

import com.google.android.gms.maps.model.LatLng;

class Location {
    private LatLng latLong;
    private String title;

    public Location() {
    }

    public LatLng getLatLong() {
        return latLong;
    }

    public void setLatLong(LatLng latLong) {
        this.latLong = latLong;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
