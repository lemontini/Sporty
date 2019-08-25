package com.montini.sporty.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.montini.sporty.MainActivity;
import com.montini.sporty.R;
import com.montini.sporty.viewmodel.EventsViewModel;

public class EventsFragment extends Fragment {

    // constants
    private static final String TAG = "EventsFragment";

    // vars
    private EventsViewModel mViewModel;
    Button btnAdd;

    // instance
    public static EventsFragment newInstance() {
        return new EventsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        btnAdd = getActivity().findViewById(R.id.buttonAdd);
        btnAdd.setVisibility(View.VISIBLE);
        return inflater.inflate(R.layout.events_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(EventsViewModel.class);
        // TODO: Use the ViewModel
    }

}
