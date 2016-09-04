package com.example.rodri.letsworkout.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rodri.letsworkout.R;
import com.example.rodri.letsworkout.model.MuscleGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodri on 9/1/2016.
 */
public class MuscleGroupAdapter extends ArrayAdapter<MuscleGroup> {

    private Activity activity;
    private List<MuscleGroup> muscleGroup;
    private LayoutInflater inflater = null;
    private boolean[] selected;

    public MuscleGroupAdapter(Activity activity, int textViewResourceId, List<MuscleGroup> muscleGroup) {
        super(activity, textViewResourceId, muscleGroup);
        try {
            this.activity = activity;
            this.muscleGroup = muscleGroup;
            this.selected = new boolean[muscleGroup.size()];

            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getCount() {
        return muscleGroup.size();
    }

    public class ViewHolder {
        public TextView displayMuscleGroup;
        public CheckBox displayCheck;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder holder = new ViewHolder();
        if (convertView == null) {
            v = inflater.inflate(R.layout.custom_muscle_group_item, null);

            holder.displayMuscleGroup = (TextView) v.findViewById(R.id.customMuscleGroupItem_txtMuscleGroup);
            holder.displayCheck = (CheckBox) v.findViewById(R.id.customMuscleGroupItem_check);

            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        holder.displayMuscleGroup.setText(muscleGroup.get(position).getName());
        holder.displayCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selected[position] == true) {
                    selected[position] = false;
                } else {
                    selected[position] = true;
                }
            }
        });

        return v;

    }

    public List<MuscleGroup> getSelectedItems() {
        List<MuscleGroup> selectedItems = new ArrayList<>();
        for (int i = 0; i < selected.length; i++) {
            if (selected[i]) {
                selectedItems.add(muscleGroup.get(i));
            }
        }

        return selectedItems;
    }
}
