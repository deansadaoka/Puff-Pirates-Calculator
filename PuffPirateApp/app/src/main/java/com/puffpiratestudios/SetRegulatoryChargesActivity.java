package com.puffpiratestudios;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SetRegulatoryChargesActivity extends AppCompatActivity {

    int numSeasons;

    double s1Reg;
    double s2Reg;
    double s3Reg;
    double s4Reg;

    EditText s1RegText;
    EditText s2RegText;
    EditText s3RegText;
    EditText s4RegText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_regulatory_charges);

        TextView title = (TextView) findViewById(R.id.RegChargeTitle);
        title.setBackgroundColor(Color.parseColor("#d71414"));
        title.setTextColor(getResources().getColor(R.color.white));

        s1RegText = findViewById(R.id.s1RCharge);
        s2RegText = findViewById(R.id.s2RCharge);
        s3RegText = findViewById(R.id.s3RCharge);
        s4RegText = findViewById(R.id.s4RCharge);

        s1RegText.setFilters(new InputFilter[] {new DecimalDigitFilter(4)});
        s2RegText.setFilters(new InputFilter[] {new DecimalDigitFilter(4)});
        s3RegText.setFilters(new InputFilter[] {new DecimalDigitFilter(4)});
        s4RegText.setFilters(new InputFilter[] {new DecimalDigitFilter(4)});

        Intent i = getIntent();

        numSeasons = i.getIntExtra("numSeasons", 1);

        s1Reg = i.getDoubleExtra("s1Reg", 0);
        s2Reg = i.getDoubleExtra("s2Reg", 0);
        s3Reg = i.getDoubleExtra("s3Reg", 0);
        s4Reg = i.getDoubleExtra("s4Reg", 0);

        s1RegText.setText(String.format("%.4f", s1Reg));
        s2RegText.setText(String.format("%.4f", s2Reg));
        s3RegText.setText(String.format("%.4f", s3Reg));
        s4RegText.setText(String.format("%.4f", s4Reg));

        setSeasonsVisible(numSeasons);
    }

    public void submitRegChargeRates(View v) {
        Intent i = new Intent();

        i.putExtra("s1Reg", Double.parseDouble(s1RegText.getText().toString()));
        i.putExtra("s2Reg", Double.parseDouble(s2RegText.getText().toString()));
        i.putExtra("s3Reg", Double.parseDouble(s3RegText.getText().toString()));
        i.putExtra("s4Reg", Double.parseDouble(s4RegText.getText().toString()));

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
                findViewById(R.id.s2RegLayout).setVisibility(View.GONE);
                findViewById(R.id.s3RegLayout).setVisibility(View.GONE);
                findViewById(R.id.s4RegLayout).setVisibility(View.GONE);
                break;
            case 2:
                findViewById(R.id.s2RegLayout).setVisibility(View.VISIBLE);
                findViewById(R.id.s3RegLayout).setVisibility(View.GONE);
                findViewById(R.id.s4RegLayout).setVisibility(View.GONE);
                break;
            case 3:
                findViewById(R.id.s2RegLayout).setVisibility(View.VISIBLE);
                findViewById(R.id.s3RegLayout).setVisibility(View.VISIBLE);
                findViewById(R.id.s4RegLayout).setVisibility(View.GONE);
                break;
            case 4:
                findViewById(R.id.s2RegLayout).setVisibility(View.VISIBLE);
                findViewById(R.id.s3RegLayout).setVisibility(View.VISIBLE);
                findViewById(R.id.s4RegLayout).setVisibility(View.VISIBLE);
                break;
        }
    }
}