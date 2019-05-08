package com.cppandi.rirl.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cppandi.rirl.R;
import com.cppandi.rirl.controllers.GameService;
import com.cppandi.rirl.models.Game;
import com.cppandi.rirl.models.GameLocation;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapGameFragment extends android.support.v4.app.Fragment {

    private Context context;
    private SupportMapFragment mapFragment;
    private GameService gameService;
    private Game game;

    public MapGameFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map_game, container, false);
        context = getActivity();
        gameService = GameService.getInstance();
        game = gameService.getGame();
            if (mapFragment == null) {
                mapFragment = SupportMapFragment.newInstance();
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {

                    for (GameLocation location : game.getLocations()) {
                        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                        googleMap.addMarker(new MarkerOptions().position(latLng)
                                .title(location.getTitle()));
                    }
                    GameLocation location = game.getLocations().get(0);
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15f));

                }
            });
        }

        // R.id.map is a FrameLayout, not a Fragment
        getChildFragmentManager().beginTransaction().replace(R.id.gameMap, mapFragment).commit();
        return view;

    }
}
