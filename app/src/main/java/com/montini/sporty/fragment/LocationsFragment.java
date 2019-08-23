package com.montini.sporty.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.montini.sporty.AddLocationActivity;
import com.montini.sporty.MainActivity;
import com.montini.sporty.R;
import com.montini.sporty.adapter.LocationsAdapter;
import com.montini.sporty.model.Location;
import com.montini.sporty.viewmodel.LocationsViewModel;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.montini.sporty.MainActivity.getUriForResource;

public class LocationsFragment extends Fragment implements LocationsAdapter.OnLocationListener {

    // constants
    private static final String TAG = "LocationsFragment";
    public final int LOCATION_ADD = 01;
    public final int LOCATION_EDIT = 02;

    // vars
    private LocationsViewModel mViewModel;
    private LocationsAdapter locationsAdapter;
    View v;
    Button btnAdd;

    // instance
    public static LocationsFragment newInstance() {
        return new LocationsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: started.");
        v = inflater.inflate(R.layout.locations_fragment, container, false);
        initLocationsView();
        return v;
    }

    private void initLocationsView() {
        Log.d(TAG, "initLocationsView: init LocationsView.");
        RecyclerView recyclerView = v.findViewById(R.id.recycler_view);
        // locationsAdapter = new LocationsAdapter(v.getContext(), locations, this);
        locationsAdapter = new LocationsAdapter(v.getContext(), this);
        recyclerView.setAdapter(locationsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));

        btnAdd = getActivity().findViewById(R.id.buttonAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddLocationActivity.class);
                startActivityForResult(intent, LOCATION_ADD);
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(LocationsViewModel.class);

        // TODO: Use the ViewModel
        mViewModel.getAllLocations().observe(this, new Observer<List<Location>>() {
            @Override
            public void onChanged(@Nullable List<Location> locations) {
                locationsAdapter.setLocations(locations);
            }
        });
    }

    @Override
    public void onLocationClick(int position) {
        Log.d(TAG, "onLocationClick: clicked at " + position);
        // // locations.get(position);
        Intent intent = new Intent(v.getContext(), AddLocationActivity.class);
        // TODO: only the position should be transferred to the Add activity, as there is no
        //  locations data holder anymore in the View.
        //  Parcelable interface also might not be needed anymore
        intent.putExtra("location", (Parcelable) locationsAdapter.getLocationAt(position));
        intent.putExtra("position", position);
        // TODO: relocate the following to other part of the app:
        //  business logic shouldn't be in the View
        // Log.d(TAG, "onLocationClick: before parcelable: " + locations.get(position).getLogo());
        startActivityForResult(intent, LOCATION_EDIT);
    }

    @Override
    public void onLocationLongClick(int position) {
        Log.d(TAG, "onLocationLongClick: clicked at " + position);
        // Intent intent = new Intent(v.getContext(), AddLocationActivity.class);
        // intent.putExtra("position", position);
        // startActivityForResult(intent, LOCATION_EDIT);
        mViewModel.delete(locationsAdapter.getLocationAt(position));
        Toast.makeText(getContext(), "Location deleted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Location location = data.getParcelableExtra("location");
            int position = data.getIntExtra("position", -1);

            switch (requestCode) {
                case LOCATION_EDIT:
                    mViewModel.update(location); // TODO: relocate it to other part of the app: business logic shouldn't be in the View
                    break;
                case LOCATION_ADD: {
                    mViewModel.insert(location); // TODO: relocate it to other part of the app: business logic shouldn't be in the View
                    break;
                }
            }
            Log.d(TAG, "onActivityResult: path of the image is: " + location.getLogo());
            locationsAdapter.notifyDataSetChanged(); // is this still necessary when we have implemented the observer?
        }
    }

}
