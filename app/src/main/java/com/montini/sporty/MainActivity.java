package com.montini.sporty;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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
    private ImageButton btnAdd;
    int currentSelectedTab = 0;

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_events:
                    currentSelectedTab = 0;
                    // mTextMessage.setText(R.string.title_events);
                    break;
                case R.id.navigation_locations:
                    currentSelectedTab = 1;
                    // mTextMessage.setText(R.string.title_locations);
                    break;
                case R.id.navigation_players:
                    currentSelectedTab = 2;
                    // mTextMessage.setText(R.string.title_players);
                    break;
                default:
                    return false;
            }
            viewPager.setCurrentItem(currentSelectedTab);
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // requestPermission();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getTitle());

        //Here you get actionbar features if you
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();


        navMain = findViewById(R.id.nav_view); // init Navigation Bar
        navMain.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener); // set Listener for Navigation Items

        viewPager = findViewById(R.id.container); // init Viewpager
        SetupFragmentManager(getSupportFragmentManager(), viewPager); // setup Fragment

        viewPager.setCurrentItem(0); // set current item when activity start
        viewPager.addOnPageChangeListener(new PageChange()); // listeners for Viewpager when page changed

        btnAdd = findViewById(R.id.buttonAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent intent = new Intent(v.getContext().getApplicationContext(), AddLocationActivity.class);
                // startActivityForResult(intent, LOCATION_ADD);
                Fragment currentPage = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.container + ":" + viewPager.getCurrentItem());
                if (v.getId() == R.id.buttonAdd) {
                    switch (currentSelectedTab) {
                        case 0: {
                            Log.d(TAG, "onClick: Add Event");
                            ((EventsFragment) currentPage).addItem();
                            break;
                        }
                        case 1: {
                            Log.d(TAG, "onClick: Add Location");
                            ((LocationsFragment) currentPage).addItem();
                            break;
                        }
                        case 2: {
                            Log.d(TAG, "onClick: Add Player");
                            ((PlayersFragment) currentPage).addItem();
                            break;
                        }
                    }
                }
            }
        });
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

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(this, "Write External Storage permission allows us to save files. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1100);
        }
    }

}
