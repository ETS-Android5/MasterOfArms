package com.example.shootingranges.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.shootingranges.R;

public class LaneAdapter extends BaseAdapter {

    Context context;
    String[] lanes;
    LayoutInflater inflter;

    public LaneAdapter(Context context, String[] lanes) {
        this.context = context;
        this.lanes = lanes;
        this.inflter = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return lanes.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.custom_lane_spinner_items, null);
        TextView names = (TextView) view.findViewById(R.id.laneTxtVw);
        names.setText(lanes[i]);
        return view;
    }
}
