package com.puffpiratestudios;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

public class SetTouDetailsActivity extends AppCompatActivity {

    CheckBox saturdayCheckBox;
    CheckBox sundayCheckBox;
    CheckBox holidayCheckBox;

    RadioButton buttonUSA;
    RadioButton buttonCA;

    EditText z1Pct;
    EditText z2Pct;
    EditText z3Pct;
    EditText zOutPct;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_tou_details);

        TextView title = (TextView) findViewById(R.id.TouDetailsTitle);
        title.setBackgroundColor(Color.parseColor("#d71414"));
        title.setTextColor(getResources().getColor(R.color.white));



        buttonUSA = findViewById(R.id.buttonUSA);
        buttonCA = findViewById(R.id.buttonCA);

        saturdayCheckBox = findViewById(R.id.saturdayCheckBox);
        sundayCheckBox = findViewById(R.id.sundayCheckBox);
        holidayCheckBox = findViewById(R.id.holidaysCheckBox);

        z1Pct = findViewById(R.id.zone1PCT);
        z2Pct = findViewById(R.id.zone2PCT);
        z3Pct = findViewById(R.id.zone3PCT);
        zOutPct = findViewById(R.id.zoneOutPCT);

        z1Pct.setFilters(new InputFilter[] {new DecimalDigitFilter(4)});
        z2Pct.setFilters(new InputFilter[] {new DecimalDigitFilter(4)});
        z3Pct.setFilters(new InputFilter[] {new DecimalDigitFilter(4)});
        zOutPct.setFilters(new InputFilter[] {new DecimalDigitFilter(4)});

        Intent i = getIntent();

        z1Pct.setText(String.format("%.2f", i.getDoubleExtra("z1pct", 0)));
        z2Pct.setText(String.format("%.2f", i.getDoubleExtra("z2pct", 0)));
        z3Pct.setText(String.format("%.2f", i.getDoubleExtra("z3pct", 0)));
        zOutPct.setText(String.format("%.2f", i.getDoubleExtra("zOutpct", 0)));

        saturdayCheckBox.setChecked(i.getBooleanExtra("saturday", false));
        sundayCheckBox.setChecked(i.getBooleanExtra("sunday", false));
        holidayCheckBox.setChecked(i.getBooleanExtra("holiday", false));

        setCountryRadioButtons(i.getIntExtra("country", 0));

        setZonesVisible(i.getIntExtra("numZones", 2));


    }

    public void submit(View v) {
        Intent i = new Intent();

        i.putExtra("saturday", saturdayCheckBox.isChecked());
        i.putExtra("sunday", sundayCheckBox.isChecked());
        i.putExtra("holiday", holidayCheckBox.isChecked());

        i.putExtra("country", buttonUSA.isChecked() ? 0 : 1);

        i.putExtra("z1pct", Double.parseDouble(z1Pct.getText().toString().replace("%", "")));
        i.putExtra("z2pct", Double.parseDouble(z2Pct.getText().toString().replace("%", "")));
        i.putExtra("z3pct", Double.parseDouble(z3Pct.getText().toString().replace("%", "")));
        i.putExtra("zOutpct", Double.parseDouble(zOutPct.getText().toString().replace("%", "")));

        setResult(RESULT_OK, i);
        finish();
    }

    public void cancel(View v) {
        Intent i = new Intent();
        setResult(RESULT_CANCELED, i);
        finish();
    }

    private void setZonesVisible(int numZones) {
        switch(numZones) {
            case 2:
                findViewById(R.id.zone2pctLayout).setVisibility(View.GONE);
                findViewById(R.id.zone3pctLayout).setVisibility(View.GONE);
                break;
            case 3:
                findViewById(R.id.zone2pctLayout).setVisibility(View.VISIBLE);
                findViewById(R.id.zone3pctLayout).setVisibility(View.GONE);
                break;
            case 4:
                findViewById(R.id.zone2pctLayout).setVisibility(View.VISIBLE);
                findViewById(R.id.zone3pctLayout).setVisibility(View.VISIBLE);
                break;
        }
    }

    private void setCountryRadioButtons(int country) {
        if (country == 0) {
            buttonUSA.setChecked(true);
            buttonCA.setChecked(false);
        }
        else {
            buttonUSA.setChecked(false);
            buttonCA.setChecked(true);
        }
    }

}

