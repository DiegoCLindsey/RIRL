package com.cppandi.rirl.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.cppandi.rirl.R;

<<<<<<< Updated upstream:app/src/main/java/com/cppandi/rirl/views/NewGameFormActivity.java
public class NewGameFormActivity extends AppCompatActivity {
=======
import java.util.ArrayList;
import java.util.List;

public class NewGameForm extends AppCompatActivity {
>>>>>>> Stashed changes:app/src/main/java/com/cppandi/rirl/views/NewGameForm.java

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game_form);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}
