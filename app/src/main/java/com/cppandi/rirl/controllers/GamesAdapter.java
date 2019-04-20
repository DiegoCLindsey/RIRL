package com.cppandi.rirl.controllers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cppandi.rirl.R;
import com.cppandi.rirl.models.Game;

import java.util.List;

public class GamesAdapter extends
        RecyclerView.Adapter<GamesAdapter.ViewHolder> {

    // Store a member variable for the contacts
    private List<Game> mGames;

    // Pass in the contact array into the constructor
    public GamesAdapter(List<Game> games) {
        mGames = games;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @NonNull
    @Override
    public GamesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_game, parent, false);

        // Return a new holder instance
        return new ViewHolder(contactView);
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull GamesAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Game game = mGames.get(position);

        // Set item views based on your views and data model
        viewHolder.titleGame.setText(game.getTitle());
        viewHolder.adminName.setText(game.getMasterName());
        viewHolder.locationName.setText(game.getLocationName());
        viewHolder.playersCount.setText(game.getLengthCharacters() + "/" + game.getMaxCharacters());
        if(game.getPassword() != ""){
            viewHolder.key.setVisibility(View.VISIBLE);
        }

    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mGames.size();
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        LinearLayout itemGame;
        TextView titleGame;
        TextView adminName;
        TextView locationName;
        TextView playersCount;
        ImageView key;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(final View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            itemGame = itemView.findViewById(R.id.item_game_layout);
            titleGame = itemView.findViewById(R.id.title);
            adminName = itemView.findViewById(R.id.admin_name);
            locationName = itemView.findViewById(R.id.locationName);
            playersCount = itemView.findViewById(R.id.playersCount);
            key = itemView.findViewById(R.id.private_party_icon);

            itemView.setClickable(true);
        }

    }
}
