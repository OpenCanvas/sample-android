package com.example.opencanvas_sample_android;

import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class Route implements Parcelable {

    public long id;
    public String name;
    public String description;
    public String about;
    public double lat;
    public double lon;
    public String cover;
    public int placeCount;
    public int distance;
    public int duration;
    public int viewCount;
    public int likeCount;
    public String polyline;

    @Override
    public String toString() {
        // Override for usage in ArrayAdapter
        return name;
    }

    public static class GetRoutesResponse {
        public List<Route> routes;
    }

    protected Route(Parcel in) {
        id = in.readLong();
        name = in.readString();
        description = in.readString();
        about = in.readString();
        lat = in.readDouble();
        lon = in.readDouble();
        cover = in.readString();
        placeCount = in.readInt();
        distance = in.readInt();
        duration = in.readInt();
        viewCount = in.readInt();
        likeCount = in.readInt();
        polyline = in.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(about);
        dest.writeDouble(lat);
        dest.writeDouble(lon);
        dest.writeString(cover);
        dest.writeInt(placeCount);
        dest.writeInt(distance);
        dest.writeInt(duration);
        dest.writeInt(viewCount);
        dest.writeInt(likeCount);
        dest.writeString(polyline);
    }

    public static final Parcelable.Creator<Route> CREATOR = new Parcelable.Creator<Route>() {
        public Route createFromParcel(Parcel in) {
            return new Route(in);
        }

        public Route[] newArray(int size) {
            return new Route[size];
        }
    };
}
