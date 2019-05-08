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
import com.cppandi.rirl.fragments.InventoryGameFragment;
import com.cppandi.rirl.fragments.MainGameFragment;
import com.cppandi.rirl.fragments.MapGameFragment;
import com.cppandi.rirl.models.Game;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

public class MainGameActivity extends AppCompatActivity {
    final Fragment fragment1 = new MapGameFragment();
    final Fragment fragment2 = new MainGameFragment();
    final Fragment fragment3 = new InventoryGameFragment();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = fragment1;

    BottomNavigationView navView;

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
                        fm.beginTransaction().hide(active).show(fragment2).commit();
                        active = fragment2;
                        return true;
                    case R.id.navigation_notifications:
                        fm.beginTransaction().hide(active).show(fragment3).commit();
                        active = fragment3;
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
        navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navView.getMenu().findItem(R.id.navigation_dashboard).setChecked(true);
        gameService = GameService.getInstance();
        gameService.setGameID(getIntent().getExtras().getString("game_id"));
        gameService.getGameDocumentReference().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    gameService.setGame(task.getResult().toObject(Game.class));
                    fm.beginTransaction().add(R.id.main_container, fragment1, "1").hide(fragment1).commit();
                    fm.beginTransaction().add(R.id.main_container, fragment3, "3").hide(fragment3).commit();
                    fm.beginTransaction().add(R.id.main_container, fragment2, "2").commit();
                    loading = false;
                }
            }
        });
        navView.setSelectedItemId(R.id.navigation_dashboard);
    }

}
