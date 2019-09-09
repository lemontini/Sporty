package com.montini.sporty.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.montini.sporty.model.Location;

import java.util.List;

@Dao
public interface LocationDao {

    @Insert
    void insert(Location location);

    @Update
    void update(Location location);

    @Delete
    void delete(Location location);

    @Query("DELETE FROM locations_table")
    void deleteAllLocations();

    @Query("SELECT * FROM locations_table ORDER BY name ASC")
    LiveData<List<Location>> getAllLocations();
}
