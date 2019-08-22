package com.montini.sporty.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.BindingAdapter;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.montini.sporty.model.Location;
import com.montini.sporty.repository.LocationsRepo;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LocationsViewModel extends AndroidViewModel {
    // TODO: Implement the ViewModel
    public String name;
    public String address;
    public String logo;
    // ------------------

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

    // public void init() {
    //     if (mLocations != null) {
    //         return;
    //     }
    //     mRepo = LocationsRepo.getInstance();
    //     mLocations = mRepo.getLocations();
    // }

    // public LiveData<List<Location>> getLocations() {
    //     return mLocations;
    // }

    public String getLogo() {
        return logo;
    }

    @BindingAdapter({"imgUrl"})
    public static void loadImage(ImageView imageView, String logo) {
        Picasso.with(imageView.getContext()).load(logo).into(imageView);
    }
}
