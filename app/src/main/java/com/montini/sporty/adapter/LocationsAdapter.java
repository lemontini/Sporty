package com.montini.sporty.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.montini.sporty.R;
import com.montini.sporty.model.Location;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class LocationsAdapter extends RecyclerView.Adapter<LocationsAdapter.ViewHolder> {

    // constants
    private static final String TAG = "LocationAdapter";

    // vars
    private Context context;
    private List<Location> locations = new ArrayList<>();
    private OnLocationListener onLocationListener;

    // constructor
    public LocationsAdapter(Context context, OnLocationListener onLocationListener) {
        this.context = context;
        this.onLocationListener = onLocationListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.location_item, viewGroup, false);
        return new ViewHolder(view, onLocationListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        Log.d(TAG, "onBindViewHolder: called. Picture: " + locations.get(position).getLogo());
        Location currentLocation = locations.get(position);

        Picasso.with(context).load(currentLocation.getLogo()).resize(480, 480).centerInside().into(viewHolder.aLogo);
        viewHolder.aName.setText(currentLocation.getName());
        viewHolder.aAddress.setText(currentLocation.getAddress());

        // viewHolder.aItem.setOnClickListener(new View.OnClickListener() {
        //     @Override
        //     public void onClick(View v) {
        //         Log.d(TAG, "onClick: clicked on: " + locations.get(i).getName());
        //         Toast.makeText(context, locations.get(i).getName(), Toast.LENGTH_SHORT).show();
        //     }
        // });
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        ImageView aLogo;
        TextView aName, aAddress;
        OnLocationListener onLocationListener;

        public ViewHolder(@NonNull View itemView, OnLocationListener onLocationListener) {
            super(itemView);

            this.aLogo = itemView.findViewById(R.id.vLocationLogo);
            this.aName = itemView.findViewById(R.id.vLocationName);
            this.aAddress = itemView.findViewById(R.id.vLocationAddress);
            this.onLocationListener = onLocationListener;

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onLocationListener.onLocationClick(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View view) {
            onLocationListener.onLocationLongClick(getAdapterPosition());
            return true;
        }
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
        for (Location i : locations) Log.d(TAG, "setLocations TAG: " + i.getId());
        notifyDataSetChanged();
    }

    public Location getLocationAt(int position) {
        return locations.get(position);
    }

    public interface OnLocationListener {
        void onLocationClick(int position);
        void onLocationLongClick(int position);
    }
}