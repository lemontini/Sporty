package com.montini.sporty.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.montini.sporty.room.UriTypeConverter;

@Entity(tableName = "players_table")
public class Player implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private Boolean inATeam = false;
    private Uri logo;

    // Constructors

    @Ignore // for Room persistence
    public Player() {}

    public Player(String name, Uri logo) {
        this.name = name;
        this.inATeam = false;
        this.logo = logo;
        // id = MainActivity.numPlayers;
        // MainActivity.numPlayers++;
        // MainActivity.numFreeAgents++;
        System.out.print("Player " + getPlayerData() + " has appeared.");
        // if (MainActivity.numPlayers % 4 == 0) System.out.println(" You can form a court of 4!");
        // else if (MainActivity.numPlayers % 2 == 0) System.out.println(" You can form a court of 2!");
        // else System.out.println();
    }

    // Getters and Setters

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Boolean isInATeam() { return inATeam; }
    public void setInATeam(Boolean inATeam) { this.inATeam = inATeam; }

    public Uri getLogo() { return logo; }
    public void setLogo(Uri logo) { this.logo = logo; }

    // Methods

    public String getPlayerData() {
        return "[" + id + ". " + name + "]";
    }

    public void showPlayerData() {
        System.out.println(getPlayerData());
    }

    // Implementation of Parcelable interface

    protected Player(Parcel in) {
        id = in.readInt();
        name = in.readString();
        byte tmpInATeam = in.readByte();
        inATeam = tmpInATeam == 0 ? null : tmpInATeam == 1;
        logo = in.readParcelable(Uri.class.getClassLoader());
    }

    public static final Creator<Player> CREATOR = new Creator<Player>() {
        @Override
        public Player createFromParcel(Parcel in) { return new Player(in); }

        @Override
        public Player[] newArray(int size) { return new Player[size]; }
    };

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeByte((byte) (inATeam == null ? 0 : inATeam ? 1 : 2));
        parcel.writeParcelable(logo, i);
    }
}
