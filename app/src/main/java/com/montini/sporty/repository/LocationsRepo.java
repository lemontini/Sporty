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

// Singleton pattern:

public class LocationsRepo {

    private LocationDao locationDao;
    private LiveData<List<Location>> locations;

    private static LocationsRepo instance;
    private ArrayList<Location> dataSet = new ArrayList<>();

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

    // public static LocationsRepo getInstance() {
    //     if (instance == null){
    //         instance = new LocationsRepo();
    //     }
    //     return instance;
    // }
    //
    // // Pretend to get data from a webservice or an online resource
    // public MutableLiveData<List<Location>> getLocations() {
    //     setLocations();
    //
    //     MutableLiveData<List<Location>> data = new MutableLiveData<>();
    //     data.setValue(dataSet);
    //
    //     return data;
    // }
    //
    // private void setLocations() {
    //     dataSet.add(new Location("SEB arena", "Ąžuolyno g. 7, Vilnius", 4, getUriForResource(R.drawable.logo_seb_arena)));
    //     dataSet.add(new Location("Delfi Sporto Centras", "Ozo g. 14C, Vilnius", 8, getUriForResource(R.drawable.logo_delfi_sporto_centras)));
    //     dataSet.add(new Location("Zambia", "Africa", 1, Uri.parse("https://d2lo9qrcc42lm4.cloudfront.net/Images/News/_contentLarge/Main-girls-out-of-school.jpg?mtime=20170426205135")));
    //     dataSet.add(new Location("a", "b", 1, Uri.parse("file:///storage/emulated/0/Pictures/Instagram/IMG_20190630_210003_297.jpg")));
    // }

}
