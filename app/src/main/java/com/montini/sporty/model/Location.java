package com.montini.sporty.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(tableName = "locations_table")
public class Location implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private String address;
    private int maxCourts;
    private Uri logo;

    // Constructors

    @Ignore // for Room persistence
    public Location() {}

    public Location(String name, String address, int maxCourts, Uri logo) {
        this.name = name;
        this.address = address;
        this.maxCourts = maxCourts;
        this.logo = logo;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public int getMaxCourts() { return maxCourts; }
    public void setMaxCourts(int maxCourts) { this.maxCourts = maxCourts; }

    public Uri getLogo() { return logo; }
    public void setLogo(Uri logo) { this.logo = logo; }

    // Implementation of Parcelable interface

    protected Location(Parcel in) {
        id = in.readInt();
        name = in.readString();
        address = in.readString();
        maxCourts = in.readInt();
        logo = in.readParcelable(Uri.class.getClassLoader());
    }

    public static final Creator<Location> CREATOR = new Creator<Location>() {
        @Override
        public Location createFromParcel(Parcel in) {
            return new Location(in);
        }

        @Override
        public Location[] newArray(int size) {
            return new Location[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(address);
        parcel.writeInt(maxCourts);
        parcel.writeParcelable(logo, i);
    }

}
