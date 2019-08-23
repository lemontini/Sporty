package com.montini.sporty.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.net.Uri;
import android.os.AsyncTask;

import com.montini.sporty.R;
import com.montini.sporty.model.Location;
import com.montini.sporty.room.LocationDao;
import com.montini.sporty.room.LocationDatabase;

import java.util.ArrayList;
import java.util.List;

import static com.montini.sporty.MainActivity.getUriForResource;

public class LocationsRepo {

    // vars
    private LocationDao locationDao;
    private LiveData<List<Location>> locations;

    // constructors

    public LocationsRepo(Application application) {
        LocationDatabase database = LocationDatabase.getInstance(application);
        locationDao = database.locationDao();
        locations = locationDao.getAllLocations();
    }

    // methods

    public void insert(Location location) {
        new InsertLocationAsyncTask(locationDao).execute(location);
    }

    public void update(Location location) {
        new UpdateLocationAsyncTask(locationDao).execute(location);
    }

    public void delete(Location location) {
        new DeleteLocationAsyncTask(locationDao).execute(location);
    }

    public void deleteAllLocations() {
        new DeleteAllLocationAsyncTask(locationDao).execute();
    }

    public LiveData<List<Location>> getAllLocations() {
        return locations;
    }

    // Dao operations via AsyncTask classes

    private static class InsertLocationAsyncTask extends AsyncTask<Location, Void, Void> {
        private LocationDao locationDao;

        private InsertLocationAsyncTask(LocationDao locationDao) {
            this.locationDao = locationDao;
        }

        @Override
        protected Void doInBackground(Location... locations) {
            locationDao.insert(locations[0]);
            return null;
        }
    }

    private static class UpdateLocationAsyncTask extends AsyncTask<Location, Void, Void> {
        private LocationDao locationDao;

        private UpdateLocationAsyncTask(LocationDao locationDao) {
            this.locationDao = locationDao;
        }

        @Override
        protected Void doInBackground(Location... locations) {
            locationDao.update(locations[0]);
            return null;
        }
    }

    private static class DeleteLocationAsyncTask extends AsyncTask<Location, Void, Void> {
        private LocationDao locationDao;

        private DeleteLocationAsyncTask(LocationDao locationDao) {
            this.locationDao = locationDao;
        }

        @Override
        protected Void doInBackground(Location... locations) {
            locationDao.delete(locations[0]);
            return null;
        }
    }

    private static class DeleteAllLocationAsyncTask extends AsyncTask<Void, Void, Void> {
        private LocationDao locationDao;

        private DeleteAllLocationAsyncTask(LocationDao locationDao) {
            this.locationDao = locationDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            locationDao.deleteAllLocations();
            return null;
        }
    }

}
