package com.montini.sporty.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.montini.sporty.R;
import com.montini.sporty.model.Player;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PlayersAdapter extends RecyclerView.Adapter<PlayersAdapter.ViewHolder> {

    // constants
    private static final String TAG = "PlayerAdapter";

    // vars
    private Context context;
    private List<Player> players = new ArrayList<>();
    private OnPlayerListener onPlayerListener;

    // constructor
    public PlayersAdapter(Context context, OnPlayerListener onPlayerListener) {
        this.context = context;
        this.onPlayerListener = onPlayerListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.player_item, viewGroup, false);
        return new ViewHolder(view, onPlayerListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        Log.d(TAG, "onBindViewHolder: called. Picture: " + players.get(position).getLogo());
        Player currentPlayer = players.get(position);

        Picasso.get().load(currentPlayer.getLogo()).resize(480, 480).centerInside().into(viewHolder.aLogo);
        viewHolder.aName.setText(currentPlayer.getName());
        // reminder: probably something must be done with inATeam
    }

    @Override
    public int getItemCount() {
        return players.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        ImageView aLogo;
        TextView aName;
        OnPlayerListener onPlayerListener;

        public ViewHolder(@NonNull View itemView, OnPlayerListener onPlayerListener) {
            super(itemView);

            this.aLogo = itemView.findViewById(R.id.vPlayerLogo);
            this.aName = itemView.findViewById(R.id.vPlayerName);
            this.onPlayerListener = onPlayerListener;

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onPlayerListener.onPlayerClick(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View view) {
            onPlayerListener.onPlayerLongClick(getAdapterPosition());
            return true;
        }
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
        for (Player i : players) Log.d(TAG, "setPlayers TAG: " + i.getId());
        notifyDataSetChanged();
    }

    public Player getPlayerAt(int position) {
        return players.get(position);
    }

    public interface OnPlayerListener {
        void onPlayerClick(int position);
        void onPlayerLongClick(int position);
    }
}