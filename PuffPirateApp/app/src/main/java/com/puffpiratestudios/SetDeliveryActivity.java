package com.puffpiratestudios;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SetDeliveryActivity extends AppCompatActivity {

    int numSeasons;

    double s1Delivery;
    double s2Delivery;
    double s3Delivery;
    double s4Delivery;

    EditText s1DeliveryText;
    EditText s2DeliveryText;
    EditText s3DeliveryText;
    EditText s4DeliveryText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_delivery);

        TextView title = (TextView) findViewById(R.id.DeliveryTitle);
        title.setBackgroundColor(Color.parseColor("#d71414"));
        title.setTextColor(getResources().getColor(R.color.white));

        s1DeliveryText = findViewById(R.id.s1Delivery);
        s2DeliveryText = findViewById(R.id.s2Delivery);
        s3DeliveryText = findViewById(R.id.s3Delivery);
        s4DeliveryText = findViewById(R.id.s4Delivery);

        s1DeliveryText.setFilters(new InputFilter[] {new DecimalDigitFilter(4)});
        s2DeliveryText.setFilters(new InputFilter[] {new DecimalDigitFilter(4)});
        s3DeliveryText.setFilters(new InputFilter[] {new DecimalDigitFilter(4)});
        s4DeliveryText.setFilters(new InputFilter[] {new DecimalDigitFilter(4)});

        Intent i = getIntent();

        numSeasons = i.getIntExtra("numSeasons", 1);

        s1Delivery = i.getDoubleExtra("s1Delivery", 0);
        s2Delivery = i.getDoubleExtra("s2Delivery", 0);
        s3Delivery = i.getDoubleExtra("s3Delivery", 0);
        s4Delivery = i.getDoubleExtra("s4Delivery", 0);

        s1DeliveryText.setText(String.format("%.4f", s1Delivery));
        s2DeliveryText.setText(String.format("%.4f", s2Delivery));
        s3DeliveryText.setText(String.format("%.4f", s3Delivery));
        s4DeliveryText.setText(String.format("%.4f", s4Delivery));

        setSeasonsVisible(numSeasons);
    }

    public void submitDeliveryRates(View v) {
        Intent i = new Intent();

        i.putExtra("s1Delivery", Double.parseDouble(s1DeliveryText.getText().toString()));
        i.putExtra("s2Delivery", Double.parseDouble(s2DeliveryText.getText().toString()));
        i.putExtra("s3Delivery", Double.parseDouble(s3DeliveryText.getText().toString()));
        i.putExtra("s4Delivery", Double.parseDouble(s4DeliveryText.getText().toString()));

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
                findViewById(R.id.s2DeliveryLayout).setVisibility(View.GONE);
                findViewById(R.id.s3DeliveryLayout).setVisibility(View.GONE);
                findViewById(R.id.s4DeliveryLayout).setVisibility(View.GONE);
                break;
            case 2:
                findViewById(R.id.s2DeliveryLayout).setVisibility(View.VISIBLE);
                findViewById(R.id.s3DeliveryLayout).setVisibility(View.GONE);
                findViewById(R.id.s4DeliveryLayout).setVisibility(View.GONE);
                break;
            case 3:
                findViewById(R.id.s2DeliveryLayout).setVisibility(View.VISIBLE);
                findViewById(R.id.s3DeliveryLayout).setVisibility(View.VISIBLE);
                findViewById(R.id.s4DeliveryLayout).setVisibility(View.GONE);
                break;
            case 4:
                findViewById(R.id.s2DeliveryLayout).setVisibility(View.VISIBLE);
                findViewById(R.id.s3DeliveryLayout).setVisibility(View.VISIBLE);
                findViewById(R.id.s4DeliveryLayout).setVisibility(View.VISIBLE);
                break;
        }
    }
}