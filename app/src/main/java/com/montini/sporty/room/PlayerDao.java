package com.montini.sporty.room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.montini.sporty.model.Player;

import java.util.List;

@Dao
public interface PlayerDao {

    @Insert
    void insert(Player player);

    @Update
    void update(Player player);

    @Delete
    void delete(Player player);

    @Query("DELETE FROM players_table")
    void deleteAllPlayers();

    @Query("SELECT * FROM players_table ORDER BY name ASC")
    LiveData<List<Player>> getAllPlayers();
}
