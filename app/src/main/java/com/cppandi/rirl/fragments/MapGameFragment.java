package com.cppandi.rirl.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cppandi.rirl.R;
import com.cppandi.rirl.controllers.GameService;
import com.cppandi.rirl.models.Game;
import com.cppandi.rirl.models.GameLocation;
import com.cppandi.rirl.views.MainGameActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import static com.cppandi.rirl.utils.LayoutUtils.createDefaultCircleOptions;


public class MapGameFragment extends android.support.v4.app.Fragment {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 41243;

    private Context context;
    private SupportMapFragment mapFragment;
    private GameService gameService;
    private Game game;
    private List<GameLocation> locations;
    private List<Marker> markers;
    private GoogleMap mMap;
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
        locations = game.getLocations();
        markers = new ArrayList<>();
        if (mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance();
            ;

            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    mMap = googleMap;
                    if (ActivityCompat.checkSelfPermission(context,
                            Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                            ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                                    != PackageManager.PERMISSION_GRANTED) {
                        if (!checkGPSPermissions()) {
                            Toast.makeText(context, "No Permisos", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        initMap();
                    }

                }
            });
        }

        // R.id.map is a FrameLayout, not a Fragment
        getChildFragmentManager().beginTransaction().replace(R.id.gameMap, mapFragment).commit();
        return view;

    }

    @SuppressLint("MissingPermission")
    private void initMap() {
        mMap.setMyLocationEnabled(true);
        for (GameLocation location : locations) {
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            Marker marker = mMap.addMarker(new MarkerOptions().position(latLng)
                    .title(location.getTitle()));
            markers.add(marker);

            mMap.addCircle(createDefaultCircleOptions(latLng, location.getRadius()));
        }
        GameLocation location = game.getLocations().get(0);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15f));

    }

    private boolean checkGPSPermissions() {
        if (Build.VERSION.SDK_INT >= 23) { // Marshmallow

            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            return true;
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initMap();
            } else { // if permission is not granted

                // decide what you want to do if you don't get permissions
            }
        }
    }
}
