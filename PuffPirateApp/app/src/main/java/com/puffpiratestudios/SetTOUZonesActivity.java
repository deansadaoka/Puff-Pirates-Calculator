package com.puffpiratestudios;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

public class SetTOUZonesActivity extends AppCompatActivity {

    private static final int S1_Z1_FROM_TIME = 1;
    private static final int S1_Z1_TO_TIME = 2;
    private static final int S1_Z2_FROM_TIME = 3;
    private static final int S1_Z2_TO_TIME = 4;
    private static final int S1_Z3_FROM_TIME = 5;
    private static final int S1_Z3_TO_TIME = 6;

    private static final int S2_Z1_FROM_TIME = 7;
    private static final int S2_Z1_TO_TIME = 8;
    private static final int S2_Z2_FROM_TIME = 9;
    private static final int S2_Z2_TO_TIME = 10;
    private static final int S2_Z3_FROM_TIME = 11;
    private static final int S2_Z3_TO_TIME = 12;

    private static final int S3_Z1_FROM_TIME = 13;
    private static final int S3_Z1_TO_TIME = 14;
    private static final int S3_Z2_FROM_TIME = 15;
    private static final int S3_Z2_TO_TIME = 16;
    private static final int S3_Z3_FROM_TIME = 17;
    private static final int S3_Z3_TO_TIME = 18;

    private static final int S4_Z1_FROM_TIME = 19;
    private static final int S4_Z1_TO_TIME = 20;
    private static final int S4_Z2_FROM_TIME = 21;
    private static final int S4_Z2_TO_TIME = 22;
    private static final int S4_Z3_FROM_TIME = 23;
    private static final int S4_Z3_TO_TIME = 24;


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


    EditText s1ZoneName;
    EditText s2ZoneName;
    EditText s3ZoneName;
    EditText s4ZoneName;


    EditText s1z1Rate;
    EditText s1z2Rate;
    EditText s1z3Rate;

    EditText s2z1Rate;
    EditText s2z2Rate;
    EditText s2z3Rate;

    EditText s3z1Rate;
    EditText s3z2Rate;
    EditText s3z3Rate;

    EditText s4z1Rate;
    EditText s4z2Rate;
    EditText s4z3Rate;


    int s1z1FromHour;
    int s1z1FromMin;
    int s1z1ToHour;
    int s1z1ToMin;
    int s1z2FromHour;
    int s1z2FromMin;
    int s1z2ToHour;
    int s1z2ToMin;
    int s1z3FromHour;
    int s1z3FromMin;
    int s1z3ToHour;
    int s1z3ToMin;

    int s2z1FromHour;
    int s2z1FromMin;
    int s2z1ToHour;
    int s2z1ToMin;
    int s2z2FromHour;
    int s2z2FromMin;
    int s2z2ToHour;
    int s2z2ToMin;
    int s2z3FromHour;
    int s2z3FromMin;
    int s2z3ToHour;
    int s2z3ToMin;

    int s3z1FromHour;
    int s3z1FromMin;
    int s3z1ToHour;
    int s3z1ToMin;
    int s3z2FromHour;
    int s3z2FromMin;
    int s3z2ToHour;
    int s3z2ToMin;
    int s3z3FromHour;
    int s3z3FromMin;
    int s3z3ToHour;
    int s3z3ToMin;

    int s4z1FromHour;
    int s4z1FromMin;
    int s4z1ToHour;
    int s4z1ToMin;
    int s4z2FromHour;
    int s4z2FromMin;
    int s4z2ToHour;
    int s4z2ToMin;
    int s4z3FromHour;
    int s4z3FromMin;
    int s4z3ToHour;
    int s4z3ToMin;


    TextView s1OutFrom;
    TextView s1OutTo;
    TextView s2OutFrom;
    TextView s2OutTo;
    TextView s3OutFrom;
    TextView s3OutTo;
    TextView s4OutFrom;
    TextView s4OutTo;


    EditText s1OutRate;
    EditText s2OutRate;
    EditText s3OutRate;
    EditText s4OutRate;


    int s1NumZones;
    int s2NumZones;
    int s3NumZones;
    int s4NumZones;


