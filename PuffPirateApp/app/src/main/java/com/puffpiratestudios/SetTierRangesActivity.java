package com.puffpiratestudios;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class SetTierRangesActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    int numSeasons;

    int s1Tier;
    int s2Tier;
    int s3Tier;
    int s4Tier;

    double s1t1Tier;
    double s2t1Tier;
    double s3t1Tier;
    double s4t1Tier;
    double s1t2Tier;
    double s2t2Tier;
    double s3t2Tier;
    double s4t2Tier;
    double s1t3Tier;
    double s2t3Tier;
    double s3t3Tier;
    double s4t3Tier;
    double s1t4Tier;
    double s2t4Tier;
    double s3t4Tier;
    double s4t4Tier;

    EditText s1t1TierText;
    EditText s2t1TierText;
    EditText s3t1TierText;
    EditText s4t1TierText;
    EditText s1t2TierText;
    EditText s2t2TierText;
    EditText s3t2TierText;
    EditText s4t2TierText;
    EditText s1t3TierText;
    EditText s2t3TierText;
    EditText s3t3TierText;
    EditText s4t3TierText;
    EditText s1t4TierText;
    EditText s2t4TierText;
    EditText s3t4TierText;
    EditText s4t4TierText;

    Spinner s1TierSpinner;
    Spinner s2TierSpinner;
    Spinner s3TierSpinner;
    Spinner s4TierSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_tier_ranges);

        TextView title = (TextView) findViewById(R.id.TierRangeTitle);
        title.setBackgroundColor(Color.parseColor("#d71414"));
        title.setTextColor(getResources().getColor(R.color.white));

        TextView s1Title = (TextView) findViewById(R.id.s1TierRangeTitle);
        s1Title.setBackgroundColor(Color.parseColor("#30bf04"));
        s1Title.setTextColor(getResources().getColor(R.color.white));

        TextView s2Title = (TextView) findViewById(R.id.s2TierRangeTitle);
        s2Title.setBackgroundColor(Color.parseColor("#30bf04"));
        s2Title.setTextColor(getResources().getColor(R.color.white));

        TextView s3Title = (TextView) findViewById(R.id.s3TierRangeTitle);
        s3Title.setBackgroundColor(Color.parseColor("#30bf04"));
        s3Title.setTextColor(getResources().getColor(R.color.white));

        TextView s4Title = (TextView) findViewById(R.id.s4TierRangeTitle);
        s4Title.setBackgroundColor(Color.parseColor("#30bf04"));
        s4Title.setTextColor(getResources().getColor(R.color.white));

        s1t1TierText = findViewById(R.id.s1t1TierRange);
        s2t1TierText = findViewById(R.id.s2t1TierRange);
        s3t1TierText = findViewById(R.id.s3t1TierRange);
        s4t1TierText = findViewById(R.id.s4t1TierRange);
        s1t2TierText = findViewById(R.id.s1t2TierRange);
        s2t2TierText = findViewById(R.id.s2t2TierRange);
        s3t2TierText = findViewById(R.id.s3t2TierRange);
        s4t2TierText = findViewById(R.id.s4t2TierRange);
        s1t3TierText = findViewById(R.id.s1t3TierRange);
        s2t3TierText = findViewById(R.id.s2t3TierRange);
        s3t3TierText = findViewById(R.id.s3t3TierRange);
        s4t3TierText = findViewById(R.id.s4t3TierRange);
        s1t4TierText = findViewById(R.id.s1t4TierRange);
        s2t4TierText = findViewById(R.id.s2t4TierRange);
        s3t4TierText = findViewById(R.id.s3t4TierRange);
        s4t4TierText = findViewById(R.id.s4t4TierRange);

        s1t1TierText.setFilters(new InputFilter[] {new DecimalDigitFilter(4)});
        s1t2TierText.setFilters(new InputFilter[] {new DecimalDigitFilter(4)});
        s1t3TierText.setFilters(new InputFilter[] {new DecimalDigitFilter(4)});
        s1t4TierText.setFilters(new InputFilter[] {new DecimalDigitFilter(4)});
        s2t1TierText.setFilters(new InputFilter[] {new DecimalDigitFilter(4)});
        s2t2TierText.setFilters(new InputFilter[] {new DecimalDigitFilter(4)});
        s2t3TierText.setFilters(new InputFilter[] {new DecimalDigitFilter(4)});
        s2t4TierText.setFilters(new InputFilter[] {new DecimalDigitFilter(4)});
        s3t1TierText.setFilters(new InputFilter[] {new DecimalDigitFilter(4)});
        s3t2TierText.setFilters(new InputFilter[] {new DecimalDigitFilter(4)});
        s3t3TierText.setFilters(new InputFilter[] {new DecimalDigitFilter(4)});
        s3t4TierText.setFilters(new InputFilter[] {new DecimalDigitFilter(4)});
        s4t1TierText.setFilters(new InputFilter[] {new DecimalDigitFilter(4)});
        s4t2TierText.setFilters(new InputFilter[] {new DecimalDigitFilter(4)});
        s4t3TierText.setFilters(new InputFilter[] {new DecimalDigitFilter(4)});
        s4t4TierText.setFilters(new InputFilter[] {new DecimalDigitFilter(4)});

        Intent i = getIntent();

        numSeasons = i.getIntExtra("numSeasons", 1);

        s1Tier = i.getIntExtra("s1Tier", 2);
        s2Tier = i.getIntExtra("s2Tier", 2);
        s3Tier = i.getIntExtra("s3Tier", 2);
        s4Tier = i.getIntExtra("s4Tier", 2);

        s1t1Tier = i.getDoubleExtra("s1t1Tier", 0);
        s2t1Tier = i.getDoubleExtra("s2t1Tier", 0);
        s3t1Tier = i.getDoubleExtra("s3t1Tier", 0);
        s4t1Tier = i.getDoubleExtra("s4t1Tier", 0);
        s1t2Tier = i.getDoubleExtra("s1t2Tier", 0);
        s2t2Tier = i.getDoubleExtra("s2t2Tier", 0);
        s3t2Tier = i.getDoubleExtra("s3t2Tier", 0);
        s4t2Tier = i.getDoubleExtra("s4t2Tier", 0);
        s1t3Tier = i.getDoubleExtra("s1t3Tier", 0);
        s2t3Tier = i.getDoubleExtra("s2t3Tier", 0);
        s3t3Tier = i.getDoubleExtra("s3t3Tier", 0);
        s4t3Tier = i.getDoubleExtra("s4t3Tier", 0);
        s1t4Tier = i.getDoubleExtra("s1t4Tier", 0);
        s2t4Tier = i.getDoubleExtra("s2t4Tier", 0);
        s3t4Tier = i.getDoubleExtra("s3t4Tier", 0);
        s4t4Tier = i.getDoubleExtra("s4t4Tier", 0);

        s1t1TierText.setText(String.format("%.4f", s1t1Tier));
        s2t1TierText.setText(String.format("%.4f", s2t1Tier));
        s3t1TierText.setText(String.format("%.4f", s3t1Tier));
        s4t1TierText.setText(String.format("%.4f", s4t1Tier));
        s1t2TierText.setText(String.format("%.4f", s1t2Tier));
        s2t2TierText.setText(String.format("%.4f", s2t2Tier));
        s3t2TierText.setText(String.format("%.4f", s3t2Tier));
        s4t2TierText.setText(String.format("%.4f", s4t2Tier));
        s1t3TierText.setText(String.format("%.4f", s1t3Tier));
        s2t3TierText.setText(String.format("%.4f", s2t3Tier));
        s3t3TierText.setText(String.format("%.4f", s3t3Tier));
        s4t3TierText.setText(String.format("%.4f", s4t3Tier));
        s1t4TierText.setText(String.format("%.4f", s1t4Tier));
        s2t4TierText.setText(String.format("%.4f", s2t4Tier));
        s3t4TierText.setText(String.format("%.4f", s3t4Tier));
        s4t4TierText.setText(String.format("%.4f", s4t4Tier));


        String[] frequencyOptions = new String[] {"1", "2", "3", "4"};
        ArrayAdapter<String> tierAdapter = new ArrayAdapter<String>(SetTierRangesActivity.this,
                android.R.layout.simple_spinner_item,frequencyOptions);
        tierAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        s1TierSpinner = findViewById(R.id.s1TierSpinner);
        s2TierSpinner = findViewById(R.id.s2TierSpinner);
        s3TierSpinner = findViewById(R.id.s3TierSpinner);
        s4TierSpinner = findViewById(R.id.s4TierSpinner);

        s1TierSpinner.setAdapter(tierAdapter);
        s2TierSpinner.setAdapter(tierAdapter);
        s3TierSpinner.setAdapter(tierAdapter);
        s4TierSpinner.setAdapter(tierAdapter);

        s1TierSpinner.setOnItemSelectedListener(this);
        s2TierSpinner.setOnItemSelectedListener(this);
        s3TierSpinner.setOnItemSelectedListener(this);
        s4TierSpinner.setOnItemSelectedListener(this);

        s1TierSpinner.setSelection(s1Tier - 1);
        s2TierSpinner.setSelection(s2Tier - 1);
        s3TierSpinner.setSelection(s3Tier - 1);
        s4TierSpinner.setSelection(s4Tier - 1);

        setSeasonsVisible(numSeasons);
    }

    public void submitTierRanges(View v) {
        Intent i = new Intent();

        //i.putExtra("numTiers", Integer.parseInt(tierSpinner.getSelectedItem().toString()));

        i.putExtra("s1t1Tier", Double.parseDouble(s1t1TierText.getText().toString()));
        i.putExtra("s2t1Tier", Double.parseDouble(s2t1TierText.getText().toString()));
        i.putExtra("s3t1Tier", Double.parseDouble(s3t1TierText.getText().toString()));
        i.putExtra("s4t1Tier", Double.parseDouble(s4t1TierText.getText().toString()));
        i.putExtra("s1t2Tier", Double.parseDouble(s1t2TierText.getText().toString()));
        i.putExtra("s2t2Tier", Double.parseDouble(s2t2TierText.getText().toString()));
        i.putExtra("s3t2Tier", Double.parseDouble(s3t2TierText.getText().toString()));
        i.putExtra("s4t2Tier", Double.parseDouble(s4t2TierText.getText().toString()));
        i.putExtra("s1t3Tier", Double.parseDouble(s1t3TierText.getText().toString()));
        i.putExtra("s2t3Tier", Double.parseDouble(s2t3TierText.getText().toString()));
        i.putExtra("s3t3Tier", Double.parseDouble(s3t3TierText.getText().toString()));
        i.putExtra("s4t3Tier", Double.parseDouble(s4t3TierText.getText().toString()));
        i.putExtra("s1t4Tier", Double.parseDouble(s1t4TierText.getText().toString()));
        i.putExtra("s2t4Tier", Double.parseDouble(s2t4TierText.getText().toString()));
        i.putExtra("s3t4Tier", Double.parseDouble(s3t4TierText.getText().toString()));
        i.putExtra("s4t4Tier", Double.parseDouble(s4t4TierText.getText().toString()));

        i.putExtra("s1Tier", Integer.parseInt(s1TierSpinner.getSelectedItem().toString()));
        i.putExtra("s2Tier", Integer.parseInt(s2TierSpinner.getSelectedItem().toString()));
        i.putExtra("s3Tier", Integer.parseInt(s3TierSpinner.getSelectedItem().toString()));
        i.putExtra("s4Tier", Integer.parseInt(s4TierSpinner.getSelectedItem().toString()));

        setResult(RESULT_OK, i);
        finish();
    }

    public void cancel(View v) {
        Intent i = new Intent();
        setResult(RESULT_CANCELED, i);
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch(parent.getId()) {
            case R.id.s1TierSpinner:
                setS1TiersVisible(Integer.parseInt(parent.getSelectedItem().toString()));
                break;
            case R.id.s2TierSpinner:
                setS2TiersVisible(Integer.parseInt(parent.getSelectedItem().toString()));
                break;
            case R.id.s3TierSpinner:
                setS3TiersVisible(Integer.parseInt(parent.getSelectedItem().toString()));
                break;
            case R.id.s4TierSpinner:
                setS4TiersVisible(Integer.parseInt(parent.getSelectedItem().toString()));
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void setSeasonsVisible(int numSeasons) {
        switch (numSeasons) {
            case 1:
                findViewById(R.id.s2TierRangeLayout).setVisibility(View.GONE);
                findViewById(R.id.s3TierRangeLayout).setVisibility(View.GONE);
                findViewById(R.id.s4TierRangeLayout).setVisibility(View.GONE);
                break;
            case 2:
                findViewById(R.id.s2TierRangeLayout).setVisibility(View.VISIBLE);
                findViewById(R.id.s3TierRangeLayout).setVisibility(View.GONE);
                findViewById(R.id.s4TierRangeLayout).setVisibility(View.GONE);
                break;
            case 3:
                findViewById(R.id.s2TierRangeLayout).setVisibility(View.VISIBLE);
                findViewById(R.id.s3TierRangeLayout).setVisibility(View.VISIBLE);
                findViewById(R.id.s4TierRangeLayout).setVisibility(View.GONE);
                break;
            case 4:
                findViewById(R.id.s2TierRangeLayout).setVisibility(View.VISIBLE);
                findViewById(R.id.s3TierRangeLayout).setVisibility(View.VISIBLE);
                findViewById(R.id.s4TierRangeLayout).setVisibility(View.VISIBLE);
                break;
        }
    }

    public void setS1TiersVisible(int n) {
        switch (n) {
            case 4:
                findViewById(R.id.s1t2Layout).setVisibility(View.VISIBLE);
                findViewById(R.id.s1t3Layout).setVisibility(View.VISIBLE);
                findViewById(R.id.s1t4Layout).setVisibility(View.VISIBLE);
                break;
            case 3:
                findViewById(R.id.s1t2Layout).setVisibility(View.VISIBLE);
                findViewById(R.id.s1t3Layout).setVisibility(View.VISIBLE);
                findViewById(R.id.s1t4Layout).setVisibility(View.GONE);
                break;
            case 2:
                findViewById(R.id.s1t2Layout).setVisibility(View.VISIBLE);
                findViewById(R.id.s1t3Layout).setVisibility(View.GONE);
                findViewById(R.id.s1t4Layout).setVisibility(View.GONE);
                break;
            case 1:
                findViewById(R.id.s1t2Layout).setVisibility(View.GONE);
                findViewById(R.id.s1t3Layout).setVisibility(View.GONE);
                findViewById(R.id.s1t4Layout).setVisibility(View.GONE);
                break;
        }
    }

    public void setS2TiersVisible(int n) {
        switch (n) {
            case 4:
                findViewById(R.id.s2t2Layout).setVisibility(View.VISIBLE);
                findViewById(R.id.s2t3Layout).setVisibility(View.VISIBLE);
                findViewById(R.id.s2t4Layout).setVisibility(View.VISIBLE);
                break;
            case 3:
                findViewById(R.id.s2t2Layout).setVisibility(View.VISIBLE);
                findViewById(R.id.s2t3Layout).setVisibility(View.VISIBLE);
                findViewById(R.id.s2t4Layout).setVisibility(View.GONE);
                break;
            case 2:
                findViewById(R.id.s2t2Layout).setVisibility(View.VISIBLE);
                findViewById(R.id.s2t3Layout).setVisibility(View.GONE);
                findViewById(R.id.s2t4Layout).setVisibility(View.GONE);
                break;
            case 1:
                findViewById(R.id.s2t2Layout).setVisibility(View.GONE);
                findViewById(R.id.s2t3Layout).setVisibility(View.GONE);
                findViewById(R.id.s2t4Layout).setVisibility(View.GONE);
                break;
        }
    }

    public void setS3TiersVisible(int n) {
        switch (n) {
            case 4:
                findViewById(R.id.s3t2Layout).setVisibility(View.VISIBLE);
                findViewById(R.id.s3t3Layout).setVisibility(View.VISIBLE);
                findViewById(R.id.s3t4Layout).setVisibility(View.VISIBLE);
                break;
            case 3:
                findViewById(R.id.s3t2Layout).setVisibility(View.VISIBLE);
                findViewById(R.id.s3t3Layout).setVisibility(View.VISIBLE);
                findViewById(R.id.s3t4Layout).setVisibility(View.GONE);
                break;
            case 2:
                findViewById(R.id.s3t2Layout).setVisibility(View.VISIBLE);
                findViewById(R.id.s3t3Layout).setVisibility(View.GONE);
                findViewById(R.id.s3t4Layout).setVisibility(View.GONE);
                break;
            case 1:
                findViewById(R.id.s3t2Layout).setVisibility(View.GONE);
                findViewById(R.id.s3t3Layout).setVisibility(View.GONE);
                findViewById(R.id.s3t4Layout).setVisibility(View.GONE);
                break;
        }
    }

    public void setS4TiersVisible(int n) {
        switch (n) {
            case 4:
                findViewById(R.id.s4t2Layout).setVisibility(View.VISIBLE);
                findViewById(R.id.s4t3Layout).setVisibility(View.VISIBLE);
                findViewById(R.id.s4t4Layout).setVisibility(View.VISIBLE);
                break;
            case 3:
                findViewById(R.id.s4t2Layout).setVisibility(View.VISIBLE);
                findViewById(R.id.s4t3Layout).setVisibility(View.VISIBLE);
                findViewById(R.id.s4t4Layout).setVisibility(View.GONE);
                break;
            case 2:
                findViewById(R.id.s4t2Layout).setVisibility(View.VISIBLE);
                findViewById(R.id.s4t3Layout).setVisibility(View.GONE);
                findViewById(R.id.s4t4Layout).setVisibility(View.GONE);
                break;
            case 1:
                findViewById(R.id.s4t2Layout).setVisibility(View.GONE);
                findViewById(R.id.s4t3Layout).setVisibility(View.GONE);
                findViewById(R.id.s4t4Layout).setVisibility(View.GONE);
                break;
        }
    }

}