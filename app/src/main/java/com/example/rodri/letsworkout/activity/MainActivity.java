package com.example.rodri.letsworkout.activity;

import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.rodri.letsworkout.R;
import com.example.rodri.letsworkout.adapter.DrawerItemAdapter;
import com.example.rodri.letsworkout.database.MyDataSource;
import com.example.rodri.letsworkout.fragment.HomeFragment;
import com.example.rodri.letsworkout.fragment.NewBodyFragment;
import com.example.rodri.letsworkout.model.Authentication;
import com.example.rodri.letsworkout.model.DrawerItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ListView drawerListView;
    private ActionBarDrawerToggle drawerToggle; // need to look for it later

    private CharSequence drawerTitle;
    private CharSequence title;

    private String[] menuTitles;
    private TypedArray menuIcons;

    private List<DrawerItem> drawerItems;
    private DrawerItemAdapter adapter;

    private MyDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Views and other variables
        initialize();
        createDrawerItems();

        adapter = new DrawerItemAdapter(this, 0, drawerItems);
        drawerListView.setAdapter(adapter);

        // Enable Action Bar and make it behave as a Toggle Button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // "Gambiarra" used to change the Navigation Drawer Icon
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_drawer, getTheme());
        getSupportActionBar().setHomeAsUpIndicator(drawable);

        Toolbar toolbar = new Toolbar(this);

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name) {

            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(title);
                // call onPreparedOptionsMenu() to show action bar icons
                // need to look for it later
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(drawerTitle);
                // call onPreparedOptionsMenu() to hide action bar icons
                // need to look for it later
                invalidateOptionsMenu();
            }

        };

        drawerLayout.setDrawerListener(drawerToggle);

        if (savedInstanceState == null) {
            displayView(0);
        }

        drawerListView.setOnItemClickListener(new SlideMenuClickListener());
    }

    public void initialize() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerListView = (ListView) findViewById(R.id.listNavigationDrawer);

        drawerTitle = title = getTitle();

        menuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);
        menuTitles = getResources().getStringArray(R.array.nav_drawer_items);

        drawerItems = new ArrayList<>();
    }

    @SuppressWarnings("ResourceType")
    public void createDrawerItems() {
        drawerItems.add(new DrawerItem(menuTitles[0], menuIcons.getResourceId(0, -1)));
        drawerItems.add(new DrawerItem(menuTitles[1], menuIcons.getResourceId(1, -1)));
        drawerItems.add(new DrawerItem(menuTitles[2], menuIcons.getResourceId(2, -1)));
        drawerItems.add(new DrawerItem(menuTitles[3], menuIcons.getResourceId(3, -1)));
        drawerItems.add(new DrawerItem(menuTitles[4], menuIcons.getResourceId(4, -1)));

        menuIcons.recycle();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return true;
    }

    @Override
    public void setTitle(CharSequence title) {
        this.title = title;
        getSupportActionBar().setTitle(title);
    }

    /**
     * Will be called when using the ActionBarDrawerToggle
     * - Sync the toggle state after onRestoreInstanceState occurred
     * @param savedInstanceState
     */
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    /**
     * Will be called when using the ActionBarDrawerToggle
     * - Pass the new configuration changes to the DrawerToggle
     * @param newConfig
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    /**
     * Update the fragment according to the given position
     *
     *  0 - Home
     *  1 - My Body
     *  2 - Schedule
     *  3 - Statistics
     *  4 - Timer
     *
     * @param position
     */
    public void displayView(int position) {
        Fragment fragment = null;

        switch (position) {
            case 0:
                fragment = new HomeFragment();
                break;
            case 1:
                boolean res = checkIfIsThereABodyRegistered(Authentication.getInstance().getUser().getId());
                if (res) {
                    //fragment = new BodyFragment();
                } else {
                    fragment = new NewBodyFragment();
                }
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            default:
                break;
        }

        if (fragment != null) {
            // Here we create the proper methods to "update" the fragment
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment).commit();

            // Set Drawer Item as checked/selected (the list_selector.xml will set the proper events)
            drawerListView.setItemChecked(position, true);
            drawerListView.setSelection(position);
            // change the ActionBar's title
            setTitle(menuTitles[position]);
            // close the Navigation Drawer
            drawerLayout.closeDrawer(drawerListView);
        } else {
            Log.e("MainActivity", "Error while trying to create fragment.");
        }
    }

    /**
     *
     * When the user clicks in the NavigationDrawer Item, it will call the displayView() method
     * passing the item's position
     *
     */
    private class SlideMenuClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            displayView(position);
        }
    }

    public boolean checkIfIsThereABodyRegistered(long userId) {
        dataSource = new MyDataSource(getApplicationContext());
        try {
            dataSource.open();

            boolean res = dataSource.isThereAnyBodyRegistered(userId);
            dataSource.close();
            return res;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
