package com.puffpiratestudios;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class SetHolidaysActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Spinner countrySpinner;

    ListView USAListView;
    ListView CAListView;

    private FloatingActionButton addHolidayFAB;

    ArrayList<HolidayData> holidaysUSA = new ArrayList<>();
    ArrayList<HolidayData> holidaysCA = new ArrayList<>();

    int country;

    String nameToBeAdded;

    HolidayAdapter holidayAdapterUSA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_holidays);

        TextView title = (TextView) findViewById(R.id.HolidayTitle);
        title.setBackgroundColor(Color.parseColor("#008b8b"));
        title.setTextColor(getResources().getColor(R.color.white));

        addHolidayFAB = findViewById(R.id.holidayFAB);

        USAListView = findViewById(R.id.USAListView);
        CAListView = findViewById(R.id.CAListView);


        addHolidayFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHolidayDialog();
            }
        });


        Intent i = getIntent();
        country = i.getIntExtra("country", 0);

        addIntentHolidays(holidaysUSA, i.getStringArrayListExtra("holidayNamesUSA"),
                i.getLongArrayExtra("datesUSA"));

        addIntentHolidays(holidaysCA, i.getStringArrayListExtra("holidayNamesCA"),
                i.getLongArrayExtra("datesCA"));


        String[] countryOptions = new String[] {"USA", "Canada"};
        ArrayAdapter<String> countryAdapter = new ArrayAdapter<String>(SetHolidaysActivity.this,
                android.R.layout.simple_spinner_item,countryOptions);
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        countrySpinner = findViewById(R.id.countrySpinner);
        countrySpinner.setAdapter(countryAdapter);
        countrySpinner.setOnItemSelectedListener(this);
        countrySpinner.setSelection(country);


        holidayAdapterUSA = new HolidayAdapter(holidaysUSA, getApplicationContext());
        USAListView = findViewById(R.id.USAListView);
        USAListView.setAdapter(holidayAdapterUSA);



    }

    public void submit(View v) {
        Intent i = new Intent();

        i.putExtra("holidayNamesUSA", getHolidayNames(holidaysUSA));
        i.putExtra("holidayNamesCA", getHolidayNames(holidaysCA));

        i.putExtra("datesUSA", getHolidayDates(holidaysUSA));
        i.putExtra("datesCA", getHolidayDates(holidaysCA));

        setResult(RESULT_OK, i);
        finish();
    }

    public void cancel(View v) {
        Intent i = new Intent();
        setResult(RESULT_CANCELED, i);
        finish();
    }


    private void openHolidayDialog() {
        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Enter Holiday Details");

        final EditText nameEdit = new EditText(this);
        nameEdit.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        builder.setView(nameEdit);

        builder.setPositiveButton("Set Date", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                nameToBeAdded = nameEdit.getText().toString();
                openDateSetDialog();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            if (country == 0) {
                holidaysUSA.add(new HolidayData(nameToBeAdded, (new GregorianCalendar(year, month, dayOfMonth)).getTime().getTime()));
                holidayAdapterUSA.notifyDataSetChanged();
            }
            else {
                holidaysCA.add(new HolidayData(nameToBeAdded, (new GregorianCalendar(year, month, dayOfMonth)).getTime().getTime()));
            }
        }
    };

    public void openDateSetDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, AlertDialog.THEME_HOLO_DARK, onDateSetListener,
                Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setTitle("Set Date");
        datePickerDialog.show();
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getSelectedItemPosition()) {
            case 0:
                country = 0;
                USAListView.setVisibility(View.VISIBLE);
                CAListView.setVisibility(View.GONE);
                break;
            case 1:
                country = 1;
                USAListView.setVisibility(View.GONE);
                CAListView.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

    private void addIntentHolidays(ArrayList<HolidayData> data, ArrayList<String> names, long[] dates) {
        for (int i = 0; i < names.size(); i++) {
            data.add(new HolidayData(names.get(i), dates[i]));
        }
    }

    private ArrayList<String> getHolidayNames(ArrayList<HolidayData> data) {
        ArrayList<String> names = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            names.add(data.get(i).getName());
        }
        return names;
    }

    private long[] getHolidayDates(ArrayList<HolidayData> data) {
        long[] dates = new long[data.size()];
        for (int i = 0; i < data.size(); i++) {
            dates[i] = data.get(i).getDate();
        }
        return dates;
    }

}