package com.montini.sporty.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import com.montini.sporty.model.Location;
import com.montini.sporty.repository.LocationsRepo;

import java.util.List;

public class LocationsViewModel extends AndroidViewModel {

    private LiveData<List<Location>> mLocations;
    private LocationsRepo mRepo;

    public LocationsViewModel(@NonNull Application application) {
        super(application);
        mRepo = new LocationsRepo(application);
        mLocations = mRepo.getAllLocations();
    }

    public void insert(Location location) {
        mRepo.insert(location);
    }

    public void update(Location location) {
        mRepo.update(location);
    }

    public void delete(Location location) {
        mRepo.delete(location);
    }

    public void deleteAllLocations() {
        mRepo.deleteAllLocations();
    }

    public LiveData<List<Location>> getAllLocations() {
        return mRepo.getAllLocations();
    }
}
