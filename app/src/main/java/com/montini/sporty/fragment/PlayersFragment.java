package com.montini.sporty.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.montini.sporty.R;
import com.montini.sporty.viewmodel.PlayersViewModel;

public class PlayersFragment extends Fragment {

    // constants
    private static final String TAG = "PlayersFragment";

    // vars
    private PlayersViewModel mViewModel;

    public static PlayersFragment newInstance() {
        return new PlayersFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.players_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(PlayersViewModel.class);
        // TODO: Use the ViewModel
    }

    public void addItem() {
        Toast.makeText(getContext(), "Add Player not yet implemented", Toast.LENGTH_SHORT).show();
        // TODO: implement the addition of the Event
    }
}
