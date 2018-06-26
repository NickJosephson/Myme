package com.nitrogen.myme.presentation;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.nitrogen.myme.R;
import com.nitrogen.myme.business.AccessFavourites;
import com.nitrogen.myme.objects.Meme;

public class FavouritesActivity extends AppCompatActivity {
    private AccessFavourites accessMemes;
    private List<Meme> memes;
    private MemesRecyclerAdapter adapter;
    private RecyclerView rvMemes;
    private boolean layoutAsGrid = true;

    //**************************************************
    // Activity Lifecycle
    //**************************************************

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        // Setup toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        // Set up bottom navigation
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Menu bottomNavMenu = navigation.getMenu();
        MenuItem item = bottomNavMenu.getItem(2);
        item.setChecked(true);

        // Initialize memes
        accessMemes = new AccessFavourites();
        memes = accessMemes.getMemes();

        // Setup recycler view
        setupRV();
    }

    @Override
    protected void onResume() {
        super.onResume();

        //data may have changed (e.g. some meme is now a favorite)
        rvMemes.getAdapter().notifyDataSetChanged();
    }

    //**************************************************
    // Activity Events
    //**************************************************

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_bar, menu);

        menu.removeItem(R.id.search);

        setRVLayoutIcon(layoutAsGrid, menu.findItem(R.id.grid_toggle));
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.grid_toggle:
                // User chose the grid toggle item
                layoutAsGrid = !layoutAsGrid;
                setRVLayout(layoutAsGrid);
                setRVLayoutIcon(layoutAsGrid, item);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    //**************************************************
    // Navigation Events
    //**************************************************

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_explore:
                    startActivity(new Intent(FavouritesActivity.this, ExploreActivity.class));
                    finish(); //end this activity
                    return true;
                case R.id.navigation_studio:
                    startActivity(new Intent(FavouritesActivity.this, CreateActivity.class));
                    finish(); //end this activity
                    return true;
                case R.id.navigation_favourites:
                    return true;
            }
            return false;
        }
    };

    //**************************************************
    // Helper Methods
    //**************************************************

    /* setupRV
     *
     * purpose: Setup the recycler view to display the list of memes.
     */
    private void setupRV() {
        // Lookup the recycler view in activity layout
        rvMemes = (RecyclerView) findViewById(R.id.rvFavourites);

        // Create adapter passing in the sample user data
        adapter = new MemesRecyclerAdapter(memes);

        // Attach the adapter to the recycler view to populate items
        rvMemes.setAdapter(adapter);

        // Set layout manager to position the items
        setRVLayout(layoutAsGrid);
    }

    /* setRVLayout
     *
     * purpose: Set the recycler view to layout as gird or list.
     */
    private void setRVLayout(boolean asGrid) {
        int numberOfColumns = 3;

        if (layoutAsGrid) {
            rvMemes.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        } else {
            rvMemes.setLayoutManager(new LinearLayoutManager(this));
        }
    }

    /* setRVLayoutIcon
     *
     * purpose: Set the given grid view toggle icon.
     */
    private void setRVLayoutIcon(boolean asGrid, MenuItem item) {
        if (layoutAsGrid) {
            item.setIcon(R.drawable.list_icon);
        } else {
            item.setIcon(R.drawable.grid_icon);
        }
    }

}
