package com.nitrogen.myme.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.nitrogen.myme.R;

public class FavouritesActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Menu bottomNavMenu = navigation.getMenu();
        MenuItem item = bottomNavMenu.getItem(1);
        item.setChecked(true);

    }





    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_explore:
                    //mTextMessage.setText(R.string.title_home);
                    startActivity(new Intent(FavouritesActivity.this, MemesActivity.class));
                    return true;
                case R.id.navigation_favourites:
                    //mTextMessage.setText(R.string.title_dashboard);

                    return true;
//                case R.id.navigation_studio:
//                    //mTextMessage.setText(R.string.title_notifications);
//                    return true;
            }
            return false;
        }
    };





}
