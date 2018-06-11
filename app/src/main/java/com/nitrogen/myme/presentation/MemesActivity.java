package com.nitrogen.myme.presentation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

import com.nitrogen.myme.R;
import com.nitrogen.myme.business.AccessMemes;
import com.nitrogen.myme.objects.Meme;
import com.nitrogen.myme.objects.Tag;

import java.util.List;

public class MemesActivity extends AppCompatActivity {
    AccessMemes accessMemes;
    List<Meme> memes;

    MemesRecyclerAdapter adapter;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_bookmarks:
                    //mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_explore:
                    //mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_studio:
                    //mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_bar, menu);

        MenuItem searchIcon = menu.findItem(R.id.search);
        SearchView searchView = (SearchView)searchIcon.getActionView();

        // handle input from the user
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String userInput) {
                // filter through meme db
                handleSearch(userInput);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String userInput) {
                // do nothing (this method definition is required by the constructor)
                return false;
            }
        });

        // if the user cancels their search, go back to initial meme list
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                displayMemes(accessMemes.getMemes());
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memes);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        //mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        // Lookup the recycler view in activity layout
        RecyclerView rvMemes = (RecyclerView) findViewById(R.id.rvMemes);

        // Initialize memes
        accessMemes = new AccessMemes();
        memes = accessMemes.getMemes();

        // Create adapter passing in the sample user data
        adapter = new MemesRecyclerAdapter(memes);

        // Attach the adapter to the recycler view to populate items
        rvMemes.setAdapter(adapter);

        // Set layout manager to position the items
        int numberOfColumns = 3;
        rvMemes.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
    }

    /* handleSearch
     *
     * purpose: take the user's input (the name of a tag) and perform a query to retrieve a
     *          list of memes with that tag.
     *
    */
    private void handleSearch(String input) {
        memes =  accessMemes.getMemesByTag(new Tag(input));
        displayMemes(memes);
    }

    /* displayMemes
     *
     * purpose: update the memes displayed on the screen.
    */
    private void displayMemes(List<Meme> memes) {
        adapter.updateMemeList(memes);
    }
}
