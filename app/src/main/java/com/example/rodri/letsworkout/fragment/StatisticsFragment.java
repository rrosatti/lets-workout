package com.example.rodri.letsworkout.fragment;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rodri.letsworkout.R;
import com.example.rodri.letsworkout.adapter.StatisticsAdapter;
import com.example.rodri.letsworkout.model.StatisticsMenuItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by rodri on 10/12/2016.
 */

public class StatisticsFragment extends Fragment {

    private RecyclerView listOfStatistics;
    private List<StatisticsMenuItem> menuItems = new ArrayList<>();
    private List<String> menuItemTitles;
    private TypedArray menuItemIcons;
    StatisticsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_statistics, container, false);

        listOfStatistics = (RecyclerView) v.findViewById(R.id.fragmentStatistics_listOfStatistics);
        menuItemTitles = Arrays.asList(getActivity().getResources().getStringArray(R.array.statistics));
        menuItemIcons = getActivity().getResources().obtainTypedArray(R.array.statistics_menu_icon);

        createStatisticMenuItems();
        adapter = new StatisticsAdapter(this, menuItems);
        listOfStatistics.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        listOfStatistics.setLayoutManager(layoutManager);

        return v;
    }

    public void createStatisticMenuItems() {
        for (int i = 0; i < menuItemTitles.size(); i++) {
            menuItems.add(new StatisticsMenuItem(menuItemTitles.get(i), menuItemIcons.getResourceId(i, -1)));
        }
    }
}
