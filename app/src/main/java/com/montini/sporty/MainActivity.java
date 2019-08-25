package com.montini.sporty;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.montini.sporty.adapter.FragmentAdapter;
import com.montini.sporty.fragment.EventsFragment;
import com.montini.sporty.fragment.LocationsFragment;
import com.montini.sporty.fragment.PlayersFragment;

public class MainActivity extends AppCompatActivity {

    // constants
    private static final String TAG = "MainActivity";

    //vars
    BottomNavigationView navMain;
    ViewPager viewPager;
    Toolbar toolbar;
    // Button btnAdd;

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_events:
                    viewPager.setCurrentItem(0);
                    // mTextMessage.setText(R.string.title_events);
                    return true;
                case R.id.navigation_locations:
                    viewPager.setCurrentItem(1);
                    // mTextMessage.setText(R.string.title_locations);
                    return true;
                case R.id.navigation_players:
                    viewPager.setCurrentItem(2);
                    // mTextMessage.setText(R.string.title_players);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getTitle());

        //Here you get actionbar features if you
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        navMain = findViewById(R.id.nav_view); // init Navigation Bar
        // mTextMessage = findViewById(R.id.message);
        navMain.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener); // set Listener for Navigation Items

        viewPager = findViewById(R.id.container); // init Viewpager
        SetupFragmentManager(getSupportFragmentManager(), viewPager); // setup Fragment

        viewPager.setCurrentItem(0); // set current item when activity start
        viewPager.addOnPageChangeListener(new PageChange()); // listeners for Viewpager when page changed

        // btnAdd = findViewById(R.id.buttonAdd);
        // btnAdd.setVisibility(View.VISIBLE);

    }

    public static void SetupFragmentManager(FragmentManager fragmentManager, ViewPager viewPager) {
        FragmentAdapter Adapter = new FragmentAdapter(fragmentManager);

        //Add All Fragment To List
        Adapter.add(new EventsFragment(), "Events");
        Adapter.add(new LocationsFragment(), "Locations");
        Adapter.add(new PlayersFragment(), "Players");
        viewPager.setAdapter(Adapter);
    }

    public class PageChange implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            switch (position) {
                case 0:
                    navMain.setSelectedItemId(R.id.navigation_events);
                    break;
                case 1:
                    navMain.setSelectedItemId(R.id.navigation_locations);
                    break;
                case 2:
                    navMain.setSelectedItemId(R.id.navigation_players);
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    }

    public static Uri getUriForResource(int resourceId) {
        return Uri.parse("android.resource://" + R.class.getPackage().getName() + "/" + resourceId);
    }

    // public void toggleButtonVisibility(boolean show) {
    //     if (show) {
    //         btnAdd.setVisibility(View.VISIBLE);
    //     }
    //     else btnAdd.setVisibility(View.GONE);
    // }

}
