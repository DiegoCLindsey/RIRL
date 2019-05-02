package com.cppandi.rirl.controllers;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cppandi.rirl.R;
import com.cppandi.rirl.models.Game;
import com.cppandi.rirl.models.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayersAdapter extends
        RecyclerView.Adapter<PlayersAdapter.ViewHolder>
implements Filterable {

    // Store a member variable for the contacts
    private List<Player> mPlayers;
    private List<Player> mPlayersFiltered;

    // Pass in the contact array into the constructor
    public PlayersAdapter(List<Player> players) {
        mPlayers = players;
        mPlayersFiltered = players;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @NonNull
    @Override
    public PlayersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_ingame_player, parent, false);

        // Return a new holder instance
        return new ViewHolder(contactView);
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull PlayersAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Player player = mPlayersFiltered.get(position);

        // Set item views based on your views and data model
        viewHolder.userName.setText(player.getUserName());
        viewHolder.playerName.setText(player.getPlayerName());

    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mPlayersFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mPlayersFiltered = mPlayers;
                } else {
                    mPlayersFiltered = filterList(charString);
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mPlayersFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mPlayersFiltered = (ArrayList<Player>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }



    private List<Player> filterList(String charString){
        List<Player> filteredList = new ArrayList<>();
        for (Player row : mPlayers) {
                filteredList.add(row);
        }
        return filteredList;
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder{
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        LinearLayout itemPlayer;
        TextView userName;
        TextView playerName;
        ImageView userImage;
        //ImageView playerImage;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(final View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            itemPlayer = itemView.findViewById(R.id.item_game_layout);
            userName = itemView.findViewById(R.id.userName);
            playerName = itemView.findViewById(R.id.ingameUserImage);
            userImage = itemView.findViewById(R.id.ingameUserImage);
            //playerImage = itemView.findViewById(R.id.userImage);

            itemView.setClickable(true);
        }

    }
}
