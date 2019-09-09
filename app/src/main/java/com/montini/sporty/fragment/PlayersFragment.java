package com.montini.sporty.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.montini.sporty.AddPlayerActivity;
import com.montini.sporty.R;
import com.montini.sporty.adapter.PlayersAdapter;
import com.montini.sporty.model.Player;
import com.montini.sporty.viewmodel.PlayersViewModel;

import java.util.List;

import static android.app.Activity.RESULT_OK;

public class PlayersFragment extends Fragment implements PlayersAdapter.OnPlayerListener {

    // constants
    private static final String TAG = "PlayersFragment";
    public final int PLAYER_ADD = 03;
    public final int PLAYER_EDIT = 04;

    // vars
    private PlayersViewModel mViewModel;
    private PlayersAdapter playersAdapter;
    View v;

    // instance
    public static PlayersFragment newInstance() {
        return new PlayersFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.players_fragment, container, false);
        initPlayersView();
        return v;
    }

    private void initPlayersView() {
        Log.d(TAG, "initPlayersView: init");
        RecyclerView recyclerView = v.findViewById(R.id.player_view);
        playersAdapter = new PlayersAdapter(v.getContext(), this);
        recyclerView.setAdapter(playersAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(PlayersViewModel.class);

        // Use the ViewModel
        mViewModel.getAll().observe(this, new Observer<List<Player>>() {
            @Override
            public void onChanged(@Nullable List<Player> players) {
                playersAdapter.setPlayers(players);
            }
        });
    }

    @Override
    public void onPlayerClick(int position) {
        Log.d(TAG, "onPlayerClick: clicked at " + position);
        Intent intent = new Intent(v.getContext(), AddPlayerActivity.class);
        // TODO: only the position should be transferred to the Add activity, as there is no
        //  players data holder anymore in the View.
        //  Parcelable interface also might not be needed anymore
        intent.putExtra("player", (Parcelable) playersAdapter.getPlayerAt(position));
        intent.putExtra("position", position);
        // TODO: relocate the following to other part of the app:
        //  business logic shouldn't be in the View
        // Log.d(TAG, "onPlayerClick: before parcelable: " + players.get(position).getLogo());
        startActivityForResult(intent, PLAYER_EDIT);
    }

    @Override
    public void onPlayerLongClick(int position) {
        Log.d(TAG, "onPlayerLongClick: clicked at " + position);
        // Intent intent = new Intent(v.getContext(), AddPlayerActivity.class);
        // intent.putExtra("position", position);
        // startActivityForResult(intent, PLAYER_EDIT);
        mViewModel.delete(playersAdapter.getPlayerAt(position));
        Toast.makeText(getContext(), "Player deleted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Player player = data.getParcelableExtra("player");
            int position = data.getIntExtra("position", -1);

            switch (requestCode) {
                case PLAYER_EDIT:
                    mViewModel.update(player); // TODO: relocate it to other part of the app: business logic shouldn't be in the View
                    break;
                case PLAYER_ADD: {
                    mViewModel.insert(player); // TODO: relocate it to other part of the app: business logic shouldn't be in the View
                    break;
                }
            }
            Log.d(TAG, "onActivityResult: path of the image is: " + player.getLogo());
            playersAdapter.notifyDataSetChanged(); // is this still necessary when we have implemented the observer?
        }
    }

    public void addItem() {
        Intent intent = new Intent(v.getContext(), AddPlayerActivity.class);
        startActivityForResult(intent, PLAYER_ADD);
    }
}
