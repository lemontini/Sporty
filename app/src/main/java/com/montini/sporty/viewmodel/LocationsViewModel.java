package com.montini.sporty.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.montini.sporty.model.Location;
import com.montini.sporty.repository.LocationsRepo;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LocationsViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    public String name;
    public String address;
    public String logo;
    // ------------------

    private MutableLiveData<List<Location>> mLocations;
    private LocationsRepo mRepo;

    public void init() {
        if (mLocations != null) {
            return;
        }
        mRepo = LocationsRepo.getInstance();
        mLocations = mRepo.getLocations();
    }

    public LiveData<List<Location>> getLocations() {
        return mLocations;
    }

    public String getLogo() {
        return logo;
    }

    @BindingAdapter({"imgUrl"})
    public static void loadImage(ImageView imageView, String logo) {
        Picasso.with(imageView.getContext()).load(logo).into(imageView);
    }
}
