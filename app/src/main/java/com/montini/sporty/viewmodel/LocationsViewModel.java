package com.montini.sporty.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import com.montini.sporty.model.Location;
import com.montini.sporty.repository.DataRepo;

import java.util.List;

public class LocationsViewModel extends AndroidViewModel {

    private LiveData<List<Location>> mLocations;
    private DataRepo mRepo;

    public LocationsViewModel(@NonNull Application application) {
        super(application);
        mRepo = new DataRepo(application);
        mLocations = mRepo.getAllLocations();
    }

    public void insert(Location location) {
        mRepo.insertLocation(location);
    }

    public void update(Location location) {
        mRepo.updateLocation(location);
    }

    public void delete(Location location) {
        mRepo.deleteLocation(location);
    }

    public void deleteAll() {
        mRepo.deleteAllLocations();
    }

    public LiveData<List<Location>> getAll() {
        return mRepo.getAllLocations();
    }
}
