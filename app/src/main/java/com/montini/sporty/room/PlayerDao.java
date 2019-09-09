package com.montini.sporty.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

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
