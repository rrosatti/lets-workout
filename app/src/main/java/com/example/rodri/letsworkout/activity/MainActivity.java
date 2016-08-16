package com.example.rodri.letsworkout.activity;

import android.content.res.TypedArray;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.rodri.letsworkout.R;
import com.example.rodri.letsworkout.adapter.DrawerItemAdapter;
import com.example.rodri.letsworkout.model.DrawerItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ListView drawerListView;
    private ActionBarDrawerToggle drawerToggle; // need to look after it

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
}