    int currentTimeEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_touzones);

        TextView title = (TextView) findViewById(R.id.ZoneTitle);
        title.setBackgroundColor(Color.parseColor("#008b8b"));
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


        s1ZoneName = findViewById(R.id.s1ZoneNameEdit);
        s2ZoneName = findViewById(R.id.s2ZoneNameEdit);
        s3ZoneName = findViewById(R.id.s3ZoneNameEdit);
        s4ZoneName = findViewById(R.id.s4ZoneNameEdit);


        s1z1Rate = findViewById(R.id.s1z1RateEdit);
        s2z1Rate = findViewById(R.id.s2z1RateEdit);
        s3z1Rate = findViewById(R.id.s3z1RateEdit);
        s4z1Rate = findViewById(R.id.s4z1RateEdit);

        s1z2Rate = findViewById(R.id.s1z2RateEdit);
        s2z2Rate = findViewById(R.id.s2z2RateEdit);
        s3z2Rate = findViewById(R.id.s3z2RateEdit);
        s4z2Rate = findViewById(R.id.s4z2RateEdit);

        s1z3Rate = findViewById(R.id.s1z3RateEdit);
        s2z3Rate = findViewById(R.id.s2z3RateEdit);
        s3z3Rate = findViewById(R.id.s3z3RateEdit);
        s4z3Rate = findViewById(R.id.s4z3RateEdit);

        s1OutRate = findViewById(R.id.s1OutRateEdit);
        s2OutRate = findViewById(R.id.s2OutRateEdit);
        s3OutRate = findViewById(R.id.s3OutRateEdit);
        s4OutRate = findViewById(R.id.s4OutRateEdit);


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




    }

    public void submit(View v) {
        Intent i = new Intent();

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
                case S1_Z1_FROM_TIME:
                    s1z1FromHour = hourOfDay;
                    s1z1FromMin = minute;
                    s1z1FromButton.setText(formatTime(s1z1FromHour, s1z1FromMin));
                    break;
                case S1_Z1_TO_TIME:
                    s1z1ToHour = hourOfDay;
                    s1z1ToMin = minute;
                    s1z1ToButton.setText(formatTime(s1z1ToHour, s1z1ToMin));
                    break;
                case S1_Z2_FROM_TIME:
                    s1z2FromHour = hourOfDay;
                    s1z2FromMin = minute;
                    s1z2FromButton.setText(formatTime(s1z2FromHour, s1z2FromMin));
                    break;
                case S1_Z2_TO_TIME:
                    s1z2ToHour = hourOfDay;
                    s1z2ToMin = minute;
                    s1z2ToButton.setText(formatTime(s1z2ToHour, s1z2ToMin));
                    break;
                case S1_Z3_FROM_TIME:
                    s1z3FromHour = hourOfDay;
                    s1z3FromMin = minute;
                    s1z3FromButton.setText(formatTime(s1z3FromHour, s1z3FromMin));
                    break;
                case S1_Z3_TO_TIME:
                    s1z3ToHour = hourOfDay;
                    s1z3ToMin = minute;
                    s1z3ToButton.setText(formatTime(s1z3ToHour, s1z3ToMin));
                    break;
                case S2_Z1_FROM_TIME:
                    s2z1FromHour = hourOfDay;
                    s2z1FromMin = minute;
                    s2z1FromButton.setText(formatTime(s2z1FromHour, s2z1FromMin));
                    break;
                case S2_Z1_TO_TIME:
                    s2z1ToHour = hourOfDay;
                    s2z1ToMin = minute;
                    s2z1ToButton.setText(formatTime(s2z1ToHour, s2z1ToMin));
                    break;
                case S2_Z2_FROM_TIME:
                    s2z2FromHour = hourOfDay;
                    s2z2FromMin = minute;
                    s2z2FromButton.setText(formatTime(s2z2FromHour, s2z2FromMin));
                    break;
                case S2_Z2_TO_TIME:
                    s2z2ToHour = hourOfDay;
                    s2z2ToMin = minute;
                    s2z2ToButton.setText(formatTime(s2z2ToHour, s2z2ToMin));
                    break;
                case S2_Z3_FROM_TIME:
                    s2z3FromHour = hourOfDay;
                    s2z3FromMin = minute;
                    s2z3FromButton.setText(formatTime(s2z3FromHour, s2z3FromMin));
                    break;
                case S2_Z3_TO_TIME:
                    s2z3ToHour = hourOfDay;
                    s2z3ToMin = minute;
                    s2z3ToButton.setText(formatTime(s2z3ToHour, s2z3ToMin));
                    break;
                case S3_Z1_FROM_TIME:
                    s3z1FromHour = hourOfDay;
                    s3z1FromMin = minute;
                    s3z1FromButton.setText(formatTime(s3z1FromHour, s3z1FromMin));
                    break;
                case S3_Z1_TO_TIME:
                    s3z1ToHour = hourOfDay;
                    s3z1ToMin = minute;
                    s3z1ToButton.setText(formatTime(s3z1ToHour, s3z1ToMin));
                    break;
                case S3_Z2_FROM_TIME:
                    s3z2FromHour = hourOfDay;
                    s3z2FromMin = minute;
                    s3z2FromButton.setText(formatTime(s3z2FromHour, s3z2FromMin));
                    break;
                case S3_Z2_TO_TIME:
                    s3z2ToHour = hourOfDay;
                    s3z2ToMin = minute;
                    s3z2ToButton.setText(formatTime(s3z2ToHour, s3z2ToMin));
                    break;
                case S3_Z3_FROM_TIME:
                    s3z3FromHour = hourOfDay;
                    s3z3FromMin = minute;
                    s3z3FromButton.setText(formatTime(s3z3FromHour, s3z3FromMin));
                    break;
                case S3_Z3_TO_TIME:
                    s3z3ToHour = hourOfDay;
                    s3z3ToMin = minute;
                    s3z3ToButton.setText(formatTime(s3z3ToHour, s3z3ToMin));
                    break;
                case S4_Z1_FROM_TIME:
                    s4z1FromHour = hourOfDay;
                    s4z1FromMin = minute;
                    s4z1FromButton.setText(formatTime(s4z1FromHour, s4z1FromMin));
                    break;
                case S4_Z1_TO_TIME:
                    s4z1ToHour = hourOfDay;
                    s4z1ToMin = minute;
                    s4z1ToButton.setText(formatTime(s4z1ToHour, s4z1ToMin));
                    break;
                case S4_Z2_FROM_TIME:
                    s4z2FromHour = hourOfDay;
                    s4z2FromMin = minute;
                    s4z2FromButton.setText(formatTime(s4z2FromHour, s4z2FromMin));
                    break;
                case S4_Z2_TO_TIME:
                    s4z2ToHour = hourOfDay;
                    s4z2ToMin = minute;
                    s4z2ToButton.setText(formatTime(s4z2ToHour, s4z2ToMin));
                    break;
                case S4_Z3_FROM_TIME:
                    s4z3FromHour = hourOfDay;
                    s4z3FromMin = minute;
                    s4z3FromButton.setText(formatTime(s4z3FromHour, s4z3FromMin));
                    break;
                case S4_Z3_TO_TIME:
                    s4z3ToHour = hourOfDay;
                    s4z3ToMin = minute;
                    s4z3ToButton.setText(formatTime(s4z3ToHour, s4z3ToMin));
                    break;
            }
        }
    };

    public void openTimeSetDialog() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, AlertDialog.THEME_HOLO_DARK, onTimeSetListener, 0, 0, true);
        timePickerDialog.setTitle("Set Title");
        timePickerDialog.show();
    }

    public String formatTime(int hour, int minute) {
        return (new StringBuilder().append(String.format("%02d", hour)).append(":")
                .append(String.format("%02d", minute))).toString();
    }

    public void s1z1FromClicked(View view) { currentTimeEdit = S1_Z1_FROM_TIME; openTimeSetDialog(); }
    public void s1z1ToClicked(View view) { currentTimeEdit = S1_Z1_TO_TIME; openTimeSetDialog(); }

    public void s1z2FromClicked(View view) { currentTimeEdit = S1_Z2_FROM_TIME; openTimeSetDialog(); }
    public void s1z2ToClicked(View view) { currentTimeEdit = S1_Z2_TO_TIME; openTimeSetDialog(); }

    public void s1z3FromClicked(View view) { currentTimeEdit = S1_Z3_FROM_TIME; openTimeSetDialog(); }
    public void s1z3ToClicked(View view) { currentTimeEdit = S1_Z3_TO_TIME; openTimeSetDialog(); }


    public void s2z1FromClicked(View view) { currentTimeEdit = S2_Z1_FROM_TIME; openTimeSetDialog(); }
    public void s2z1ToClicked(View view) { currentTimeEdit = S2_Z1_TO_TIME; openTimeSetDialog(); }

    public void s2z2FromClicked(View view) { currentTimeEdit = S2_Z2_FROM_TIME; openTimeSetDialog(); }
    public void s2z2ToClicked(View view) { currentTimeEdit = S2_Z2_TO_TIME; openTimeSetDialog(); }

    public void s2z3FromClicked(View view) { currentTimeEdit = S2_Z3_FROM_TIME; openTimeSetDialog(); }
    public void s2z3ToClicked(View view) { currentTimeEdit = S2_Z3_TO_TIME; openTimeSetDialog(); }


    public void s3z1FromClicked(View view) { currentTimeEdit = S3_Z1_FROM_TIME; openTimeSetDialog(); }
    public void s3z1ToClicked(View view) { currentTimeEdit = S3_Z1_TO_TIME; openTimeSetDialog(); }

    public void s3z2FromClicked(View view) { currentTimeEdit = S3_Z2_FROM_TIME; openTimeSetDialog(); }
    public void s3z2ToClicked(View view) { currentTimeEdit = S3_Z2_TO_TIME; openTimeSetDialog(); }

    public void s3z3FromClicked(View view) { currentTimeEdit = S3_Z3_FROM_TIME; openTimeSetDialog(); }
    public void s3z3ToClicked(View view) { currentTimeEdit = S3_Z3_TO_TIME; openTimeSetDialog(); }


    public void s4z1FromClicked(View view) { currentTimeEdit = S4_Z1_FROM_TIME; openTimeSetDialog(); }
    public void s4z1ToClicked(View view) { currentTimeEdit = S4_Z1_TO_TIME; openTimeSetDialog(); }

    public void s4z2FromClicked(View view) { currentTimeEdit = S4_Z2_FROM_TIME; openTimeSetDialog(); }
    public void s4z2ToClicked(View view) { currentTimeEdit = S4_Z2_TO_TIME; openTimeSetDialog(); }

    public void s4z3FromClicked(View view) { currentTimeEdit = S4_Z3_FROM_TIME; openTimeSetDialog(); }
    public void s4z3ToClicked(View view) { currentTimeEdit = S4_Z3_TO_TIME; openTimeSetDialog(); }



}