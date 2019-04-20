package com.cppandi.rirl.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;

import com.cppandi.rirl.R;
import com.cppandi.rirl.controllers.UserService;
import com.cppandi.rirl.models.Game;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class NewGameFormActivity extends AppCompatActivity {

    EditText gPass;
    EditText gName;
    EditText gMPs;
    Button sendNGForm;
    ProgressBar progressBar;
    ArrayList<EditText> formGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game_form);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
            gNameTIL.setError("¡Debes escribir un nombre para la partida!");
        } else if (lenName < 5) {
            valid = false;
            gNameTIL.setError("El nombre es demasiado corto");
        } else if (lenName > 20) {
            valid = false;
            gNameTIL.setError("El nombre es demasiado largo");
        } else {
            gNameTIL.setError(null);
        }

        // Validate Game Password
        TextInputLayout gPassTIL = findViewById(R.id.newGameFormPasswordTIL);
        String gamePass = gPass.getText().toString();
        if (gPass.getVisibility() == View.VISIBLE) {
            valid = checkPassword(gamePass, gPassTIL);
        }

        // Validate Number of players
        TextInputLayout gMPsTIL = findViewById(R.id.newGameFormMaxPlayersTIL);
        String gPlys = gMPs.getText().toString();
        Log.d("Número de jugadores: ", gPlys);
        try {
            int gamePlayers = Integer.valueOf(gPlys);
            if (gamePlayers > 20) {
                gMPsTIL.setError("¡Esos son muchos jugadores!");
                valid = false;
            } else if (gamePlayers == 0) {
                gMPsTIL.setError("El juego debe permitir al menos un jugador");
                valid = false;
            } else {
                gMPsTIL.setError(null);
            }
        } catch (Exception e) {
            gMPsTIL.setError("El juego debe permitir al menos un jugador");
            valid = false;
        }
        return valid;

    }

    private boolean checkPassword(String s, TextInputLayout target) {
        boolean valid = true;
        boolean hasNumber = false;
        boolean hasSymbol = false;

        if (s.length() < 8) {
            target.setError("La contraseña debe tener al menos 8 caracteres");
            valid = false;
        } else if (s.length() > 20) {
            target.setError("La contraseña no debe tener más de 20 caracteres");
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
