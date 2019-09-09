package com.montini.sporty.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.montini.sporty.model.Location;
import com.montini.sporty.model.Player;
import com.montini.sporty.room.LocationDao;
import com.montini.sporty.room.LocationDatabase;
import com.montini.sporty.room.PlayerDao;

import java.util.List;

public class DataRepo {

    // vars
    private LocationDao locationDao;
    private LiveData<List<Location>> locations;

    private PlayerDao playerDao;
    private LiveData<List<Player>> players;

    // constructors

    public DataRepo(Application application) {
        LocationDatabase database = LocationDatabase.getInstance(application);

        locationDao = database.locationDao();
        locations = locationDao.getAllLocations();

        playerDao = database.playerDao();
        players = playerDao.getAllPlayers();
    }

    // methods

    // for Locations:

    public void insertLocation(Location location) {
        new InsertLocationAsyncTask(locationDao).execute(location);
    }

    public void updateLocation(Location location) {
        new UpdateLocationAsyncTask(locationDao).execute(location);
    }

    public void deleteLocation(Location location) {
        new DeleteLocationAsyncTask(locationDao).execute(location);
    }

    public void deleteAllLocations() {
        new DeleteAllLocationAsyncTask(locationDao).execute();
    }

    public LiveData<List<Location>> getAllLocations() {
        return locations;
    }

    // for Players:

    public void insertPlayer(Player player) {
        new InsertPlayerAsyncTask(playerDao).execute(player);
    }

    public void updatePlayer(Player player) {
        new UpdatePlayerAsyncTask(playerDao).execute(player);
    }

    public void deletePlayer(Player player) {
        new DeletePlayerAsyncTask(playerDao).execute(player);
    }

    public void deleteAllPlayers() {
        new DeleteAllPlayerAsyncTask(playerDao).execute();
    }

    public LiveData<List<Player>> getAllPlayers() {
        return players;
    }

    // Dao operations via AsyncTask classes

    // 1. for Locations:

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

    // 1. for Players:

    private static class InsertPlayerAsyncTask extends AsyncTask<Player, Void, Void> {
        private PlayerDao playerDao;

        private InsertPlayerAsyncTask(PlayerDao playerDao) {
            this.playerDao = playerDao;
        }

        @Override
        protected Void doInBackground(Player... players) {
            playerDao.insert(players[0]);
            return null;
        }
    }

    private static class UpdatePlayerAsyncTask extends AsyncTask<Player, Void, Void> {
        private PlayerDao playerDao;

        private UpdatePlayerAsyncTask(PlayerDao playerDao) {
            this.playerDao = playerDao;
        }

        @Override
        protected Void doInBackground(Player... players) {
            playerDao.update(players[0]);
            return null;
        }
    }

    private static class DeletePlayerAsyncTask extends AsyncTask<Player, Void, Void> {
        private PlayerDao playerDao;

        private DeletePlayerAsyncTask(PlayerDao playerDao) {
            this.playerDao = playerDao;
        }

        @Override
        protected Void doInBackground(Player... players) {
            playerDao.delete(players[0]);
            return null;
        }
    }

    private static class DeleteAllPlayerAsyncTask extends AsyncTask<Void, Void, Void> {
        private PlayerDao playerDao;

        private DeleteAllPlayerAsyncTask(PlayerDao playerDao) {
            this.playerDao = playerDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            playerDao.deleteAllPlayers();
            return null;
        }
    }

}
