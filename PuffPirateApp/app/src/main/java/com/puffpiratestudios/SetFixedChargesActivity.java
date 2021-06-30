package com.puffpiratestudios;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SetFixedChargesActivity extends AppCompatActivity {

    int numSeasons;

    double s1Fixed;
    double s2Fixed;
    double s3Fixed;
    double s4Fixed;

    EditText s1FixedText;
    EditText s2FixedText;
    EditText s3FixedText;
    EditText s4FixedText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_fixed_charges);

        TextView title = (TextView) findViewById(R.id.FixedChargeTitle);
        title.setBackgroundColor(Color.parseColor("#d71414"));
        title.setTextColor(getResources().getColor(R.color.white));

        s1FixedText = findViewById(R.id.s1FCharge);
        s2FixedText = findViewById(R.id.s2FCharge);
        s3FixedText = findViewById(R.id.s3FCharge);
        s4FixedText = findViewById(R.id.s4FCharge);

        s1FixedText.setFilters(new InputFilter[] {new DecimalDigitFilter(4)});
        s2FixedText.setFilters(new InputFilter[] {new DecimalDigitFilter(4)});
        s3FixedText.setFilters(new InputFilter[] {new DecimalDigitFilter(4)});
        s4FixedText.setFilters(new InputFilter[] {new DecimalDigitFilter(4)});

        Intent i = getIntent();

        numSeasons = i.getIntExtra("numSeasons", 1);

        s1Fixed = i.getDoubleExtra("s1Fixed", 0);
        s2Fixed = i.getDoubleExtra("s2Fixed", 0);
        s3Fixed = i.getDoubleExtra("s3Fixed", 0);
        s4Fixed = i.getDoubleExtra("s4Fixed", 0);

        s1FixedText.setText(String.format("%.4f", s1Fixed));
        s2FixedText.setText(String.format("%.4f", s2Fixed));
        s3FixedText.setText(String.format("%.4f", s3Fixed));
        s4FixedText.setText(String.format("%.4f", s4Fixed));

        setSeasonsVisible(numSeasons);
    }

    public void submitFixedChargeRates(View v) {
        Intent i = new Intent();

        i.putExtra("s1Fixed", Double.parseDouble(s1FixedText.getText().toString()));
        i.putExtra("s2Fixed", Double.parseDouble(s2FixedText.getText().toString()));
        i.putExtra("s3Fixed", Double.parseDouble(s3FixedText.getText().toString()));
        i.putExtra("s4Fixed", Double.parseDouble(s4FixedText.getText().toString()));

        setResult(RESULT_OK, i);
        finish();
    }

    public void cancel(View v) {
        Intent i = new Intent();
        setResult(RESULT_CANCELED, i);
        finish();
    }

    public void setSeasonsVisible(int numSeasons) {
        switch (numSeasons) {
            case 1:
                findViewById(R.id.s2FixedLayout).setVisibility(View.GONE);
                findViewById(R.id.s3FixedLayout).setVisibility(View.GONE);
                findViewById(R.id.s4FixedLayout).setVisibility(View.GONE);
                break;
            case 2:
                findViewById(R.id.s2FixedLayout).setVisibility(View.VISIBLE);
                findViewById(R.id.s3FixedLayout).setVisibility(View.GONE);
                findViewById(R.id.s4FixedLayout).setVisibility(View.GONE);
                break;
            case 3:
                findViewById(R.id.s2FixedLayout).setVisibility(View.VISIBLE);
                findViewById(R.id.s3FixedLayout).setVisibility(View.VISIBLE);
                findViewById(R.id.s4FixedLayout).setVisibility(View.GONE);
                break;
            case 4:
                findViewById(R.id.s2FixedLayout).setVisibility(View.VISIBLE);
                findViewById(R.id.s3FixedLayout).setVisibility(View.VISIBLE);
                findViewById(R.id.s4FixedLayout).setVisibility(View.VISIBLE);
                break;
        }
    }
}