package com.montini.sporty.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.montini.sporty.AddEventActivity;
import com.montini.sporty.AddLocationActivity;
import com.montini.sporty.MainActivity;
import com.montini.sporty.R;
import com.montini.sporty.viewmodel.EventsViewModel;

public class EventsFragment extends Fragment {

    // constants
    private static final String TAG = "EventsFragment";
    public final int EVENT_ADD = 03;

    // vars
    private EventsViewModel mViewModel;

    // instance
    public static EventsFragment newInstance() {
        return new EventsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.events_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(EventsViewModel.class);
        // TODO: Use the ViewModel
    }

    public void addItem() {
        Toast.makeText(getContext(), "Add Event not yet implemented", Toast.LENGTH_SHORT).show();
        // TODO: implement the addition of the Event
    }

}
