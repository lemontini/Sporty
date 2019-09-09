package com.montini.sporty.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.montini.sporty.model.Player;
import com.montini.sporty.repository.DataRepo;

import java.util.List;

public class PlayersViewModel extends AndroidViewModel {

    private LiveData<List<Player>> mPlayers;
    private DataRepo mRepo;

    public PlayersViewModel(@NonNull Application application) {
        super(application);
        mRepo = new DataRepo(application);
        mPlayers = mRepo.getAllPlayers();
    }

    public void insert(Player player) { mRepo.insertPlayer(player); }

    public void update(Player player) { mRepo.updatePlayer(player); }

    public void delete(Player player) { mRepo.deletePlayer(player); }

    public void deleteAll() { mRepo.deleteAllPlayers(); }

    public LiveData<List<Player>> getAll() { return mRepo.getAllPlayers(); }
    
}
