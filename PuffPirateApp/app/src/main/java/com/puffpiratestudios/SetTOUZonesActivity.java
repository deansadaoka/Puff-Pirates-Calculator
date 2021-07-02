package com.puffpiratestudios;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

public class SetTOUZonesActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private static final int S1_OUT_TO_Z1_TIME = 1;
    private static final int S1_Z1_TO_Z2_TIME = 2;
    private static final int S1_Z2_TO_Z3_TIME = 3;
    private static final int S1_Z3_TO_OUT_TIME = 4;

    private static final int S2_OUT_TO_Z1_TIME = 5;
    private static final int S2_Z1_TO_Z2_TIME = 6;
    private static final int S2_Z2_TO_Z3_TIME = 7;
    private static final int S2_Z3_TO_OUT_TIME = 8;

    private static final int S3_OUT_TO_Z1_TIME = 9;
    private static final int S3_Z1_TO_Z2_TIME = 10;
    private static final int S3_Z2_TO_Z3_TIME = 11;
    private static final int S3_Z3_TO_OUT_TIME = 12;

    private static final int S4_OUT_TO_Z1_TIME = 13;
    private static final int S4_Z1_TO_Z2_TIME = 14;
    private static final int S4_Z2_TO_Z3_TIME = 15;
    private static final int S4_Z3_TO_OUT_TIME = 16;


    Button s1z1FromButton;
    Button s1z1ToButton;
    Button s1z2FromButton;
    Button s1z2ToButton;
    Button s1z3FromButton;
    Button s1z3ToButton;

    Button s2z1FromButton;
    Button s2z1ToButton;
    Button s2z2FromButton;
    Button s2z2ToButton;
    Button s2z3FromButton;
    Button s2z3ToButton;

    Button s3z1FromButton;
    Button s3z1ToButton;
    Button s3z2FromButton;
    Button s3z2ToButton;
    Button s3z3FromButton;
    Button s3z3ToButton;

    Button s4z1FromButton;
    Button s4z1ToButton;
    Button s4z2FromButton;
    Button s4z2ToButton;
    Button s4z3FromButton;
    Button s4z3ToButton;


    EditText z1NameEdit;
    EditText z2NameEdit;
    EditText z3NameEdit;


    EditText s1z1RateEdit;
    EditText s1z2RateEdit;
    EditText s1z3RateEdit;

    EditText s2z1RateEdit;
    EditText s2z2RateEdit;
    EditText s2z3RateEdit;

    EditText s3z1RateEdit;
    EditText s3z2RateEdit;
    EditText s3z3RateEdit;

    EditText s4z1RateEdit;
    EditText s4z2RateEdit;
    EditText s4z3RateEdit;


    int s1_out_z1_hour;
    int s1_out_z1_min;
    int s1_z1_z2_hour;
    int s1_z1_z2_min;
    int s1_z2_z3_hour;
    int s1_z2_z3_min;
    int s1_z3_out_hour;
    int s1_z3_out_min;

    int s2_out_z1_hour;
    int s2_out_z1_min;
    int s2_z1_z2_hour;
    int s2_z1_z2_min;
    int s2_z2_z3_hour;
    int s2_z2_z3_min;
    int s2_z3_out_hour;
    int s2_z3_out_min;

    int s3_out_z1_hour;
    int s3_out_z1_min;
    int s3_z1_z2_hour;
    int s3_z1_z2_min;
    int s3_z2_z3_hour;
    int s3_z2_z3_min;
    int s3_z3_out_hour;
    int s3_z3_out_min;

    int s4_out_z1_hour;
    int s4_out_z1_min;
    int s4_z1_z2_hour;
    int s4_z1_z2_min;
    int s4_z2_z3_hour;
    int s4_z2_z3_min;
    int s4_z3_out_hour;
    int s4_z3_out_min;


    TextView s1OutFrom;
    TextView s1OutTo;
    TextView s2OutFrom;
    TextView s2OutTo;
    TextView s3OutFrom;
    TextView s3OutTo;
    TextView s4OutFrom;
    TextView s4OutTo;


    EditText s1OutRateEdit;
    EditText s2OutRateEdit;
    EditText s3OutRateEdit;
    EditText s4OutRateEdit;


    int numZones;

    Spinner zoneSpinner;


    String z1Name;
    String z2Name;
    String z3Name;


    double s1z1Rate;
    double s1z2Rate;
    double s1z3Rate;
    double s1OutRate;

    double s2z1Rate;
    double s2z2Rate;
    double s2z3Rate;
    double s2OutRate;

    double s3z1Rate;
    double s3z2Rate;
    double s3z3Rate;
    double s3OutRate;

    double s4z1Rate;
    double s4z2Rate;
    double s4z3Rate;
    double s4OutRate;


    int currentTimeEdit;

    int numSeasons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_touzones);

        TextView title = (TextView) findViewById(R.id.ZoneTitle);
        title.setBackgroundColor(Color.parseColor("#d71414"));
        title.setTextColor(getResources().getColor(R.color.white));

        TextView s1Title = (TextView) findViewById(R.id.s1ZoneTitle);
        s1Title.setBackgroundColor(Color.parseColor("#30bf04"));
        s1Title.setTextColor(getResources().getColor(R.color.white));

        TextView s2Title = (TextView) findViewById(R.id.s2ZoneTitle);
        s2Title.setBackgroundColor(Color.parseColor("#30bf04"));
        s2Title.setTextColor(getResources().getColor(R.color.white));

        TextView s3Title = (TextView) findViewById(R.id.s3ZoneTitle);
        s3Title.setBackgroundColor(Color.parseColor("#30bf04"));
        s3Title.setTextColor(getResources().getColor(R.color.white));

        TextView s4Title = (TextView) findViewById(R.id.s4ZoneTitle);
        s4Title.setBackgroundColor(Color.parseColor("#30bf04"));
        s4Title.setTextColor(getResources().getColor(R.color.white));


        z1NameEdit = findViewById(R.id.z1NameEdit);
        z2NameEdit = findViewById(R.id.z2NameEdit);
        z3NameEdit = findViewById(R.id.z3NameEdit);


        s1z1RateEdit = findViewById(R.id.s1z1RateEdit);
        s2z1RateEdit = findViewById(R.id.s2z1RateEdit);
        s3z1RateEdit = findViewById(R.id.s3z1RateEdit);
        s4z1RateEdit = findViewById(R.id.s4z1RateEdit);

        s1z2RateEdit = findViewById(R.id.s1z2RateEdit);
        s2z2RateEdit = findViewById(R.id.s2z2RateEdit);
        s3z2RateEdit = findViewById(R.id.s3z2RateEdit);
        s4z2RateEdit = findViewById(R.id.s4z2RateEdit);

        s1z3RateEdit = findViewById(R.id.s1z3RateEdit);
        s2z3RateEdit = findViewById(R.id.s2z3RateEdit);
        s3z3RateEdit = findViewById(R.id.s3z3RateEdit);
        s4z3RateEdit = findViewById(R.id.s4z3RateEdit);

        s1OutRateEdit = findViewById(R.id.s1OutRateEdit);
        s2OutRateEdit = findViewById(R.id.s2OutRateEdit);
        s3OutRateEdit = findViewById(R.id.s3OutRateEdit);
        s4OutRateEdit = findViewById(R.id.s4OutRateEdit);


        s1z1FromButton = findViewById(R.id.s1z1FromButton);
        s2z1FromButton = findViewById(R.id.s2z1FromButton);
        s3z1FromButton = findViewById(R.id.s3z1FromButton);
        s4z1FromButton = findViewById(R.id.s4z1FromButton);
        s1z1ToButton = findViewById(R.id.s1z1ToButton);
        s2z1ToButton = findViewById(R.id.s2z1ToButton);
        s3z1ToButton = findViewById(R.id.s3z1ToButton);
        s4z1ToButton = findViewById(R.id.s4z1ToButton);

        s1z2FromButton = findViewById(R.id.s1z2FromButton);
        s2z2FromButton = findViewById(R.id.s2z2FromButton);
        s3z2FromButton = findViewById(R.id.s3z2FromButton);
        s4z2FromButton = findViewById(R.id.s4z2FromButton);
        s1z2ToButton = findViewById(R.id.s1z2ToButton);
        s2z2ToButton = findViewById(R.id.s2z2ToButton);
        s3z2ToButton = findViewById(R.id.s3z2ToButton);
        s4z2ToButton = findViewById(R.id.s4z2ToButton);

        s1z3FromButton = findViewById(R.id.s1z3FromButton);
        s2z3FromButton = findViewById(R.id.s2z3FromButton);
        s3z3FromButton = findViewById(R.id.s3z3FromButton);
        s4z3FromButton = findViewById(R.id.s4z3FromButton);
        s1z3ToButton = findViewById(R.id.s1z3ToButton);
        s2z3ToButton = findViewById(R.id.s2z3ToButton);
        s3z3ToButton = findViewById(R.id.s3z3ToButton);
        s4z3ToButton = findViewById(R.id.s4z3ToButton);

        s1OutFrom = findViewById(R.id.s1OutFrom);
        s2OutFrom = findViewById(R.id.s2OutFrom);
        s3OutFrom = findViewById(R.id.s3OutFrom);
        s4OutFrom = findViewById(R.id.s4OutFrom);
        s1OutTo = findViewById(R.id.s1OutTo);
        s2OutTo = findViewById(R.id.s2OutTo);
        s3OutTo = findViewById(R.id.s3OutTo);
        s4OutTo = findViewById(R.id.s4OutTo);


        Intent i = getIntent();

        numSeasons = i.getIntExtra("numSeasons", 1);

        numZones = i.getIntExtra("numZones", 2);

        z1Name = i.getStringExtra("z1Name");
        z2Name = i.getStringExtra("z2Name");
        z3Name = i.getStringExtra("z3Name");
        z1NameEdit.setText(z1Name);
        z2NameEdit.setText(z2Name);
        z3NameEdit.setText(z3Name);

        s1z1Rate = i.getDoubleExtra("s1z1Rate", 0);
        s2z1Rate = i.getDoubleExtra("s2z1Rate", 0);
        s3z1Rate = i.getDoubleExtra("s3z1Rate", 0);
        s4z1Rate = i.getDoubleExtra("s4z1Rate", 0);
        s1z1RateEdit.setText(String.format("%.04f", s1z1Rate));
        s2z1RateEdit.setText(String.format("%.04f", s2z1Rate));
        s3z1RateEdit.setText(String.format("%.04f", s3z1Rate));
        s4z1RateEdit.setText(String.format("%.04f", s4z1Rate));

        s1z2Rate = i.getDoubleExtra("s1z2Rate", 0);
        s2z2Rate = i.getDoubleExtra("s2z2Rate", 0);
        s3z2Rate = i.getDoubleExtra("s3z2Rate", 0);
        s4z2Rate = i.getDoubleExtra("s4z2Rate", 0);
        s1z2RateEdit.setText(String.format("%.04f", s1z2Rate));
        s2z2RateEdit.setText(String.format("%.04f", s2z2Rate));
        s3z2RateEdit.setText(String.format("%.04f", s3z2Rate));
        s4z2RateEdit.setText(String.format("%.04f", s4z2Rate));

        s1z3Rate = i.getDoubleExtra("s1z3Rate", 0);
        s2z3Rate = i.getDoubleExtra("s2z3Rate", 0);
        s3z3Rate = i.getDoubleExtra("s3z3Rate", 0);
        s4z3Rate = i.getDoubleExtra("s4z3Rate", 0);
        s1z3RateEdit.setText(String.format("%.04f", s1z3Rate));
        s2z3RateEdit.setText(String.format("%.04f", s2z3Rate));
        s3z3RateEdit.setText(String.format("%.04f", s3z3Rate));
        s4z3RateEdit.setText(String.format("%.04f", s4z3Rate));

        s1OutRate = i.getDoubleExtra("s1OutRate", 0);
        s2OutRate = i.getDoubleExtra("s2OutRate", 0);
        s3OutRate = i.getDoubleExtra("s3OutRate", 0);
        s4OutRate = i.getDoubleExtra("s4OutRate", 0);
        s1OutRateEdit.setText(String.format("%.04f", s1OutRate));
        s2OutRateEdit.setText(String.format("%.04f", s2OutRate));
        s3OutRateEdit.setText(String.format("%.04f", s3OutRate));
        s4OutRateEdit.setText(String.format("%.04f", s4OutRate));



        String[] zoneOptions = new String[] {"2", "3", "4"};
        ArrayAdapter<String> zoneAdapter = new ArrayAdapter<String>(SetTOUZonesActivity.this,
                android.R.layout.simple_spinner_item,zoneOptions);
        zoneAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        zoneSpinner = findViewById(R.id.zoneSpinner);
        zoneSpinner.setAdapter(zoneAdapter);
        zoneSpinner.setOnItemSelectedListener(this);
        zoneSpinner.setSelection(numZones - 2);


        setSeasonsVisible(numSeasons);


        s1_out_z1_hour = i.getIntExtra("s1_z1_from_hour", 0);
        s1_out_z1_min = i.getIntExtra("s1_z1_from_min", 0);
        s1_z1_z2_hour = i.getIntExtra("s1_z2_from_hour", 0);
        s1_z1_z2_min = i.getIntExtra("s1_z2_from_min", 0);
        s1_z2_z3_hour = i.getIntExtra("s1_z3_from_hour", 0);
        s1_z2_z3_min = i.getIntExtra("s1_z3_from_min", 0);
        s1_z3_out_hour = i.getIntExtra("s1_out_from_hour", 0);
        s1_z3_out_min = i.getIntExtra("s1_out_from_min", 0);

        s1OutTo.setText(formatTime(s1_out_z1_hour, s1_out_z1_min));
        s1z1FromButton.setText(formatTime(s1_out_z1_hour, s1_out_z1_min));
        s1z1ToButton.setText(formatTime(s1_z1_z2_hour, s1_z1_z2_min));
        s1z2FromButton.setText(formatTime(s1_z1_z2_hour, s1_z1_z2_min));
        s1z2ToButton.setText(formatTime(s1_z2_z3_hour, s1_z2_z3_min));
        s1z3FromButton.setText(formatTime(s1_z2_z3_hour, s1_z2_z3_min));
        s1z3ToButton.setText(formatTime(s1_z3_out_hour, s1_z3_out_min));
        s1OutFrom.setText(formatTime(s1_z3_out_hour, s1_z3_out_min));

        s2_out_z1_hour = i.getIntExtra("s2_z1_from_hour", 0);
        s2_out_z1_min = i.getIntExtra("s2_z1_from_min", 0);
        s2_z1_z2_hour = i.getIntExtra("s2_z2_from_hour", 0);
        s2_z1_z2_min = i.getIntExtra("s2_z2_from_min", 0);
        s2_z2_z3_hour = i.getIntExtra("s2_z3_from_hour", 0);
        s2_z2_z3_min = i.getIntExtra("s2_z3_from_min", 0);
        s2_z3_out_hour = i.getIntExtra("s2_out_from_hour", 0);
        s2_z3_out_min = i.getIntExtra("s2_out_from_min", 0);

        s2OutTo.setText(formatTime(s2_out_z1_hour, s2_out_z1_min));
        s2z1FromButton.setText(formatTime(s2_out_z1_hour, s2_out_z1_min));
        s2z1ToButton.setText(formatTime(s2_z1_z2_hour, s2_z1_z2_min));
        s2z2FromButton.setText(formatTime(s2_z1_z2_hour, s2_z1_z2_min));
        s2z2ToButton.setText(formatTime(s2_z2_z3_hour, s2_z2_z3_min));
        s2z3FromButton.setText(formatTime(s2_z2_z3_hour, s2_z2_z3_min));
        s2z3ToButton.setText(formatTime(s2_z3_out_hour, s2_z3_out_min));
        s2OutFrom.setText(formatTime(s2_z3_out_hour, s2_z3_out_min));

        s3_out_z1_hour = i.getIntExtra("s3_z1_from_hour", 0);
        s3_out_z1_min = i.getIntExtra("s3_z1_from_min", 0);
        s3_z1_z2_hour = i.getIntExtra("s3_z2_from_hour", 0);
        s3_z1_z2_min = i.getIntExtra("s3_z2_from_min", 0);
        s3_z2_z3_hour = i.getIntExtra("s3_z3_from_hour", 0);
        s3_z2_z3_min = i.getIntExtra("s3_z3_from_min", 0);
        s3_z3_out_hour = i.getIntExtra("s3_out_from_hour", 0);
        s3_z3_out_min = i.getIntExtra("s3_out_from_min", 0);

        s3OutTo.setText(formatTime(s3_out_z1_hour, s3_out_z1_min));
        s3z1FromButton.setText(formatTime(s3_out_z1_hour, s3_out_z1_min));
        s3z1ToButton.setText(formatTime(s3_z1_z2_hour, s3_z1_z2_min));
        s3z2FromButton.setText(formatTime(s3_z1_z2_hour, s3_z1_z2_min));
        s3z2ToButton.setText(formatTime(s3_z2_z3_hour, s3_z2_z3_min));
        s3z3FromButton.setText(formatTime(s3_z2_z3_hour, s3_z2_z3_min));
        s3z3ToButton.setText(formatTime(s3_z3_out_hour, s3_z3_out_min));
        s3OutFrom.setText(formatTime(s3_z3_out_hour, s3_z3_out_min));

        s4_out_z1_hour = i.getIntExtra("s4_z1_from_hour", 0);
        s4_out_z1_min = i.getIntExtra("s4_z1_from_min", 0);
        s4_z1_z2_hour = i.getIntExtra("s4_z2_from_hour", 0);
        s4_z1_z2_min = i.getIntExtra("s4_z2_from_min", 0);
        s4_z2_z3_hour = i.getIntExtra("s4_z3_from_hour", 0);
        s4_z2_z3_min = i.getIntExtra("s4_z3_from_min", 0);
        s4_z3_out_hour = i.getIntExtra("s4_out_from_hour", 0);
        s4_z3_out_min = i.getIntExtra("s4_out_from_min", 0);

        s4OutTo.setText(formatTime(s4_out_z1_hour, s4_out_z1_min));
        s4z1FromButton.setText(formatTime(s4_out_z1_hour, s4_out_z1_min));
        s4z1ToButton.setText(formatTime(s4_z1_z2_hour, s4_z1_z2_min));
        s4z2FromButton.setText(formatTime(s4_z1_z2_hour, s4_z1_z2_min));
        s4z2ToButton.setText(formatTime(s4_z2_z3_hour, s4_z2_z3_min));
        s4z3FromButton.setText(formatTime(s4_z2_z3_hour, s4_z2_z3_min));
        s4z3ToButton.setText(formatTime(s4_z3_out_hour, s4_z3_out_min));
        s4OutFrom.setText(formatTime(s4_z3_out_hour, s4_z3_out_min));

        set_s1_out_from_text();
        set_s2_out_from_text();
        set_s3_out_from_text();
        set_s4_out_from_text();


    }

    public void submit(View v) {
        Intent i = new Intent();

        i.putExtra("z1Name", z1NameEdit.getText().toString());
        i.putExtra("z2Name", z2NameEdit.getText().toString());
        i.putExtra("z3Name", z3NameEdit.getText().toString());

        i.putExtra("s1z1Rate", Double.parseDouble(s1z1RateEdit.getText().toString()));
        i.putExtra("s2z1Rate", Double.parseDouble(s2z1RateEdit.getText().toString()));
        i.putExtra("s3z1Rate", Double.parseDouble(s3z1RateEdit.getText().toString()));
        i.putExtra("s4z1Rate", Double.parseDouble(s4z1RateEdit.getText().toString()));

        i.putExtra("s1z2Rate", Double.parseDouble(s1z2RateEdit.getText().toString()));
        i.putExtra("s2z2Rate", Double.parseDouble(s2z2RateEdit.getText().toString()));
        i.putExtra("s3z2Rate", Double.parseDouble(s3z2RateEdit.getText().toString()));
        i.putExtra("s4z2Rate", Double.parseDouble(s4z2RateEdit.getText().toString()));

        i.putExtra("s1z3Rate", Double.parseDouble(s1z3RateEdit.getText().toString()));
        i.putExtra("s2z3Rate", Double.parseDouble(s2z3RateEdit.getText().toString()));
        i.putExtra("s3z3Rate", Double.parseDouble(s3z3RateEdit.getText().toString()));
        i.putExtra("s4z3Rate", Double.parseDouble(s4z3RateEdit.getText().toString()));

        i.putExtra("s1OutRate", Double.parseDouble(s1OutRateEdit.getText().toString()));
        i.putExtra("s2OutRate", Double.parseDouble(s2OutRateEdit.getText().toString()));
        i.putExtra("s3OutRate", Double.parseDouble(s3OutRateEdit.getText().toString()));
        i.putExtra("s4OutRate", Double.parseDouble(s4OutRateEdit.getText().toString()));

        i.putExtra("numZones", numZones);

        i.putExtra("s1OutFromHour", s1_z3_out_hour);
        i.putExtra("s1OutFromMin", s1_z3_out_min);
        i.putExtra("s1OutToHour", s1_out_z1_hour);
        i.putExtra("s1OutToMin", s1_out_z1_min);
        i.putExtra("s1z1FromHour", s1_out_z1_hour);
        i.putExtra("s1z1FromMin", s1_out_z1_min);
        i.putExtra("s1z1ToHour", s1_z1_z2_hour);
        i.putExtra("s1z1ToMin", s1_z1_z2_min);
        i.putExtra("s1z2FromHour", s1_z1_z2_hour);
        i.putExtra("s1z2FromMin", s1_z1_z2_min);
        i.putExtra("s1z2ToHour", s1_z2_z3_hour);
        i.putExtra("s1z2ToMin", s1_z2_z3_min);
        i.putExtra("s1z3FromHour", s1_z2_z3_hour);
        i.putExtra("s1z3FromMin", s1_z2_z3_min);
        i.putExtra("s1z3ToHour", s1_z3_out_hour);
        i.putExtra("s1z3ToMin", s1_z3_out_min);

        i.putExtra("s2OutFromHour", s2_z3_out_hour);
        i.putExtra("s2OutFromMin", s2_z3_out_min);
        i.putExtra("s2OutToHour", s2_out_z1_hour);
        i.putExtra("s2OutToMin", s2_out_z1_min);
        i.putExtra("s2z1FromHour", s2_out_z1_hour);
        i.putExtra("s2z1FromMin", s2_out_z1_min);
        i.putExtra("s2z1ToHour", s2_z1_z2_hour);
        i.putExtra("s2z1ToMin", s2_z1_z2_min);
        i.putExtra("s2z2FromHour", s2_z1_z2_hour);
        i.putExtra("s2z2FromMin", s2_z1_z2_min);
        i.putExtra("s2z2ToHour", s2_z2_z3_hour);
        i.putExtra("s2z2ToMin", s2_z2_z3_min);
        i.putExtra("s2z3FromHour", s2_z2_z3_hour);
        i.putExtra("s2z3FromMin", s2_z2_z3_min);
        i.putExtra("s2z3ToHour", s2_z3_out_hour);
        i.putExtra("s2z3ToMin", s2_z3_out_min);

        i.putExtra("s3OutFromHour", s3_z3_out_hour);
        i.putExtra("s3OutFromMin", s3_z3_out_min);
        i.putExtra("s3OutToHour", s3_out_z1_hour);
        i.putExtra("s3OutToMin", s3_out_z1_min);
        i.putExtra("s3z1FromHour", s3_out_z1_hour);
        i.putExtra("s3z1FromMin", s3_out_z1_min);
        i.putExtra("s3z1ToHour", s3_z1_z2_hour);
        i.putExtra("s3z1ToMin", s3_z1_z2_min);
        i.putExtra("s3z2FromHour", s3_z1_z2_hour);
        i.putExtra("s3z2FromMin", s3_z1_z2_min);
        i.putExtra("s3z2ToHour", s3_z2_z3_hour);
        i.putExtra("s3z2ToMin", s3_z2_z3_min);
        i.putExtra("s3z3FromHour", s3_z2_z3_hour);
        i.putExtra("s3z3FromMin", s3_z2_z3_min);
        i.putExtra("s3z3ToHour", s3_z3_out_hour);
        i.putExtra("s3z3ToMin", s3_z3_out_min);

        i.putExtra("s4OutFromHour", s4_z3_out_hour);
        i.putExtra("s4OutFromMin", s4_z3_out_min);
        i.putExtra("s4OutToHour", s4_out_z1_hour);
        i.putExtra("s4OutToMin", s4_out_z1_min);
        i.putExtra("s4z1FromHour", s4_out_z1_hour);
        i.putExtra("s4z1FromMin", s4_out_z1_min);
        i.putExtra("s4z1ToHour", s4_z1_z2_hour);
        i.putExtra("s4z1ToMin", s4_z1_z2_min);
        i.putExtra("s4z2FromHour", s4_z1_z2_hour);
        i.putExtra("s4z2FromMin", s4_z1_z2_min);
        i.putExtra("s4z2ToHour", s4_z2_z3_hour);
        i.putExtra("s4z2ToMin", s4_z2_z3_min);
        i.putExtra("s4z3FromHour", s4_z2_z3_hour);
        i.putExtra("s4z3FromMin", s4_z2_z3_min);
        i.putExtra("s4z3ToHour", s4_z3_out_hour);
        i.putExtra("s4z3ToMin", s4_z3_out_min);

        setResult(RESULT_OK, i);
        finish();
    }

    public void cancel(View v) {
        Intent i = new Intent();
        setResult(RESULT_CANCELED, i);
        finish();
    }

    TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            switch (currentTimeEdit) {
                case S1_OUT_TO_Z1_TIME:
                    s1_out_z1_hour = hourOfDay;
                    s1_out_z1_min = minute;
                    s1OutTo.setText(formatTime(s1_out_z1_hour, s1_out_z1_min));
                    s1z1FromButton.setText(formatTime(s1_out_z1_hour, s1_out_z1_min));
                    break;
                case S1_Z1_TO_Z2_TIME:
                    s1_z1_z2_hour = hourOfDay;
                    s1_z1_z2_min = minute;
                    s1z1ToButton.setText(formatTime(s1_z1_z2_hour, s1_z1_z2_min));
                    s1z2FromButton.setText(formatTime(s1_z1_z2_hour, s1_z1_z2_min));
                    set_s1_out_from_text();
                    break;
                case S1_Z2_TO_Z3_TIME:
                    s1_z2_z3_hour = hourOfDay;
                    s1_z2_z3_min = minute;
                    s1z2ToButton.setText(formatTime(s1_z2_z3_hour, s1_z2_z3_min));
                    s1z3FromButton.setText(formatTime(s1_z2_z3_hour, s1_z2_z3_min));
                    set_s1_out_from_text();
                    break;
                case S1_Z3_TO_OUT_TIME:
                    s1_z3_out_hour = hourOfDay;
                    s1_z3_out_min = minute;
                    s1z3ToButton.setText(formatTime(s1_z3_out_hour, s1_z3_out_min));
                    set_s1_out_from_text();
                    break;
                case S2_OUT_TO_Z1_TIME:
                    s2_out_z1_hour = hourOfDay;
                    s2_out_z1_min = minute;
                    s2OutTo.setText(formatTime(s2_out_z1_hour, s2_out_z1_min));
                    s2z1FromButton.setText(formatTime(s2_out_z1_hour, s2_out_z1_min));
                    break;
                case S2_Z1_TO_Z2_TIME:
                    s2_z1_z2_hour = hourOfDay;
                    s2_z1_z2_min = minute;
                    s2z1ToButton.setText(formatTime(s2_z1_z2_hour, s2_z1_z2_min));
                    s2z2FromButton.setText(formatTime(s2_z1_z2_hour, s2_z1_z2_min));
                    set_s2_out_from_text();
                    break;
                case S2_Z2_TO_Z3_TIME:
                    s2_z2_z3_hour = hourOfDay;
                    s2_z2_z3_min = minute;
                    s2z2ToButton.setText(formatTime(s2_z2_z3_hour, s2_z2_z3_min));
                    s2z3FromButton.setText(formatTime(s2_z2_z3_hour, s2_z2_z3_min));
                    set_s2_out_from_text();
                    break;
                case S2_Z3_TO_OUT_TIME:
                    s2_z3_out_hour = hourOfDay;
                    s2_z3_out_min = minute;
                    s2z3ToButton.setText(formatTime(s2_z3_out_hour, s2_z3_out_min));
                    set_s2_out_from_text();
                    break;
                case S3_OUT_TO_Z1_TIME:
                    s3_out_z1_hour = hourOfDay;
                    s3_out_z1_min = minute;
                    s3OutTo.setText(formatTime(s3_out_z1_hour, s3_out_z1_min));
                    s3z1FromButton.setText(formatTime(s3_out_z1_hour, s3_out_z1_min));
                    break;
                case S3_Z1_TO_Z2_TIME:
                    s3_z1_z2_hour = hourOfDay;
                    s3_z1_z2_min = minute;
                    s3z1ToButton.setText(formatTime(s3_z1_z2_hour, s3_z1_z2_min));
                    s3z2FromButton.setText(formatTime(s3_z1_z2_hour, s3_z1_z2_min));
                    set_s3_out_from_text();
                    break;
                case S3_Z2_TO_Z3_TIME:
                    s3_z2_z3_hour = hourOfDay;
                    s3_z2_z3_min = minute;
                    s3z2ToButton.setText(formatTime(s3_z2_z3_hour, s3_z2_z3_min));
                    s3z3FromButton.setText(formatTime(s3_z2_z3_hour, s3_z2_z3_min));
                    set_s3_out_from_text();
                    break;
                case S3_Z3_TO_OUT_TIME:
                    s3_z3_out_hour = hourOfDay;
                    s3_z3_out_min = minute;
                    s3z3ToButton.setText(formatTime(s3_z3_out_hour, s3_z3_out_min));
                    set_s3_out_from_text();
                    break;
                case S4_OUT_TO_Z1_TIME:
                    s4_out_z1_hour = hourOfDay;
                    s4_out_z1_min = minute;
                    s4OutTo.setText(formatTime(s4_out_z1_hour, s4_out_z1_min));
                    s4z1FromButton.setText(formatTime(s4_out_z1_hour, s4_out_z1_min));
                    break;
                case S4_Z1_TO_Z2_TIME:
                    s4_z1_z2_hour = hourOfDay;
                    s4_z1_z2_min = minute;
                    s4z1ToButton.setText(formatTime(s4_z1_z2_hour, s4_z1_z2_min));
                    s4z2FromButton.setText(formatTime(s4_z1_z2_hour, s4_z1_z2_min));
                    set_s4_out_from_text();
                    break;
                case S4_Z2_TO_Z3_TIME:
                    s4_z2_z3_hour = hourOfDay;
                    s4_z2_z3_min = minute;
                    s4z2ToButton.setText(formatTime(s4_z2_z3_hour, s4_z2_z3_min));
                    s4z3FromButton.setText(formatTime(s4_z2_z3_hour, s4_z2_z3_min));
                    set_s4_out_from_text();
                    break;
                case S4_Z3_TO_OUT_TIME:
                    s4_z3_out_hour = hourOfDay;
                    s4_z3_out_min = minute;
                    s4z3ToButton.setText(formatTime(s4_z3_out_hour, s4_z3_out_min));
                    set_s4_out_from_text();
                    break;
            }
        }
    };

    public void openTimeSetDialog() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, AlertDialog.THEME_HOLO_DARK, onTimeSetListener, 0, 0, true);
        timePickerDialog.setTitle("Set Time");
        timePickerDialog.show();
    }

    public String formatTime(int hour, int minute) {
        return (new StringBuilder().append(String.format("%02d", hour)).append(":")
                .append(String.format("%02d", minute))).toString();
    }

    public void set_s1_out_from_text() {
        switch(numZones) {
            case 2:
                s1OutFrom.setText(formatTime(s1_z1_z2_hour, s1_z1_z2_min));
                break;
            case 3:
                s1OutFrom.setText(formatTime(s1_z2_z3_hour, s1_z2_z3_min));
                break;
            case 4:
                s1OutFrom.setText(formatTime(s1_z3_out_hour, s1_z3_out_min));
                break;
        }
    }

    public void set_s2_out_from_text() {
        switch(numZones) {
            case 2:
                s2OutFrom.setText(formatTime(s2_z1_z2_hour, s2_z1_z2_min));
                break;
            case 3:
                s2OutFrom.setText(formatTime(s2_z2_z3_hour, s2_z2_z3_min));
                break;
            case 4:
                s2OutFrom.setText(formatTime(s2_z3_out_hour, s2_z3_out_min));
                break;
        }
    }

    public void set_s3_out_from_text() {
        switch(numZones) {
            case 2:
                s3OutFrom.setText(formatTime(s3_z1_z2_hour, s3_z1_z2_min));
                break;
            case 3:
                s3OutFrom.setText(formatTime(s3_z2_z3_hour, s3_z2_z3_min));
                break;
            case 4:
                s3OutFrom.setText(formatTime(s3_z3_out_hour, s3_z3_out_min));
                break;
        }
    }

    public void set_s4_out_from_text() {
        switch(numZones) {
            case 2:
                s4OutFrom.setText(formatTime(s4_z1_z2_hour, s4_z1_z2_min));
                break;
            case 3:
                s4OutFrom.setText(formatTime(s4_z2_z3_hour, s4_z2_z3_min));
                break;
            case 4:
                s4OutFrom.setText(formatTime(s4_z3_out_hour, s4_z3_out_min));
                break;
        }
    }

    public void s1z1FromClicked(View view) { currentTimeEdit = S1_OUT_TO_Z1_TIME; openTimeSetDialog(); }
    public void s1z1ToClicked(View view) { currentTimeEdit = S1_Z1_TO_Z2_TIME; openTimeSetDialog(); }

    public void s1z2FromClicked(View view) { currentTimeEdit = S1_Z1_TO_Z2_TIME; openTimeSetDialog(); }
    public void s1z2ToClicked(View view) { currentTimeEdit = S1_Z2_TO_Z3_TIME; openTimeSetDialog(); }

    public void s1z3FromClicked(View view) { currentTimeEdit = S1_Z2_TO_Z3_TIME; openTimeSetDialog(); }
    public void s1z3ToClicked(View view) { currentTimeEdit = S1_Z3_TO_OUT_TIME; openTimeSetDialog(); }


    public void s2z1FromClicked(View view) { currentTimeEdit = S2_OUT_TO_Z1_TIME; openTimeSetDialog(); }
    public void s2z1ToClicked(View view) { currentTimeEdit = S2_Z1_TO_Z2_TIME; openTimeSetDialog(); }

    public void s2z2FromClicked(View view) { currentTimeEdit = S2_Z1_TO_Z2_TIME; openTimeSetDialog(); }
    public void s2z2ToClicked(View view) { currentTimeEdit = S2_Z2_TO_Z3_TIME; openTimeSetDialog(); }

    public void s2z3FromClicked(View view) { currentTimeEdit = S2_Z2_TO_Z3_TIME; openTimeSetDialog(); }
    public void s2z3ToClicked(View view) { currentTimeEdit = S2_Z3_TO_OUT_TIME; openTimeSetDialog(); }


    public void s3z1FromClicked(View view) { currentTimeEdit = S3_OUT_TO_Z1_TIME; openTimeSetDialog(); }
    public void s3z1ToClicked(View view) { currentTimeEdit = S3_Z1_TO_Z2_TIME; openTimeSetDialog(); }

    public void s3z2FromClicked(View view) { currentTimeEdit = S3_Z1_TO_Z2_TIME; openTimeSetDialog(); }
    public void s3z2ToClicked(View view) { currentTimeEdit = S3_Z2_TO_Z3_TIME; openTimeSetDialog(); }

    public void s3z3FromClicked(View view) { currentTimeEdit = S3_Z2_TO_Z3_TIME; openTimeSetDialog(); }
    public void s3z3ToClicked(View view) { currentTimeEdit = S3_Z3_TO_OUT_TIME; openTimeSetDialog(); }


    public void s4z1FromClicked(View view) { currentTimeEdit = S4_OUT_TO_Z1_TIME; openTimeSetDialog(); }
    public void s4z1ToClicked(View view) { currentTimeEdit = S4_Z1_TO_Z2_TIME; openTimeSetDialog(); }

    public void s4z2FromClicked(View view) { currentTimeEdit = S4_Z1_TO_Z2_TIME; openTimeSetDialog(); }
    public void s4z2ToClicked(View view) { currentTimeEdit = S4_Z2_TO_Z3_TIME; openTimeSetDialog(); }

    public void s4z3FromClicked(View view) { currentTimeEdit = S4_Z2_TO_Z3_TIME; openTimeSetDialog(); }
    public void s4z3ToClicked(View view) { currentTimeEdit = S4_Z3_TO_OUT_TIME; openTimeSetDialog(); }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch(parent.getId()) {
            case R.id.zoneSpinner:
                numZones = parent.getSelectedItemPosition() + 2;
                setS1ZonesVisible(numZones);
                setS2ZonesVisible(numZones);
                setS3ZonesVisible(numZones);
                setS4ZonesVisible(numZones);
                setZoneNamesVisible(numZones);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void setSeasonsVisible(int n) {
        switch(n) {
            case 1:
                findViewById(R.id.s2ZoneLayout).setVisibility(View.GONE);
                findViewById(R.id.s3ZoneLayout).setVisibility(View.GONE);
                findViewById(R.id.s4ZoneLayout).setVisibility(View.GONE);
                break;
            case 2:
                findViewById(R.id.s2ZoneLayout).setVisibility(View.VISIBLE);
                findViewById(R.id.s3ZoneLayout).setVisibility(View.GONE);
                findViewById(R.id.s4ZoneLayout).setVisibility(View.GONE);
                break;
            case 3:
                findViewById(R.id.s2ZoneLayout).setVisibility(View.VISIBLE);
                findViewById(R.id.s3ZoneLayout).setVisibility(View.VISIBLE);
                findViewById(R.id.s4ZoneLayout).setVisibility(View.GONE);
                break;
            case 4:
                findViewById(R.id.s2ZoneLayout).setVisibility(View.VISIBLE);
                findViewById(R.id.s3ZoneLayout).setVisibility(View.VISIBLE);
                findViewById(R.id.s4ZoneLayout).setVisibility(View.VISIBLE);
                break;
        }
    }

    public void setS1ZonesVisible(int n) {
        switch(n) {
            case 2:
                findViewById(R.id.s1z2Layout).setVisibility(View.GONE);
                findViewById(R.id.s1z3Layout).setVisibility(View.GONE);
                break;
            case 3:
                findViewById(R.id.s1z2Layout).setVisibility(View.VISIBLE);
                findViewById(R.id.s1z3Layout).setVisibility(View.GONE);
                break;
            case 4:
                findViewById(R.id.s1z2Layout).setVisibility(View.VISIBLE);
                findViewById(R.id.s1z3Layout).setVisibility(View.VISIBLE);
                break;
        }
    }

    public void setS2ZonesVisible(int n) {
        switch(n) {
            case 2:
                findViewById(R.id.s2z2Layout).setVisibility(View.GONE);
                findViewById(R.id.s2z3Layout).setVisibility(View.GONE);
                break;
            case 3:
                findViewById(R.id.s2z2Layout).setVisibility(View.VISIBLE);
                findViewById(R.id.s2z3Layout).setVisibility(View.GONE);
                break;
            case 4:
                findViewById(R.id.s2z2Layout).setVisibility(View.VISIBLE);
                findViewById(R.id.s2z3Layout).setVisibility(View.VISIBLE);
                break;
        }
    }

    public void setS3ZonesVisible(int n) {
        switch(n) {
            case 2:
                findViewById(R.id.s3z2Layout).setVisibility(View.GONE);
                findViewById(R.id.s3z3Layout).setVisibility(View.GONE);
                break;
            case 3:
                findViewById(R.id.s3z2Layout).setVisibility(View.VISIBLE);
                findViewById(R.id.s3z3Layout).setVisibility(View.GONE);
                break;
            case 4:
                findViewById(R.id.s3z2Layout).setVisibility(View.VISIBLE);
                findViewById(R.id.s3z3Layout).setVisibility(View.VISIBLE);
                break;
        }
    }

    public void setS4ZonesVisible(int n) {
        switch(n) {
            case 2:
                findViewById(R.id.s4z2Layout).setVisibility(View.GONE);
                findViewById(R.id.s4z3Layout).setVisibility(View.GONE);
                break;
            case 3:
                findViewById(R.id.s4z2Layout).setVisibility(View.VISIBLE);
                findViewById(R.id.s4z3Layout).setVisibility(View.GONE);
                break;
            case 4:
                findViewById(R.id.s4z2Layout).setVisibility(View.VISIBLE);
                findViewById(R.id.s4z3Layout).setVisibility(View.VISIBLE);
                break;
        }
    }

    public void setZoneNamesVisible(int n) {
        switch (n) {
            case 2:
                z2NameEdit.setVisibility(View.GONE);
                z3NameEdit.setVisibility(View.GONE);
                findViewById(R.id.z2NameText).setVisibility(View.GONE);
                findViewById(R.id.z3NameText).setVisibility(View.GONE);
                break;
            case 3:
                z2NameEdit.setVisibility(View.VISIBLE);
                z3NameEdit.setVisibility(View.GONE);
                findViewById(R.id.z2NameText).setVisibility(View.VISIBLE);
                findViewById(R.id.z3NameText).setVisibility(View.GONE);
                break;
            case 4:
                z2NameEdit.setVisibility(View.VISIBLE);
                z3NameEdit.setVisibility(View.VISIBLE);
                findViewById(R.id.z2NameText).setVisibility(View.VISIBLE);
                findViewById(R.id.z3NameText).setVisibility(View.VISIBLE);
                break;
        }
    }

}