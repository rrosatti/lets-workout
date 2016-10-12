package com.example.rodri.letsworkout.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rodri.letsworkout.R;

/**
 * Created by rodri on 10/12/2016.
 */

public class StatisticsFragment extends Fragment {

    private RecyclerView listOfStatistics;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_statistics, container, false);

        listOfStatistics = (RecyclerView) v.findViewById(R.id.fragmentStatistics_listOfStatistics);

        return v;
    }
}
