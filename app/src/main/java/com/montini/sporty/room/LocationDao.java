package com.montini.sporty.room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

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
