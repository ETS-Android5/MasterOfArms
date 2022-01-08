package com.example.shootingranges.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.shootingranges.R;

public class PelletAdapter extends BaseAdapter {

    Context context;
    String[] pellets;
    LayoutInflater inflter;

    public PelletAdapter(Context context, String[] pellets) {
        this.context = context;
        this.pellets = pellets;
        this.inflter = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return pellets.length;
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
        view = inflter.inflate(R.layout.custom_pellet_spinner_items, null);
        TextView names = (TextView) view.findViewById(R.id.textView);
        names.setText(pellets[i]);
        return view;
    }
}
