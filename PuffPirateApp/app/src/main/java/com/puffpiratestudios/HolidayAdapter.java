package com.puffpiratestudios;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        HolidayData holidayData=(HolidayData)object;

        // insert what to do when clicked (edit date)
    }


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

        return convertView;
    }

}
