package com.puffpiratestudios;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SetTaxRatesActivity extends AppCompatActivity {

    int numSeasons;

    double s1Tax;
    double s2Tax;
    double s3Tax;
    double s4Tax;

    EditText s1TaxText;
    EditText s2TaxText;
    EditText s3TaxText;
    EditText s4TaxText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_tax_rates);

        TextView title = (TextView) findViewById(R.id.TaxTitle);
        title.setBackgroundColor(Color.parseColor("#d71414"));
        title.setTextColor(getResources().getColor(R.color.white));

        s1TaxText = findViewById(R.id.s1Tax);
        s2TaxText = findViewById(R.id.s2Tax);
        s3TaxText = findViewById(R.id.s3Tax);
        s4TaxText = findViewById(R.id.s4Tax);

        s1TaxText.setFilters(new InputFilter[] {new DecimalDigitFilter(4)});
        s2TaxText.setFilters(new InputFilter[] {new DecimalDigitFilter(4)});
        s3TaxText.setFilters(new InputFilter[] {new DecimalDigitFilter(4)});
        s4TaxText.setFilters(new InputFilter[] {new DecimalDigitFilter(4)});

        Intent i = getIntent();

        numSeasons = i.getIntExtra("numSeasons", 1);

        s1Tax = i.getDoubleExtra("s1Tax", 0);
        s2Tax = i.getDoubleExtra("s2Tax", 0);
        s3Tax = i.getDoubleExtra("s3Tax", 0);
        s4Tax = i.getDoubleExtra("s4Tax", 0);

        s1TaxText.setText(String.format("%.2f", s1Tax));
        s2TaxText.setText(String.format("%.2f", s2Tax));
        s3TaxText.setText(String.format("%.2f", s3Tax));
        s4TaxText.setText(String.format("%.2f", s4Tax));

        setSeasonsVisible(numSeasons);
    }

    public void submitTaxRates(View v) {
        Intent i = new Intent();

        i.putExtra("s1Tax", Double.parseDouble(s1TaxText.getText().toString()));
        i.putExtra("s2Tax", Double.parseDouble(s2TaxText.getText().toString()));
        i.putExtra("s3Tax", Double.parseDouble(s3TaxText.getText().toString()));
        i.putExtra("s4Tax", Double.parseDouble(s4TaxText.getText().toString()));

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
                findViewById(R.id.s2TaxLayout).setVisibility(View.GONE);
                findViewById(R.id.s3TaxLayout).setVisibility(View.GONE);
                findViewById(R.id.s4TaxLayout).setVisibility(View.GONE);
                break;
            case 2:
                findViewById(R.id.s2TaxLayout).setVisibility(View.VISIBLE);
                findViewById(R.id.s3TaxLayout).setVisibility(View.GONE);
                findViewById(R.id.s4TaxLayout).setVisibility(View.GONE);
                break;
            case 3:
                findViewById(R.id.s2TaxLayout).setVisibility(View.VISIBLE);
                findViewById(R.id.s3TaxLayout).setVisibility(View.VISIBLE);
                findViewById(R.id.s4TaxLayout).setVisibility(View.GONE);
                break;
            case 4:
                findViewById(R.id.s2TaxLayout).setVisibility(View.VISIBLE);
                findViewById(R.id.s3TaxLayout).setVisibility(View.VISIBLE);
                findViewById(R.id.s4TaxLayout).setVisibility(View.VISIBLE);
                break;
        }
    }
}