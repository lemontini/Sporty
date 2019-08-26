package com.montini.sporty.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "players_table")
public class Player {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private Boolean inATeam = false;

    // Constructors

    @Ignore // for Room persistence
    public Player() {}

    public Player(String name) {
        this.name = name;
        this.inATeam = false;
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

    // Methods

    public String getPlayerData() {
        return "[" + id + ". " + name + "]";
    }

    public void showPlayerData() {
        System.out.println(getPlayerData());
    }


}
