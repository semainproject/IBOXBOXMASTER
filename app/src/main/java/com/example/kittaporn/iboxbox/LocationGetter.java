package com.example.kittaporn.iboxbox;

/**
 * Created by Kittaporn on 11/9/2017.
 */

public class LocationGetter {
    Double lat,lng;

    public LocationGetter() {
    }

    public LocationGetter(Double lat, Double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }
}
