package com.montini.sporty.fragment;

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
    private List<Location> locations;
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
        if (locations == null) locations = new ArrayList<>();
        initData();
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(LocationsViewModel.class);
        // TODO: Use the ViewModel
    }

    private void initData() {
        Log.d(TAG, "initData: preparing data.");

        locations.add(new Location("SEB arena", "Ąžuolyno g. 7, Vilnius", 4, getUriForResource(R.drawable.logo_seb_arena)));
        locations.add(new Location("Delfi Sporto Centras", "Ozo g. 14C, Vilnius", 8, getUriForResource(R.drawable.logo_delfi_sporto_centras)));
        locations.add(new Location("Zambia", "Africa", 1, Uri.parse("https://d2lo9qrcc42lm4.cloudfront.net/Images/News/_contentLarge/Main-girls-out-of-school.jpg?mtime=20170426205135")));
        locations.add(new Location("a", "b", 1, Uri.parse("file:///storage/emulated/0/Pictures/Instagram/IMG_20190630_210003_297.jpg")));

        initLocationsView();

        btnAdd = getActivity().findViewById(R.id.buttonAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddLocationActivity.class);
                startActivityForResult(intent, LOCATION_ADD);
            }
        });

    }

    private void initLocationsView() {
        Log.d(TAG, "initLocationsView: init LocationsView.");
        RecyclerView recyclerView = v.findViewById(R.id.recycler_view);
        locationsAdapter = new LocationsAdapter(v.getContext(), locations, this);
        recyclerView.setAdapter(locationsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
    }

    @Override
    public void onLocationClick(int position) {
        Log.d(TAG, "onLocationClick: clicked: " + position);
        // // locations.get(position);
        Intent intent = new Intent(v.getContext(), AddLocationActivity.class);
        intent.putExtra("location", (Parcelable) locations.get(position));
        intent.putExtra("position", position);
        Log.d(TAG, "onLocationClick: before parcelable: " + locations.get(position).getLogo());
        startActivityForResult(intent, LOCATION_EDIT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Location location = data.getParcelableExtra("location");
            int position = data.getIntExtra("position", -1);

            switch (requestCode) {
                case LOCATION_EDIT:
                    locations.set(position, location);
                    break;
                case LOCATION_ADD: {
                    locations.add(location);
                    break;
                }
            }
            Log.d(TAG, "onActivityResult: path of the image is: " + location.getLogo());
            locationsAdapter.notifyDataSetChanged();
        }
    }

}
