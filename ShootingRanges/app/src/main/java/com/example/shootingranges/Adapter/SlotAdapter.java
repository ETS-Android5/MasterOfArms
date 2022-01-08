package com.example.shootingranges.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.shootingranges.R;

public class SlotAdapter extends BaseAdapter {

    Context context;
    String[] slots;
    LayoutInflater inflter;

    public SlotAdapter(Context context, String[] slots) {
        this.context = context;
        this.slots = slots;
        this.inflter = (LayoutInflater.from(context));
    }


    @Override
    public int getCount() {
        return slots.length;
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
        view = inflter.inflate(R.layout.custom_slot_spinner_items, null);
        TextView names = (TextView) view.findViewById(R.id.slotTxtVw);
        names.setText(slots[i]);
        return view;
    }
}
