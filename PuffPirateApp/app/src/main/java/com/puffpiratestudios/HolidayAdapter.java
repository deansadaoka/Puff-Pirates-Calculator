package com.puffpiratestudios;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;

import com.google.android.material.snackbar.Snackbar;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class HolidayAdapter extends ArrayAdapter<HolidayData> implements View.OnClickListener {

    private ArrayList<HolidayData> dataSet;
    Context mContext;


    public HolidayAdapter(ArrayList<HolidayData> data, Context context) {
        super(context, R.layout.row_item, data);
        this.dataSet = data;
        this.mContext = context;
    }

    @Override
    public void onClick(View v) {}


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_item, parent, false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.holidayName);
        TextView date = (TextView) convertView.findViewById(R.id.holidayDate);

        name.setText(dataSet.get(position).getName());
        date.setText(dataSet.get(position).getDateString());

        LinearLayout rowLayout = (LinearLayout)convertView.findViewById(R.id.holidayRowLayout);

        // if even, background is light blue, if odd, background is white
        if (position % 2 == 0) {
            rowLayout.setBackgroundColor(Color.parseColor("#c7f5ff"));
        }
        else {
            rowLayout.setBackgroundColor(Color.parseColor("#e3e3e3"));
        }

        return convertView;
    }

}
