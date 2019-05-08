package com.cppandi.rirl.views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.cppandi.rirl.R;
import com.cppandi.rirl.controllers.GameService;
import com.cppandi.rirl.fragments.MapGameFragment;
import com.cppandi.rirl.models.Game;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

public class MainGameActivity extends AppCompatActivity {
    final Fragment fragment1 = new MapGameFragment();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = fragment1;

    GameService gameService;

    boolean loading = true;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            if (!loading) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        fm.beginTransaction().hide(active).show(fragment1).commit();
                        active = fragment1;
                        return true;
                    case R.id.navigation_dashboard:
                        return true;
                    case R.id.navigation_notifications:
                        return true;
                }
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        gameService = GameService.getInstance();
        gameService.setGameID(getIntent().getExtras().getString("game_id"));
        gameService.getGameDocumentReference().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    gameService.setGame(task.getResult().toObject(Game.class));
                    fm.beginTransaction().add(R.id.main_container, fragment1, "1").commit();
                    loading = false;
                }
            }
        });

    }

}
