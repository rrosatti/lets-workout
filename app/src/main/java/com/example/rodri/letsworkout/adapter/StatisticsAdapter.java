package com.example.rodri.letsworkout.adapter;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rodri.letsworkout.R;
import com.example.rodri.letsworkout.activity.BodyMeasurementsChartActivity;
import com.example.rodri.letsworkout.activity.WeightChartActivity;
import com.example.rodri.letsworkout.model.StatisticsMenuItem;

import java.util.List;

/**
 * Created by rodri on 10/13/2016.
 */

public class StatisticsAdapter extends RecyclerView.Adapter<StatisticsAdapter.MyViewHolder> {

    private List<StatisticsMenuItem> statistics;
    private Fragment fragment;

    public StatisticsAdapter(Fragment fragment, List<StatisticsMenuItem> statistics) {
        this.statistics = statistics;
        this.fragment = fragment;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView displayTitle;
        public ImageView displayIcon;
        public StatisticsMenuItem menuItem;

        public MyViewHolder(View v) {
            super(v);

            displayTitle = (TextView) v.findViewById(R.id.customStatisticItem_txtTitle);
            displayIcon = (ImageView) v.findViewById(R.id.customStatisticItem_imgIcon);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (menuItem.getIconId()) {
                        case R.drawable.ic_scale_2_64: {
                            Intent i = new Intent(fragment.getActivity(), WeightChartActivity.class);
                            fragment.startActivity(i);
                            break;
                        }
                        case R.drawable.ic_measuring_tape_64: {
                            Intent i = new Intent(fragment.getActivity(), BodyMeasurementsChartActivity.class);
                            fragment.startActivity(i);
                            break;
                        }
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return statistics.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_statistic_item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        StatisticsMenuItem menuItem = statistics.get(position);

        holder.displayTitle.setText(menuItem.getTitle());
        holder.displayIcon.setImageResource(menuItem.getIconId());
        holder.menuItem = menuItem;
    }

}
