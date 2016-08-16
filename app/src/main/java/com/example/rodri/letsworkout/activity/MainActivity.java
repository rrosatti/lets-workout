package com.example.rodri.letsworkout.activity;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.example.rodri.letsworkout.R;
import com.example.rodri.letsworkout.adapter.DrawerItemAdapter;
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
}
