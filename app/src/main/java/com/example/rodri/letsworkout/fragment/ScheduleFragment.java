package com.example.rodri.letsworkout.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rodri.letsworkout.R;
import com.example.rodri.letsworkout.adapter.ScheduleAdapter;
import com.example.rodri.letsworkout.database.MyDataSource;
import com.example.rodri.letsworkout.model.Day;

import java.util.List;

/**
 * Created by rodri on 10/2/2016.
 */
public class ScheduleFragment extends Fragment {

    private List<Day> days;
    private MyDataSource dataSource;
    private RecyclerView scheduleList;
    private ScheduleAdapter adapter;
    private LinearLayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_schedule, container, false);

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        scheduleList = (RecyclerView) view.findViewById(R.id.fragmentSchedule_listSchedule);
        getDays();
        adapter = new ScheduleAdapter(getActivity(), days);
        scheduleList.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(getContext());
        scheduleList.setLayoutManager(layoutManager);
    }

    public void getDays() {
        dataSource = new MyDataSource(getActivity());
        dataSource.open();
        days = dataSource.getDays();
        dataSource.close();
    }
}
