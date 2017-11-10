package com.example.kittaporn.iboxbox;

/**
 * Created by Kittaporn on 11/10/2017.
 */

public class DogInformationGetter {
    String name;
    String deviceID;

    public DogInformationGetter() {
    }

    public DogInformationGetter(String name, String deviceID) {
        this.name = name;
        this.deviceID = deviceID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }
}
