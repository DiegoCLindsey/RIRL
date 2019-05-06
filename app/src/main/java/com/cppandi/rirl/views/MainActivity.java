package com.cppandi.rirl.views;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.cppandi.rirl.R;
import com.cppandi.rirl.controllers.GamesAdapter;
import com.cppandi.rirl.controllers.UserService;
import com.cppandi.rirl.models.Game;
import com.cppandi.rirl.models.GameLocationType;
import com.cppandi.rirl.models.User;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {


    private static final int SIGN_IN_REQUEST_CODE = 16526;
    private static final int NEW_GAME_FORM_REQUEST_CODE = 65165;
    private static final int CREATE_USER_REQUEST_CODE = 5161;
    private static final int PROFILE_REQUEST_CODE = 8263;

    AlertDialog.Builder dialogBuilder;
    ArrayList<Game> games;
    GamesAdapter gamesAdapter;
    List<AuthUI.IdpConfig> providers;
    FirebaseUser user = null;
    // Views
    ProgressBar progressBar;
    FirebaseFirestore db;
    FloatingActionButton newGameButton;
    ImageView profileImage;
    RecyclerView rvGames;
    boolean loading = true;
    // Double click prevention
    private long mLastClickTime = 0;
    // Firebase
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();




        // Init views
        progressBar = findViewById(R.id.progressBar);
        profileImage = findViewById(R.id.profileImage);
        newGameButton = findViewById(R.id.newGame);
        newGameButton.setEnabled(false);
        // Init Providers
        providers = Arrays.asList(new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());

        // Actions - Buttons
        setButtonListeners();
        // User Control
        user = mAuth.getCurrentUser();
        if (user == null) {
            showSignInOptions();
        } else {
            afterSignIn();
        }

    }

    private void setRecycleView() {
        rvGames = findViewById(R.id.recycler_games);
        rvGames.setLayoutManager(new LinearLayoutManager(this));
        games = new ArrayList<>();
        gamesAdapter = new GamesAdapter(games);
        rvGames.setAdapter(gamesAdapter);


        db.collection("games").limit(50).get().addOnCompleteListener(
                new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            progressBar.setVisibility(View.GONE);
                            loading = false;
                            newGameButton.setEnabled(true);
                            // newGameButton.setBackgroundTintList(ColorStateList.valueOf(R.color.colorAccentEdit));
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                final Game game = document.toObject(Game.class);
                                game.setId(document.getId());
                                final int pos = gamesAdapter.getItemCount();
                                games.add(game);
                                gamesAdapter.notifyItemInserted(pos);
                                game.getMaster().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            game.setMasterName(task.getResult().toObject(User.class).getUserName());
                                            gamesAdapter.notifyItemChanged(pos);

                                        }
                                    }
                                });

                            }
                        } else {
                            Log.d("prueba", "Error getting documents: ", task.getException());
                        }
                    }
                }

        );
        rvGames.addOnItemTouchListener(
                new RecyclerItemClickListener(this, rvGames,
                        new RecyclerItemClickListener.OnItemClickListener() {

                            @Override
                            public void onItemClick(View v, int position) {
                                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                                    return;
                                }

                                mLastClickTime = SystemClock.elapsedRealtime();
                                int pos = rvGames.getChildLayoutPosition(v);
                                openGameAuth(games.get(pos).getId(), v.getContext(),games.get(pos).getPassword());
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                                // do whatever
                            }

                        }
                ));
    }

    private void setButtonListeners() {
        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                startActivityForResult(new Intent(v.getContext(), NewGameFormActivity.class), NEW_GAME_FORM_REQUEST_CODE);
            }
        });

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                if (!loading)
                    startActivityForResult(new Intent(v.getContext(), ProfileActivity.class), PROFILE_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SIGN_IN_REQUEST_CODE) {

            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == RESULT_OK) {
                afterSignIn();
            } else {
                if (response != null) {
                    Toast.makeText(this, response.getError().getMessage(), Toast.LENGTH_LONG).show();

                }
                showSignInOptions();
            }
        }
        if (requestCode == NEW_GAME_FORM_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                assert data != null;
                String game_id = data.getStringExtra("game_id");
                db.collection("games").document(game_id).get().addOnCompleteListener(
                        new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    progressBar.setVisibility(View.GONE);
                                    final Game game = task.getResult().toObject(Game.class);
                                    game.setId(task.getResult().getId());
                                    games.add(game);
                                    final int position = gamesAdapter.getItemCount();
                                    gamesAdapter.notifyItemInserted(position);
                                    game.getMaster().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if (task.isSuccessful()) {
                                                game.setMasterName(task.getResult().toObject(User.class).getUserName());
                                                gamesAdapter.notifyItemChanged(position);

                                            }

                                        }
                                    });
                                } else {
                                    Log.d("prueba", "Error getting documents: ", task.getException());
                                }
                            }
                        });
                openGame(game_id, this);
            }
        }
        if (requestCode == CREATE_USER_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
            } else {
                finish();
            }
        }

    }

    private void showSignInOptions() {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders((providers))
                        .setTheme(R.style.SignInTheme)
                        .build()
                , SIGN_IN_REQUEST_CODE);
    }


    private void afterSignIn() {
        // GET USER
        user = mAuth.getCurrentUser();
        UserService.getInstance().setFirebaseUser(user);

        // SHOW EMAIL
        Toast.makeText(this, user.getEmail(), Toast.LENGTH_SHORT).show();

        // SEARCH APP USER
        final Context context = this;
        db.collection("users").document(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful() && Objects.requireNonNull(task.getResult()).exists()) {
                    User user = task.getResult().toObject(User.class);
                    UserService.getInstance().setAppUser(user);

                } else {
                    startActivityForResult(new Intent(context, CreateUserActivity.class), CREATE_USER_REQUEST_CODE);
                }
                // Set RecycleView
                setRecycleView();
            }
        });
    }

    private void openGameAuth(String game, Context context,String hasKey) {

        if(hasKey != ""){
            dialogBuilder = new AlertDialog.Builder(this);
            dialogBuilder.setMessage("Introduzca la contraseña");
            View mView = getLayoutInflater().inflate(R.layout.password_dialog, null);
            dialogBuilder.setView(mView);
            final AlertDialog dialog = dialogBuilder.create();
            dialog.show();

            final String joinGame = game;
            final String joinPass = hasKey;
            final Context joinContext = context;

            final TextView password = dialog.findViewById(R.id.passwordTextView);

            Button joinButton = mView.findViewById(R.id.password_enter_button);
            joinButton.setOnClickListener(new  View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String pass = password.getText().toString();
                    if(pass.equals(joinPass)){
                        dialog.dismiss();
                        password.setError(null);
                        openGame(joinGame,joinContext);
                    }else{
                        password.setError("Contraseña incorrecta");
                    }
                }
            });

        }else{
            openGame(game,context);
        }


    }

    private void openGame(String game, Context context){
        Intent intent = new Intent(context, JoinGameActivity.class);
        Bundle extras = new Bundle();
        extras.putString("game_id", game);
        intent.putExtras(extras);
        context.startActivity(intent);
    }
    // Set Menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);

        SearchManager searchManager = (SearchManager)
                getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchMenuItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) searchMenuItem.getActionView();

        searchView.setSearchableInfo(searchManager.
                getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                gamesAdapter.getFilter().filter(s);
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout_button:
                mAuth.signOut();
                showSignInOptions();
                return true;

            // ELIMINAR ESTE BOTÓN
            case R.id.create_user_button:
                startActivityForResult(new Intent(this, CreateUserActivity.class), CREATE_USER_REQUEST_CODE);
                return true;
            case R.id.test_ingame:
                startActivityForResult(new Intent(this, JoinGameActivity.class), CREATE_USER_REQUEST_CODE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

/**
 * Needed Classes
 **/
class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
    private GestureDetector mGestureDetector;
    private OnItemClickListener mListener;

    RecyclerItemClickListener(Context context, final RecyclerView recyclerView, OnItemClickListener listener) {
        mListener = listener;
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (child != null && mListener != null) {
                    mListener.onLongItemClick(child, recyclerView.getChildAdapterPosition(child));
                }
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView view, @NonNull MotionEvent e) {
        View childView = view.findChildViewUnder(e.getX(), e.getY());
        if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
            mListener.onItemClick(childView, view.getChildAdapterPosition(childView));
            return true;
        }
        return false;
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView view, @NonNull MotionEvent motionEvent) {
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);

        public void onLongItemClick(View view, int position);
    }
}

