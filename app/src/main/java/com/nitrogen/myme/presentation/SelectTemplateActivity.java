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
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.nitrogen.myme.R;
import com.nitrogen.myme.business.AccessMemeTemplates;
import com.nitrogen.myme.business.SearchMemes;
import com.nitrogen.myme.objects.TemplateMeme;

public class SelectTemplateActivity extends AppCompatActivity implements OnItemClick {
    private AccessMemeTemplates accessMemeTemplates;
    private List<TemplateMeme> templates;
    private TemplatesRecyclerAdapter adapter;
    private RecyclerView rvTemplates;
    private SearchMemes searchMemes = new SearchMemes();
    private boolean layoutAsGrid = true;

    //**************************************************
    // Activity Lifecycle
    //**************************************************

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_templates);

        // Setup toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        // Set up bottom navigation
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Menu bottomNavMenu = navigation.getMenu();
        MenuItem item = bottomNavMenu.getItem(0);
        item.setChecked(true);

        // Initialize memes
        accessMemeTemplates = new AccessMemeTemplates();
        templates = accessMemeTemplates.getTemplates();

        // Setup recycler view
        setupRV();
    }

    @Override
    protected void onResume() {
        super.onResume();

        //data may have changed (e.g. some meme is now a favorite)
        rvTemplates.getAdapter().notifyDataSetChanged();
    }

    //**************************************************
    // Activity Events
    //**************************************************

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_bar, menu);

        // Remove searching for now
        MenuItem searchIcon = menu.findItem(R.id.search);
        searchIcon.setVisible(false);

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
                    return true;
                case R.id.navigation_studio:
                    startActivity(new Intent(SelectTemplateActivity.this, CreateActivity.class));
                    finish(); //end this activity
                    return true;
                case R.id.navigation_favourites:
                    startActivity(new Intent(SelectTemplateActivity.this, FavouritesActivity.class));
                    finish(); //end this activity
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
        rvTemplates = (RecyclerView) findViewById(R.id.rvMemes);

        // Create adapter passing in the sample user data
        adapter = new TemplatesRecyclerAdapter(templates, this);

        // Attach the adapter to the recycler view to populate items
        rvTemplates.setAdapter(adapter);

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
            rvTemplates.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        } else {
            rvTemplates.setLayoutManager(new LinearLayoutManager(this));
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

    /* displayTemplates
     *
     * purpose: update the templates displayed on the screen.
     */
    private void displayTemplates(List<TemplateMeme> template) {
        adapter.updateTemplateList(templates);
    }

    /* getSource
     *
     * purpose: get the id of the template the user selected and return it to its parent activity.
     */
    @Override
    public void getSource(String source) {
        Toast toast = Toast.makeText(this, "Received id = "+source, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

        Intent data = new Intent();
        data.putExtra("template", source);

        // Activity finished ok, return the data
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public void getID(int id) {
        Toast toast = Toast.makeText(this, "Received id = "+id, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

        TemplateMeme template = accessMemeTemplates.getTemplateByID(id);

        Intent data = new Intent();
        data.putExtra("templatePath", template.getImagePath());

        Bundle mBundle = new Bundle();
        mBundle.putSerializable("templateCoordinates", template.getCoordinates());

        data.putExtras(mBundle);

        // Activity finished ok, return the data
        setResult(RESULT_OK, data);
        finish();

    }


}
