package com.example.rodri.letsworkout.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rodri.letsworkout.R;
import com.example.rodri.letsworkout.model.DrawerItem;

import java.util.List;

/**
 * Created by rodri on 8/16/2016.
 */
public class DrawerItemAdapter extends ArrayAdapter<DrawerItem> {

    private Activity activity;
    private LayoutInflater inflater = null;
    private List<DrawerItem> drawerItems;

    public DrawerItemAdapter(Activity activity, int textViewResourceId, List<DrawerItem> drawerItems) {
        super(activity, textViewResourceId, drawerItems);
        try {
            this.activity = activity;
            this.drawerItems = drawerItems;

            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getCount() {
        return drawerItems.size();
    }

    public class ViewHolder {
        public ImageView displayIcon;
        public TextView displayTitle;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder holder = new ViewHolder();
        if (convertView == null) {
            v = inflater.inflate(R.layout.drawer_list_item, null);

            holder.displayIcon = (ImageView) v.findViewById(R.id.imgMenuItemIcon);
            holder.displayTitle = (TextView) v.findViewById(R.id.txtMenuItemTitle);

            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        DrawerItem drawerItem = drawerItems.get(position);
        holder.displayIcon.setImageResource(drawerItem.getIcon());
        holder.displayTitle.setText(drawerItem.getTitle());

        return v;
    }
}
