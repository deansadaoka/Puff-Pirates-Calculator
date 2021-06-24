package com.puffpiratestudios;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class SetSeasonDetailsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final int S1_START_DATE = 1;
    private static final int S2_START_DATE = 2;

    String s1Name;
    String s2Name;
    String s3Name;
    String s4Name;

    int[] s1StartDate;
    int[] s2StartDate;
    int[] s3StartDate;
    int[] s4StartDate;

    int[] s1EndDate;
    int[] s2EndDate;
    int[] s3EndDate;
    int[] s4EndDate;

    int numSeasons;

    EditText s1NameText;
    EditText s2NameText;
    EditText s3NameText;
    EditText s4NameText;

    Spinner numSeasonsSpinner;

    Spinner s1StartMonthSpinner;
    Spinner s1StartDaySpinner;
    Spinner s2StartMonthSpinner;
    Spinner s2StartDaySpinner;
    Spinner s3StartMonthSpinner;
    Spinner s3StartDaySpinner;
    Spinner s4StartMonthSpinner;
    Spinner s4StartDaySpinner;

    Spinner s1EndMonthSpinner;
    Spinner s1EndDaySpinner;
    Spinner s2EndMonthSpinner;
    Spinner s2EndDaySpinner;
    Spinner s3EndMonthSpinner;
    Spinner s3EndDaySpinner;
    Spinner s4EndMonthSpinner;
    Spinner s4EndDaySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_season_details);

        TextView title = (TextView) findViewById(R.id.SeasonDetailTitle);
        title.setBackgroundColor(Color.parseColor("#008b8b"));
        title.setTextColor(getResources().getColor(R.color.white));

        TextView s1Label = (TextView) findViewById(R.id.s1Label);
        s1Label.setBackgroundColor(Color.parseColor("#90ee90"));
        s1Label.setTextColor(getResources().getColor(R.color.black));

        TextView s2Label = (TextView) findViewById(R.id.s2Label);
        s2Label.setBackgroundColor(Color.parseColor("#90ee90"));
        s2Label.setTextColor(getResources().getColor(R.color.black));

        TextView s3Label = (TextView) findViewById(R.id.s3Label);
        s3Label.setBackgroundColor(Color.parseColor("#90ee90"));
        s3Label.setTextColor(getResources().getColor(R.color.black));

        TextView s4Label = (TextView) findViewById(R.id.s4Label);
        s4Label.setBackgroundColor(Color.parseColor("#90ee90"));
        s4Label.setTextColor(getResources().getColor(R.color.black));


        s1NameText = findViewById(R.id.s1NameEdit);
        s2NameText = findViewById(R.id.s2NameEdit);
        s3NameText = findViewById(R.id.s3NameEdit);
        s4NameText = findViewById(R.id.s4NameEdit);

        Intent i = getIntent();

        s1StartDate = i.getIntArrayExtra("s1StartDate");
        s2StartDate = i.getIntArrayExtra("s2StartDate");
        s3StartDate = i.getIntArrayExtra("s3StartDate");
        s4StartDate = i.getIntArrayExtra("s4StartDate");

        s1EndDate = i.getIntArrayExtra("s1EndDate");
        s2EndDate = i.getIntArrayExtra("s2EndDate");
        s3EndDate = i.getIntArrayExtra("s3EndDate");
        s4EndDate = i.getIntArrayExtra("s4EndDate");

        s1Name = i.getStringExtra("s1Name");
        s2Name = i.getStringExtra("s2Name");
        s3Name = i.getStringExtra("s3Name");
        s4Name = i.getStringExtra("s4Name");

        s1NameText.setText(s1Name);
        s2NameText.setText(s2Name);
        s3NameText.setText(s3Name);
        s4NameText.setText(s4Name);

        numSeasons = i.getIntExtra("numSeasons", 1);

        String[] tierItems = new String[] {"1", "2", "3", "4"};
        ArrayAdapter<String> tierAdapter = new ArrayAdapter<String>(SetSeasonDetailsActivity.this,
                android.R.layout.simple_spinner_item,tierItems);
        tierAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        numSeasonsSpinner = findViewById(R.id.seasonsSpinner);
        numSeasonsSpinner.setAdapter(tierAdapter);
        numSeasonsSpinner.setOnItemSelectedListener(this);
        numSeasonsSpinner.setSelection(numSeasons - 1);


        String[] monthItems = new String[] {"Month", "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        String[] dayItems = new String[] {"Day", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
        ArrayAdapter<String> monthAdapter = new ArrayAdapter<String>(SetSeasonDetailsActivity.this,
                android.R.layout.simple_spinner_item,monthItems);
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> dayAdapter = new ArrayAdapter<String>(SetSeasonDetailsActivity.this,
                android.R.layout.simple_spinner_item,dayItems);
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        s1StartMonthSpinner = findViewById(R.id.s1StartMonthSpinner);
        s1StartMonthSpinner.setAdapter(monthAdapter);
        s1StartMonthSpinner.setOnItemSelectedListener(this);
        s1StartMonthSpinner.setSelection(s1StartDate[0]);
        s1StartDaySpinner = findViewById(R.id.s1StartDaySpinner);
        s1StartDaySpinner.setAdapter(dayAdapter);
        s1StartDaySpinner.setOnItemSelectedListener(this);
        s1StartDaySpinner.setSelection(s1StartDate[1]);

        s2StartMonthSpinner = findViewById(R.id.s2StartMonthSpinner);
        s2StartMonthSpinner.setAdapter(monthAdapter);
        s2StartMonthSpinner.setOnItemSelectedListener(this);
        s2StartMonthSpinner.setSelection(s2StartDate[0]);
        s2StartDaySpinner = findViewById(R.id.s2StartDaySpinner);
        s2StartDaySpinner.setAdapter(dayAdapter);
        s2StartDaySpinner.setOnItemSelectedListener(this);
        s2StartDaySpinner.setSelection(s2StartDate[1]);

        s3StartMonthSpinner = findViewById(R.id.s3StartMonthSpinner);
        s3StartMonthSpinner.setAdapter(monthAdapter);
        s3StartMonthSpinner.setOnItemSelectedListener(this);
        s3StartMonthSpinner.setSelection(s3StartDate[0]);
        s3StartDaySpinner = findViewById(R.id.s3StartDaySpinner);
        s3StartDaySpinner.setAdapter(dayAdapter);
        s3StartDaySpinner.setOnItemSelectedListener(this);
        s3StartDaySpinner.setSelection(s3StartDate[1]);

        s4StartMonthSpinner = findViewById(R.id.s4StartMonthSpinner);
        s4StartMonthSpinner.setAdapter(monthAdapter);
        s4StartMonthSpinner.setOnItemSelectedListener(this);
        s4StartMonthSpinner.setSelection(s4StartDate[0]);
        s4StartDaySpinner = findViewById(R.id.s4StartDaySpinner);
        s4StartDaySpinner.setAdapter(dayAdapter);
        s4StartDaySpinner.setOnItemSelectedListener(this);
        s4StartDaySpinner.setSelection(s4StartDate[1]);

        s1EndMonthSpinner = findViewById(R.id.s1EndMonthSpinner);
        s1EndMonthSpinner.setAdapter(monthAdapter);
        s1EndMonthSpinner.setOnItemSelectedListener(this);
        s1EndMonthSpinner.setSelection(s1EndDate[0]);
        s1EndDaySpinner = findViewById(R.id.s1EndDaySpinner);
        s1EndDaySpinner.setAdapter(dayAdapter);
        s1EndDaySpinner.setOnItemSelectedListener(this);
        s1EndDaySpinner.setSelection(s1EndDate[1]);

        s2EndMonthSpinner = findViewById(R.id.s2EndMonthSpinner);
        s2EndMonthSpinner.setAdapter(monthAdapter);
        s2EndMonthSpinner.setOnItemSelectedListener(this);
        s2EndMonthSpinner.setSelection(s2EndDate[0]);
        s2EndDaySpinner = findViewById(R.id.s2EndDaySpinner);
        s2EndDaySpinner.setAdapter(dayAdapter);
        s2EndDaySpinner.setOnItemSelectedListener(this);
        s2EndDaySpinner.setSelection(s2EndDate[1]);

        s3EndMonthSpinner = findViewById(R.id.s3EndMonthSpinner);
        s3EndMonthSpinner.setAdapter(monthAdapter);
        s3EndMonthSpinner.setOnItemSelectedListener(this);
        s3EndMonthSpinner.setSelection(s3EndDate[0]);
        s3EndDaySpinner = findViewById(R.id.s3EndDaySpinner);
        s3EndDaySpinner.setAdapter(dayAdapter);
        s3EndDaySpinner.setOnItemSelectedListener(this);
        s3EndDaySpinner.setSelection(s3EndDate[1]);

        s4EndMonthSpinner = findViewById(R.id.s4EndMonthSpinner);
        s4EndMonthSpinner.setAdapter(monthAdapter);
        s4EndMonthSpinner.setOnItemSelectedListener(this);
        s4EndMonthSpinner.setSelection(s4EndDate[0]);
        s4EndDaySpinner = findViewById(R.id.s4EndDaySpinner);
        s4EndDaySpinner.setAdapter(dayAdapter);
        s4EndDaySpinner.setOnItemSelectedListener(this);
        s4EndDaySpinner.setSelection(s4EndDate[1]);


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        switch(parent.getId()) {
            case R.id.seasonsSpinner:
                setSeasonsVisible(Integer.parseInt((String) parent.getSelectedItem()));
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

    private void setSeasonsVisible(int numSeasons) {
        switch (numSeasons) {
            case 1:
                findViewById(R.id.s2Layout).setVisibility(View.GONE);
                findViewById(R.id.s3Layout).setVisibility(View.GONE);
                findViewById(R.id.s4Layout).setVisibility(View.GONE);
                break;
            case 2:
                findViewById(R.id.s2Layout).setVisibility(View.VISIBLE);
                findViewById(R.id.s3Layout).setVisibility(View.GONE);
                findViewById(R.id.s4Layout).setVisibility(View.GONE);
                break;
            case 3:
                findViewById(R.id.s2Layout).setVisibility(View.VISIBLE);
                findViewById(R.id.s3Layout).setVisibility(View.VISIBLE);
                findViewById(R.id.s4Layout).setVisibility(View.GONE);
                break;
            case 4:
                findViewById(R.id.s2Layout).setVisibility(View.VISIBLE);
                findViewById(R.id.s3Layout).setVisibility(View.VISIBLE);
                findViewById(R.id.s4Layout).setVisibility(View.VISIBLE);
                break;
        }
    }

    public void submitSeasonDetails(View v) {
        Intent i = new Intent();

        i.putExtra("s1StartDateMonth", monthStringToInt(s1StartMonthSpinner.getSelectedItem().toString()));
        i.putExtra("s2StartDateMonth", monthStringToInt(s2StartMonthSpinner.getSelectedItem().toString()));
        i.putExtra("s3StartDateMonth", monthStringToInt(s3StartMonthSpinner.getSelectedItem().toString()));
        i.putExtra("s4StartDateMonth", monthStringToInt(s4StartMonthSpinner.getSelectedItem().toString()));

        i.putExtra("s1EndDateMonth", monthStringToInt(s1EndMonthSpinner.getSelectedItem().toString()));
        i.putExtra("s2EndDateMonth", monthStringToInt(s2EndMonthSpinner.getSelectedItem().toString()));
        i.putExtra("s3EndDateMonth", monthStringToInt(s3EndMonthSpinner.getSelectedItem().toString()));
        i.putExtra("s4EndDateMonth", monthStringToInt(s4EndMonthSpinner.getSelectedItem().toString()));

        i.putExtra("s1StartDateDay", dayStringToInt(s1StartDaySpinner.getSelectedItem().toString()));
        i.putExtra("s2StartDateDay", dayStringToInt(s2StartDaySpinner.getSelectedItem().toString()));
        i.putExtra("s3StartDateDay", dayStringToInt(s3StartDaySpinner.getSelectedItem().toString()));
        i.putExtra("s4StartDateDay", dayStringToInt(s4StartDaySpinner.getSelectedItem().toString()));

        i.putExtra("s1EndDateDay", dayStringToInt(s1EndDaySpinner.getSelectedItem().toString()));
        i.putExtra("s2EndDateDay", dayStringToInt(s2EndDaySpinner.getSelectedItem().toString()));
        i.putExtra("s3EndDateDay", dayStringToInt(s3EndDaySpinner.getSelectedItem().toString()));
        i.putExtra("s4EndDateDay", dayStringToInt(s4EndDaySpinner.getSelectedItem().toString()));

        i.putExtra("s1Name", s1NameText.getText().toString());
        i.putExtra("s2Name", s2NameText.getText().toString());
        i.putExtra("s3Name", s3NameText.getText().toString());
        i.putExtra("s4Name", s4NameText.getText().toString());

        i.putExtra("numSeasons", Integer.parseInt(numSeasonsSpinner.getSelectedItem().toString()));

        setResult(RESULT_OK, i);
        finish();
    }

    public void cancel(View v) {
        Intent i = new Intent();
        setResult(RESULT_CANCELED, i);
        finish();
    }

    /**
     * Converts date from String (MM/DD) to int array [Month, Day]
     * @param s string of date in format of MM/DD
     * @return int array of date in format of [Month, Day]
     */
    public int[] dateStringToInt(String s) {
        int[] d = new int[2];
        String[] vals = s.split("/");
        try {
            d[0] = Integer.parseInt(vals[0]);
            d[1] = Integer.parseInt(vals[1]);
        }
        catch (Exception e) {
            d[0] = 0;
            d[1] = 0;
        }
        return d;
    }

    /**
     * Returns string in number format MM/DD
     * @param date date [Month, Day]
     * @return returns string of start date
     */
    public String dateToString(int[] date) {
        if (date[0] == 0) {
            return "MM/DD";
        }
        return (new StringBuilder().append(String.format("%02d", date[0])).append("/")
                .append(String.format("%02d", date[1])).toString());
    }

    public int monthStringToInt(String m) {
        switch(m) {
            case "Jan":
                return 1;
            case "Feb":
                return 2;
            case "Mar":
                return 3;
            case "Apr":
                return 4;
            case "May":
                return 5;
            case "Jun":
                return 6;
            case "Jul":
                return 7;
            case "Aug":
                return 8;
            case "Sep":
                return 9;
            case "Oct":
                return 10;
            case "Nov":
                return 11;
            case "Dec":
                return 12;
            default:
                return 0;
        }
    }

    public int dayStringToInt(String d) {
        if (d.equals("Day")) {
            return 0;
        }
        else {
            return Integer.parseInt(d);
        }
    }



}