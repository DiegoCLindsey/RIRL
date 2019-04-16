package com.cppandi.rirl.controllers;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cppandi.rirl.R;
import com.cppandi.rirl.models.Game;
import com.cppandi.rirl.views.MapsActivity;

import java.util.List;

public class GamesAdapter extends
        RecyclerView.Adapter<GamesAdapter.ViewHolder> {

    // Double click prevention
    private long mLastClickTime = 0;
    // Store a member variable for the contacts
    private List<Game> mGames;

    // Pass in the contact array into the constructor
    public GamesAdapter(List<Game> games) {
        mGames = games;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public GamesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_game, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(GamesAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Game game = mGames.get(position);

        // Set item views based on your views and data model
        TextView textView = viewHolder.personName;
        textView.setText(game.getTitle());
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
        TextView personName;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            itemGame = itemView.findViewById(R.id.item_game_layout);
            personName = itemView.findViewById(R.id.contact_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                        return;
                    }
                    mLastClickTime = SystemClock.elapsedRealtime();
                    Context context = v.getContext();
                    context.startActivity(new Intent(context, MapsActivity.class));
                    v.setEnabled(true);

                }
            });
            itemView.setClickable(true);
        }

    }
}
