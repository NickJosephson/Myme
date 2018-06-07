package com.nitrogen.myme;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.TextView;
import com.nitrogen.myme.objects.Meme;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //private TextView mTextMessage;
    ArrayList<Meme> memes;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    //mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    //mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        // ...
        // Lookup the recyclerview in activity layout
        RecyclerView rvMemes = (RecyclerView) findViewById(R.id.rvMemes);

        // Initialize memes
        memes = Meme.createMemesList(20);
        // Create adapter passing in the sample user data
        MemesRecyclerAdapter adapter = new MemesRecyclerAdapter(memes);
        // Attach the adapter to the recyclerview to populate items
        rvMemes.setAdapter(adapter);
        // Set layout manager to position the items
        int numberOfColumns = 3;
        rvMemes.setLayoutManager(new GridLayoutManager(this, numberOfColumns));

    }

}
