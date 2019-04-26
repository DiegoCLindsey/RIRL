package com.cppandi.rirl.views;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import com.cppandi.rirl.R;
import com.cppandi.rirl.controllers.UserService;
import com.cppandi.rirl.models.Game;
import com.cppandi.rirl.models.GameLocation;
import com.cppandi.rirl.utils.Constants;
import com.cppandi.rirl.utils.LayoutUtils;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class NewGameFormActivity extends AppCompatActivity {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 41242;
    EditText gPass;
    EditText gName;
    EditText gMPs;
    Button sendNGForm;
    ProgressBar progressBar;
    ArrayList<EditText> formGroup;
    GoogleMap mMap;
    AlertDialog.Builder dialogBuilder;
    List<GameLocation> locations;
    List<Marker> markers;
    List<Circle> circles;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game_form);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        locations = new ArrayList<>();
        markers = new ArrayList<>();
        circles = new ArrayList<>();
        // Get Views

        gPass = findViewById(R.id.newGameFormPassword);
        gName = findViewById(R.id.newGameFormGameName);
        gMPs = findViewById(R.id.newGameFormMaxPlayers);
        sendNGForm = findViewById(R.id.newGameFormSendButton);
        progressBar = findViewById(R.id.newGameProgressBar);

        formGroup = new ArrayList<>();
        formGroup.add(gPass);
        formGroup.add(gName);
        formGroup.add(gMPs);

        setButtonListeners();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        final Context context = this;
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

    private boolean checkGPSPermissions() {
        if (Build.VERSION.SDK_INT >= 23) { // Marshmallow

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
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

    private void initMap() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        final Context context = this;
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                MarkerOptions markerOptions = new MarkerOptions().position(latLng);
                Marker marker = mMap.addMarker(markerOptions);
                Circle circle = mMap.addCircle(LayoutUtils.createDefaultCircleOptions(latLng));
                markers.add(marker);
                circles.add(circle);
                GameLocation location = new GameLocation();
                location.setLatitude(latLng.latitude);
                location.setLongitude(latLng.longitude);
                locations.add(location);
                showMarkerDialog(markers.size() - 1);
            }
        });
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                showMarkerDialog(markers.indexOf(marker));
                return false;
            }
        });
    }

    private void showMarkerDialog(final int position) {
        dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setMessage("Crear Localización");
        View mView = getLayoutInflater().inflate(R.layout.marker_dialog, null);
        dialogBuilder.setView(mView);
        final AlertDialog dialog = dialogBuilder.create();
        final TextInputEditText markerTextView = mView.findViewById(R.id.markerTextView);
        Button saveButton = mView.findViewById(R.id.saveButton);
        Button removeButton = mView.findViewById(R.id.removeButton);

        final Marker marker = markers.get(position);
        final GameLocation location = locations.get(position);
        final Circle circle = circles.get(position);
        markerTextView.setText(marker.getTitle());

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = markerTextView.getText().toString();
                circle.setRadius(location.getRadius());
                marker.setTitle(title);
                location.setTitle(title);
                dialog.dismiss();
            }
        });
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                marker.remove();
                circle.remove();
                circles.remove(position);
                markers.remove(position);
                locations.remove(position);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    private void setButtonListeners() {
        sendNGForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateFormData()) {
                    sendNGForm.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);
                    final Game game = new Game();
                    game.setPassword(gPass.getText().toString());
                    game.setTitle(gName.getText().toString());
                    game.setMaxCharacters(Integer.parseInt(gMPs.getText().toString()));
                    game.setMaster(UserService.getInstance().getDocumentReference());
                    game.setLocations(locations);
                    FirebaseFirestore.getInstance().collection("games").add(game).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if (task.isSuccessful()) {
                                game.setId(task.getResult().getId());
                                Intent intent = new Intent();
                                Bundle extras = new Bundle();
                                extras.putString("game_id", game.getId());
                                intent.putExtras(extras);
                                setResult(RESULT_OK, intent);
                                finish();
                            }
                        }
                    });
                }
            }
        });

        final Switch privateParty = findViewById(R.id.switchPrivate);
        privateParty.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TextInputLayout gPassTIL = findViewById(R.id.newGameFormPasswordTIL);
                if (!isChecked) {
                    gPassTIL.setVisibility(View.GONE);
                    gPass.setVisibility(View.GONE);
                    gPass.setText("");
                    privateParty.setTextColor(getResources().getColor(R.color.common_google_signin_btn_text_dark_disabled));
                    gPassTIL.setError(null);
                } else {
                    privateParty.setTextColor(getResources().getColor(R.color.common_google_signin_btn_text_dark_focused));
                    gPassTIL.setVisibility(View.VISIBLE);
                    gPass.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    // AUXILIAR

    private boolean validateFormData() {
        boolean valid = true;

        // Validate Game Name
        gName = findViewById(R.id.newGameFormGameName);
        TextInputLayout gNameTIL = findViewById(R.id.newGameFormGameNameTIL);
        String gameName = gName.getText().toString();
        int lenName = gameName.length();
        if (lenName == 0) {
            valid = false;
            gNameTIL.setError(Constants.ERR_NO_NAME);
        } else if (lenName < Constants.NAME_SZ_MIN) {
            valid = false;
            gNameTIL.setError(Constants.ERR_NAME_MIN);
        } else if (lenName > Constants.NAME_SZ_MAX) {
            valid = false;
            gNameTIL.setError(Constants.ERR_NAME_MAX);
        } else {
            gNameTIL.setError(null);
        }

        // Validate Game Password
        TextInputLayout gPassTIL = findViewById(R.id.newGameFormPasswordTIL);
        String gamePass = gPass.getText().toString();
        if (gPass.getVisibility() == View.VISIBLE) {
            Boolean pass = checkPassword(gamePass, gPassTIL);
            valid = valid && pass;
        }

        // Validate Number of players
        TextInputLayout gMPsTIL = findViewById(R.id.newGameFormMaxPlayersTIL);
        String gPlys = gMPs.getText().toString();
        try {
            int gamePlayers = Integer.valueOf(gPlys);
            if (gamePlayers > Constants.PLAYER_CNT_MAX) {
                gMPsTIL.setError(Constants.ERR_PLAYER_CNT_MAX);
                valid = false;
            } else if (gamePlayers == 0) {
                gMPsTIL.setError(Constants.ERR_NO_PLAYERS);
                valid = false;
            } else {
                gMPsTIL.setError(null);
            }
        } catch (Exception e) {
            gMPsTIL.setError(Constants.ERR_NO_PLAYERS);
            valid = false;
        }
        return valid;

    }

    private boolean checkPassword(String s, TextInputLayout target) {
        boolean valid = true;
        boolean hasNumber = false;
        boolean hasSymbol = false;

        if (s.length() < Constants.PASS_SZ_MIN) {
            target.setError(Constants.ERR_PASS_SZ_MIN);
            valid = false;
        } else if (s.length() > Constants.PASS_SZ_MAX) {
            target.setError(Constants.ERR_PASS_SZ_MAX);
            valid = false;
        } else {
            char c = ' ';
            for (int i = 0; i < s.length(); i++) {
                c = s.charAt(i);

                if (Character.isDigit(c)) {
                    hasNumber = true;
                }
                if (!Character.isLetterOrDigit(c)) {
                    hasSymbol = true;
                }
            }

            if (!(hasNumber && hasSymbol)) {
                String err = "La contraseña debe contener al menos un";
                String err1 = "";
                String err2 = "";
                if (!hasNumber) {
                    err1 += " número";
                }
                if (!hasSymbol) {
                    if (err1 != "") {
                        err2 += " y un";
                    }
                    err2 += " símbolo.";
                }
                valid = false;
                target.setError(err + err1 + err2);
            } else {
                target.setError(null);
            }
        }
        return valid;
    }

}
