package com.puffpiratestudios;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SetUtilityRatesActivity extends AppCompatActivity {

    int numSeasons;
    int calcType;

    int s1Tier;
    int s2Tier;
    int s3Tier;
    int s4Tier;

    double s1t1Util;
    double s2t1Util;
    double s3t1Util;
    double s4t1Util;
    double s1t2Util;
    double s2t2Util;
    double s3t2Util;
    double s4t2Util;
    double s1t3Util;
    double s2t3Util;
    double s3t3Util;
    double s4t3Util;
    double s1t4Util;
    double s2t4Util;
    double s3t4Util;
    double s4t4Util;

    EditText s1t1UtilText;
    EditText s2t1UtilText;
    EditText s3t1UtilText;
    EditText s4t1UtilText;
    EditText s1t2UtilText;
    EditText s2t2UtilText;
    EditText s3t2UtilText;
    EditText s4t2UtilText;
    EditText s1t3UtilText;
    EditText s2t3UtilText;
    EditText s3t3UtilText;
    EditText s4t3UtilText;
    EditText s1t4UtilText;
    EditText s2t4UtilText;
    EditText s3t4UtilText;
    EditText s4t4UtilText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_utility_rates);

        TextView title = (TextView) findViewById(R.id.UtilTitle);
        title.setBackgroundColor(Color.parseColor("#d71414"));
        title.setTextColor(getResources().getColor(R.color.white));

        TextView s1Title = (TextView) findViewById(R.id.s1UtilTitle);
        s1Title.setBackgroundColor(Color.parseColor("#30bf04"));
        s1Title.setTextColor(getResources().getColor(R.color.white));

        TextView s2Title = (TextView) findViewById(R.id.s2UtilTitle);
        s2Title.setBackgroundColor(Color.parseColor("#30bf04"));
        s2Title.setTextColor(getResources().getColor(R.color.white));

        TextView s3Title = (TextView) findViewById(R.id.s3UtilTitle);
        s3Title.setBackgroundColor(Color.parseColor("#30bf04"));
        s3Title.setTextColor(getResources().getColor(R.color.white));

        TextView s4Title = (TextView) findViewById(R.id.s4UtilTitle);
        s4Title.setBackgroundColor(Color.parseColor("#30bf04"));
        s4Title.setTextColor(getResources().getColor(R.color.white));

        s1t1UtilText = findViewById(R.id.s1t1Util);
        s2t1UtilText = findViewById(R.id.s2t1Util);
        s3t1UtilText = findViewById(R.id.s3t1Util);
        s4t1UtilText = findViewById(R.id.s4t1Util);
        s1t2UtilText = findViewById(R.id.s1t2Util);
        s2t2UtilText = findViewById(R.id.s2t2Util);
        s3t2UtilText = findViewById(R.id.s3t2Util);
        s4t2UtilText = findViewById(R.id.s4t2Util);
        s1t3UtilText = findViewById(R.id.s1t3Util);
        s2t3UtilText = findViewById(R.id.s2t3Util);
        s3t3UtilText = findViewById(R.id.s3t3Util);
        s4t3UtilText = findViewById(R.id.s4t3Util);
        s1t4UtilText = findViewById(R.id.s1t4Util);
        s2t4UtilText = findViewById(R.id.s2t4Util);
        s3t4UtilText = findViewById(R.id.s3t4Util);
        s4t4UtilText = findViewById(R.id.s4t4Util);

        s1t1UtilText.setFilters(new InputFilter[] {new DecimalDigitFilter(4)});
        s1t2UtilText.setFilters(new InputFilter[] {new DecimalDigitFilter(4)});
        s1t3UtilText.setFilters(new InputFilter[] {new DecimalDigitFilter(4)});
        s1t4UtilText.setFilters(new InputFilter[] {new DecimalDigitFilter(4)});
        s2t1UtilText.setFilters(new InputFilter[] {new DecimalDigitFilter(4)});
        s2t2UtilText.setFilters(new InputFilter[] {new DecimalDigitFilter(4)});
        s2t3UtilText.setFilters(new InputFilter[] {new DecimalDigitFilter(4)});
        s2t4UtilText.setFilters(new InputFilter[] {new DecimalDigitFilter(4)});
        s3t1UtilText.setFilters(new InputFilter[] {new DecimalDigitFilter(4)});
        s3t2UtilText.setFilters(new InputFilter[] {new DecimalDigitFilter(4)});
        s3t3UtilText.setFilters(new InputFilter[] {new DecimalDigitFilter(4)});
        s3t4UtilText.setFilters(new InputFilter[] {new DecimalDigitFilter(4)});
        s4t1UtilText.setFilters(new InputFilter[] {new DecimalDigitFilter(4)});
        s4t2UtilText.setFilters(new InputFilter[] {new DecimalDigitFilter(4)});
        s4t3UtilText.setFilters(new InputFilter[] {new DecimalDigitFilter(4)});
        s4t4UtilText.setFilters(new InputFilter[] {new DecimalDigitFilter(4)});



        Intent i = getIntent();

        numSeasons = i.getIntExtra("numSeasons", 1);

        calcType = i.getIntExtra("calcType", 1);

        s1Tier = i.getIntExtra("s1Tier", 2);
        s2Tier = i.getIntExtra("s2Tier", 2);
        s3Tier = i.getIntExtra("s3Tier", 2);
        s4Tier = i.getIntExtra("s4Tier", 2);

        s1t1Util = i.getDoubleExtra("s1t1Util", 0);
        s2t1Util = i.getDoubleExtra("s2t1Util", 0);
        s3t1Util = i.getDoubleExtra("s3t1Util", 0);
        s4t1Util = i.getDoubleExtra("s4t1Util", 0);
        s1t2Util = i.getDoubleExtra("s1t2Util", 0);
        s2t2Util = i.getDoubleExtra("s2t2Util", 0);
        s3t2Util = i.getDoubleExtra("s3t2Util", 0);
        s4t2Util = i.getDoubleExtra("s4t2Util", 0);
        s1t3Util = i.getDoubleExtra("s1t3Util", 0);
        s2t3Util = i.getDoubleExtra("s2t3Util", 0);
        s3t3Util = i.getDoubleExtra("s3t3Util", 0);
        s4t3Util = i.getDoubleExtra("s4t3Util", 0);
        s1t4Util = i.getDoubleExtra("s1t4Util", 0);
        s2t4Util = i.getDoubleExtra("s2t4Util", 0);
        s3t4Util = i.getDoubleExtra("s3t4Util", 0);
        s4t4Util = i.getDoubleExtra("s4t4Util", 0);

        s1t1UtilText.setText(String.format("%.4f", s1t1Util));
        s2t1UtilText.setText(String.format("%.4f", s2t1Util));
        s3t1UtilText.setText(String.format("%.4f", s3t1Util));
        s4t1UtilText.setText(String.format("%.4f", s4t1Util));
        s1t2UtilText.setText(String.format("%.4f", s1t2Util));
        s2t2UtilText.setText(String.format("%.4f", s2t2Util));
        s3t2UtilText.setText(String.format("%.4f", s3t2Util));
        s4t2UtilText.setText(String.format("%.4f", s4t2Util));
        s1t3UtilText.setText(String.format("%.4f", s1t3Util));
        s2t3UtilText.setText(String.format("%.4f", s2t3Util));
        s3t3UtilText.setText(String.format("%.4f", s3t3Util));
        s4t3UtilText.setText(String.format("%.4f", s4t3Util));
        s1t4UtilText.setText(String.format("%.4f", s1t4Util));
        s2t4UtilText.setText(String.format("%.4f", s2t4Util));
        s3t4UtilText.setText(String.format("%.4f", s3t4Util));
        s4t4UtilText.setText(String.format("%.4f", s4t4Util));

        setSeasonsVisible(numSeasons);
        setTypeVisible(calcType);
        setS1TierVisible(s1Tier);
        setS2TierVisible(s2Tier);
        setS3TierVisible(s3Tier);
        setS4TierVisible(s4Tier);

    }

    public void submitUtilRates(View v) {
        Intent i = new Intent();

        i.putExtra("s1t1Util", Double.parseDouble(s1t1UtilText.getText().toString()));
        i.putExtra("s2t1Util", Double.parseDouble(s2t1UtilText.getText().toString()));
        i.putExtra("s3t1Util", Double.parseDouble(s3t1UtilText.getText().toString()));
        i.putExtra("s4t1Util", Double.parseDouble(s4t1UtilText.getText().toString()));
        i.putExtra("s1t2Util", Double.parseDouble(s1t2UtilText.getText().toString()));
        i.putExtra("s2t2Util", Double.parseDouble(s2t2UtilText.getText().toString()));
        i.putExtra("s3t2Util", Double.parseDouble(s3t2UtilText.getText().toString()));
        i.putExtra("s4t2Util", Double.parseDouble(s4t2UtilText.getText().toString()));
        i.putExtra("s1t3Util", Double.parseDouble(s1t3UtilText.getText().toString()));
        i.putExtra("s2t3Util", Double.parseDouble(s2t3UtilText.getText().toString()));
        i.putExtra("s3t3Util", Double.parseDouble(s3t3UtilText.getText().toString()));
        i.putExtra("s4t3Util", Double.parseDouble(s4t3UtilText.getText().toString()));
        i.putExtra("s1t4Util", Double.parseDouble(s1t4UtilText.getText().toString()));
        i.putExtra("s2t4Util", Double.parseDouble(s2t4UtilText.getText().toString()));
        i.putExtra("s3t4Util", Double.parseDouble(s3t4UtilText.getText().toString()));
        i.putExtra("s4t4Util", Double.parseDouble(s4t4UtilText.getText().toString()));

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
                findViewById(R.id.s2UtilLayout).setVisibility(View.GONE);
                findViewById(R.id.s3UtilLayout).setVisibility(View.GONE);
                findViewById(R.id.s4UtilLayout).setVisibility(View.GONE);
                break;
            case 2:
                findViewById(R.id.s2UtilLayout).setVisibility(View.VISIBLE);
                findViewById(R.id.s3UtilLayout).setVisibility(View.GONE);
                findViewById(R.id.s4UtilLayout).setVisibility(View.GONE);
                break;
            case 3:
                findViewById(R.id.s2UtilLayout).setVisibility(View.VISIBLE);
                findViewById(R.id.s3UtilLayout).setVisibility(View.VISIBLE);
                findViewById(R.id.s4UtilLayout).setVisibility(View.GONE);
                break;
            case 4:
                findViewById(R.id.s2UtilLayout).setVisibility(View.VISIBLE);
                findViewById(R.id.s3UtilLayout).setVisibility(View.VISIBLE);
                findViewById(R.id.s4UtilLayout).setVisibility(View.VISIBLE);
                break;
        }
    }

    public void setS1TierVisible(int s1Tier) {
        switch (s1Tier) {
            case 1:
                findViewById(R.id.s1t2UtilLayout).setVisibility(View.GONE);
                findViewById(R.id.s1t3UtilLayout).setVisibility(View.GONE);
                findViewById(R.id.s1t4UtilLayout).setVisibility(View.GONE);
                break;
            case 2:
                findViewById(R.id.s1t2UtilLayout).setVisibility(View.VISIBLE);
                findViewById(R.id.s1t3UtilLayout).setVisibility(View.GONE);
                findViewById(R.id.s1t4UtilLayout).setVisibility(View.GONE);
                break;
            case 3:
                findViewById(R.id.s1t2UtilLayout).setVisibility(View.VISIBLE);
                findViewById(R.id.s1t3UtilLayout).setVisibility(View.VISIBLE);
                findViewById(R.id.s1t4UtilLayout).setVisibility(View.GONE);
                break;
            case 4:
                findViewById(R.id.s1t2UtilLayout).setVisibility(View.VISIBLE);
                findViewById(R.id.s1t3UtilLayout).setVisibility(View.VISIBLE);
                findViewById(R.id.s1t4UtilLayout).setVisibility(View.VISIBLE);
                break;
        }
    }

    public void setS2TierVisible(int s2Tier) {
        switch (s2Tier) {
            case 1:
                findViewById(R.id.s2t2UtilLayout).setVisibility(View.GONE);
                findViewById(R.id.s2t3UtilLayout).setVisibility(View.GONE);
                findViewById(R.id.s2t4UtilLayout).setVisibility(View.GONE);
                break;
            case 2:
                findViewById(R.id.s2t2UtilLayout).setVisibility(View.VISIBLE);
                findViewById(R.id.s2t3UtilLayout).setVisibility(View.GONE);
                findViewById(R.id.s2t4UtilLayout).setVisibility(View.GONE);
                break;
            case 3:
                findViewById(R.id.s2t2UtilLayout).setVisibility(View.VISIBLE);
                findViewById(R.id.s2t3UtilLayout).setVisibility(View.VISIBLE);
                findViewById(R.id.s2t4UtilLayout).setVisibility(View.GONE);
                break;
            case 4:
                findViewById(R.id.s2t2UtilLayout).setVisibility(View.VISIBLE);
                findViewById(R.id.s2t3UtilLayout).setVisibility(View.VISIBLE);
                findViewById(R.id.s2t4UtilLayout).setVisibility(View.VISIBLE);
                break;
        }
    }

    public void setS3TierVisible(int s3Tier) {
        switch (s3Tier) {
            case 1:
                findViewById(R.id.s3t2UtilLayout).setVisibility(View.GONE);
                findViewById(R.id.s3t3UtilLayout).setVisibility(View.GONE);
                findViewById(R.id.s3t4UtilLayout).setVisibility(View.GONE);
                break;
            case 2:
                findViewById(R.id.s3t2UtilLayout).setVisibility(View.VISIBLE);
                findViewById(R.id.s3t3UtilLayout).setVisibility(View.GONE);
                findViewById(R.id.s3t4UtilLayout).setVisibility(View.GONE);
                break;
            case 3:
                findViewById(R.id.s3t2UtilLayout).setVisibility(View.VISIBLE);
                findViewById(R.id.s3t3UtilLayout).setVisibility(View.VISIBLE);
                findViewById(R.id.s3t4UtilLayout).setVisibility(View.GONE);
                break;
            case 4:
                findViewById(R.id.s3t2UtilLayout).setVisibility(View.VISIBLE);
                findViewById(R.id.s3t3UtilLayout).setVisibility(View.VISIBLE);
                findViewById(R.id.s3t4UtilLayout).setVisibility(View.VISIBLE);
                break;
        }
    }

    public void setS4TierVisible(int s4Tier) {
        switch (s4Tier) {
            case 1:
                findViewById(R.id.s4t2UtilLayout).setVisibility(View.GONE);
                findViewById(R.id.s4t3UtilLayout).setVisibility(View.GONE);
                findViewById(R.id.s4t4UtilLayout).setVisibility(View.GONE);
                break;
            case 2:
                findViewById(R.id.s4t2UtilLayout).setVisibility(View.VISIBLE);
                findViewById(R.id.s4t3UtilLayout).setVisibility(View.GONE);
                findViewById(R.id.s4t4UtilLayout).setVisibility(View.GONE);
                break;
            case 3:
                findViewById(R.id.s4t2UtilLayout).setVisibility(View.VISIBLE);
                findViewById(R.id.s4t3UtilLayout).setVisibility(View.VISIBLE);
                findViewById(R.id.s4t4UtilLayout).setVisibility(View.GONE);
                break;
            case 4:
                findViewById(R.id.s4t2UtilLayout).setVisibility(View.VISIBLE);
                findViewById(R.id.s4t3UtilLayout).setVisibility(View.VISIBLE);
                findViewById(R.id.s4t4UtilLayout).setVisibility(View.VISIBLE);
                break;
        }
    }

    public void setTypeVisible(int structure) {
        switch(structure) {
            case 0:
                ((TextView)findViewById(R.id.s1t1UtilText)).setText("Season 1 Tier 1 Rate: $");
                ((TextView)findViewById(R.id.s2t1UtilText)).setText("Season 2 Tier 1 Rate: $");
                ((TextView)findViewById(R.id.s3t1UtilText)).setText("Season 3 Tier 1 Rate: $");
                ((TextView)findViewById(R.id.s4t1UtilText)).setText("Season 4 Tier 1 Rate: $");
                break;
            case 1:
                ((TextView)findViewById(R.id.s1t1UtilText)).setText("Season 1 Rate: $");
                ((TextView)findViewById(R.id.s2t1UtilText)).setText("Season 2 Rate: $");
                ((TextView)findViewById(R.id.s3t1UtilText)).setText("Season 3 Rate: $");
                ((TextView)findViewById(R.id.s4t1UtilText)).setText("Season 4 Rate: $");
                break;
        }
    }


}