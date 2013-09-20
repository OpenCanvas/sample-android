package com.example.opencanvas_sample_android;

import java.util.List;

public class Place {
    public long id;
    public String name;
    public double lat;
    public double lon;
    public String geoFence;
    public String cover;
    public int storyCount;
    public boolean isMapPoint;
    public boolean isLocked;
    public int pin;

    @Override
    public String toString() {
        // Override for usage in ArrayAdapter
        return name;
    }

    public static class GetPlacesResponse {
        public List<Place> places;
    }
}
