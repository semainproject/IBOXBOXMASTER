package com.example.kittaporn.iboxbox;

/**
 * Created by narathorn on 11/9/2017 AD.
 */

public class HospitalMarkerGetter {
    String name;
    String info;
    String tel;
    double lat;
    double lng;


    public HospitalMarkerGetter() {

    }

    public HospitalMarkerGetter(String name, String info, String tel, double lat, double lng) {
        this.name = name;
        this.info = info;
        this.tel = tel;
        this.lat = lat;
        this.lng = lng;
    }

    public String getName() {
        return name;
    }

    public String getInfo() {
        return info;
    }

    public String getTel() {
        return tel;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }
}
