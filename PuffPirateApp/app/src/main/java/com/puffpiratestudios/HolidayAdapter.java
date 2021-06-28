package com.puffpiratestudios;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import java.util.HashMap;

public class HolidayAdapter extends ArrayAdapter<HolidayDataSet> implements View.OnClickListener {

    private static class ViewHolder {
        TextView name;
        TextView date;
    }

    public HolidayAdapter() {

    }

}
