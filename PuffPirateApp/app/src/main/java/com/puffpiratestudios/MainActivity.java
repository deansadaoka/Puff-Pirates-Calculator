package com.puffpiratestudios;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private static final int FIXED_CHARGES_ACTIVITY_REQUEST_CODE = 2;
    private static final int REGULATORY_CHARGES_ACTIVITY_REQUEST_CODE = 3;
    private static final int DELIVERY_ACTIVITY_REQUEST_CODE = 4;
    private static final int TAX_RATES_ACTIVITY_REQUEST_CODE = 5;
    private static final int UTILITY_RATES_ACTIVITY_REQUEST_CODE = 6;
    private static final int TIER_RANGES_ACTIVITY_REQUEST_CODE = 7;
    private static final int SEASON_DETAILS_ACTIVITY_REQUEST_CODE = 8;

    private static final String SHARED_PREFERENCES_DATA_FILE = "calcData";
    private static final String SHARED_PREFERENCES_DATA_KEY = "Calc_Data";

    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateView;

    BroadcastReceiver zUpdateReceiver;

    TextView zVal;
    TextView aVal;
    TextView bVal;
    TextView cVal;

    Spinner billingFrequencySpinner;

    Spinner billingStructureSpinner;

    Calc[] calcs;

    private int calcType; // tiered = 0, flat = 1, TOU = 2
    private double a, b, c, z;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Context context = getApplicationContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_DATA_FILE, MODE_PRIVATE);

        if (sharedPreferences.contains(SHARED_PREFERENCES_DATA_KEY)) {
            Gson gson = new Gson();
            String calcJson = sharedPreferences.getString(SHARED_PREFERENCES_DATA_KEY, "NaN");
            Log.i("Gson", calcJson);
            if (calcJson.equals("NaN")) {
                calcs = new Calc[3];
                calcs[0] = new Calc(1);
                calcs[1] = new Calc(2);
                calcs[2] = new Calc(3);
            }
            else {
                calcs = gson.fromJson(calcJson, Calc[].class);
            }
        }
        else {
            calcs = new Calc[3];
            calcs[0] = new Calc(1);
            calcs[1] = new Calc(2);
            calcs[2] = new Calc(3);
        }

        calcs[1].updateAllSeasonTiers(1);

        calcType = 0;

        // A B C AND Z VALUES TAKEN FROM STORED VALUES IN CALC FOR NOW
        // WHEN APP FINISHED, A,B,C VALUES SHOULD COME FROM SERVER EVERY SECOND
        a = calcs[0].a;
        b = calcs[0].b;
        c = calcs[0].c;
        z = calcs[0].z;


        TextView billingInfo = (TextView) findViewById(R.id.BillingInfo);
        billingInfo.setBackgroundColor(Color.parseColor("#FF5555"));
        billingInfo.setTextColor(getResources().getColor(R.color.white));

        zVal = findViewById(R.id.zTextVal);
        zVal.setText(String.format("%.04f", z));

        aVal = findViewById(R.id.aEditText);
        bVal = findViewById(R.id.bEditText);
        cVal = findViewById(R.id.cEditText);

        aVal.setText(String.format("%.04f", a));
        bVal.setText(String.format("%.04f", b));
        cVal.setText(String.format("%.04f", c));

        dateView = (TextView) findViewById(R.id.dateText);
        calendar = Calendar.getInstance();

        if (!dateExists()) {
            calcs[calcType].startYear = calendar.get(Calendar.YEAR);
            calcs[calcType].startMonth = calendar.get(Calendar.MONTH) + 1;
            calcs[calcType].startDay = calendar.get(Calendar.DAY_OF_MONTH);
        }
        showDate();


        String[] frequencyOptions = new String[] {"Monthly", "Bi-Monthly", "Quarterly"};
        ArrayAdapter<String> billingFrequencyAdapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_spinner_item,frequencyOptions);
        billingFrequencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        billingFrequencySpinner = findViewById(R.id.billingCycleSpinner);
        billingFrequencySpinner.setAdapter(billingFrequencyAdapter);
        billingFrequencySpinner.setOnItemSelectedListener(this);
        billingFrequencySpinner.setSelection(calcs[calcType].monthsPerBillingCycle - 1);

        String[] structureOptions = new String[] {"Tiered", "Flat", "Time of Use (TOU)"};
        ArrayAdapter<String> billingStructureAdapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_spinner_item,structureOptions);
        billingStructureAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        billingStructureSpinner = findViewById(R.id.billingStructureSpinner);
        billingStructureSpinner.setAdapter(billingStructureAdapter);
        billingStructureSpinner.setOnItemSelectedListener(this);
        billingStructureSpinner.setSelection(calcType);
        switchToCalcType();
    }

    public void setFixedCharges(View v) {
        Intent i = new Intent(this, SetFixedChargesActivity.class);
        i.putExtra("numSeasons", calcs[calcType].numSeasons);
        i.putExtra("s1Fixed", calcs[calcType].seasons.get(0).fixed);
        i.putExtra("s2Fixed", calcs[calcType].seasons.get(1).fixed);
        i.putExtra("s3Fixed", calcs[calcType].seasons.get(2).fixed);
        i.putExtra("s4Fixed", calcs[calcType].seasons.get(3).fixed);
        startActivityForResult(i, FIXED_CHARGES_ACTIVITY_REQUEST_CODE);
    }

    public void setRegCharges(View v) {
        Intent i = new Intent(this, SetRegulatoryChargesActivity.class);
        i.putExtra("numSeasons", calcs[calcType].numSeasons);
        i.putExtra("s1Reg", calcs[calcType].seasons.get(0).regulatory);
        i.putExtra("s2Reg", calcs[calcType].seasons.get(1).regulatory);
        i.putExtra("s3Reg", calcs[calcType].seasons.get(2).regulatory);
        i.putExtra("s4Reg", calcs[calcType].seasons.get(3).regulatory);
        startActivityForResult(i, REGULATORY_CHARGES_ACTIVITY_REQUEST_CODE);
    }

    public void setDelivery(View v) {
        Intent i = new Intent(this, SetDeliveryActivity.class);

        i.putExtra("numSeasons", calcs[calcType].numSeasons);

        i.putExtra("s1Delivery", calcs[calcType].seasons.get(0).delivery);
        i.putExtra("s2Delivery", calcs[calcType].seasons.get(1).delivery);
        i.putExtra("s3Delivery", calcs[calcType].seasons.get(2).delivery);
        i.putExtra("s4Delivery", calcs[calcType].seasons.get(3).delivery);
        startActivityForResult(i, DELIVERY_ACTIVITY_REQUEST_CODE);
    }

    public void setTaxRates(View v) {
        Intent i = new Intent(this, SetTaxRatesActivity.class);
        i.putExtra("numSeasons", calcs[calcType].numSeasons);
        i.putExtra("s1Tax", calcs[calcType].seasons.get(0).taxRate);
        i.putExtra("s2Tax", calcs[calcType].seasons.get(1).taxRate);
        i.putExtra("s3Tax", calcs[calcType].seasons.get(2).taxRate);
        i.putExtra("s4Tax", calcs[calcType].seasons.get(3).taxRate);
        startActivityForResult(i, TAX_RATES_ACTIVITY_REQUEST_CODE);
    }

    public void setUtilityRates(View v) {
        Intent i = new Intent(this, SetUtilityRatesActivity.class);
        i.putExtra("numSeasons", calcs[calcType].numSeasons);
        i.putExtra("calcType", calcType);

        i.putExtra("s1Tier", calcs[calcType].seasons.get(0).tier);
        i.putExtra("s2Tier", calcs[calcType].seasons.get(1).tier);
        i.putExtra("s3Tier", calcs[calcType].seasons.get(2).tier);
        i.putExtra("s4Tier", calcs[calcType].seasons.get(3).tier);

        i.putExtra("s1t1Util", calcs[calcType].seasons.get(0).utilRates[0]);
        i.putExtra("s1t2Util", calcs[calcType].seasons.get(0).utilRates[1]);
        i.putExtra("s1t3Util", calcs[calcType].seasons.get(0).utilRates[2]);
        i.putExtra("s1t4Util", calcs[calcType].seasons.get(0).utilRates[3]);
        i.putExtra("s2t1Util", calcs[calcType].seasons.get(1).utilRates[0]);
        i.putExtra("s2t2Util", calcs[calcType].seasons.get(1).utilRates[1]);
        i.putExtra("s2t3Util", calcs[calcType].seasons.get(1).utilRates[2]);
        i.putExtra("s2t4Util", calcs[calcType].seasons.get(1).utilRates[3]);
        i.putExtra("s3t1Util", calcs[calcType].seasons.get(2).utilRates[0]);
        i.putExtra("s3t2Util", calcs[calcType].seasons.get(2).utilRates[1]);
        i.putExtra("s3t3Util", calcs[calcType].seasons.get(2).utilRates[2]);
        i.putExtra("s3t4Util", calcs[calcType].seasons.get(2).utilRates[3]);
        i.putExtra("s4t1Util", calcs[calcType].seasons.get(3).utilRates[0]);
        i.putExtra("s4t2Util", calcs[calcType].seasons.get(3).utilRates[1]);
        i.putExtra("s4t3Util", calcs[calcType].seasons.get(3).utilRates[2]);
        i.putExtra("s4t4Util", calcs[calcType].seasons.get(3).utilRates[3]);

        startActivityForResult(i, UTILITY_RATES_ACTIVITY_REQUEST_CODE);
    }

    public void setTierRanges(View v) {
        Intent i = new Intent(this, SetTierRangesActivity.class);

        i.putExtra("numSeasons", calcs[calcType].numSeasons);

        i.putExtra("s1Tier", calcs[calcType].seasons.get(0).tier);
        i.putExtra("s2Tier", calcs[calcType].seasons.get(1).tier);
        i.putExtra("s3Tier", calcs[calcType].seasons.get(2).tier);
        i.putExtra("s4Tier", calcs[calcType].seasons.get(3).tier);

        i.putExtra("s1t1Tier", calcs[calcType].seasons.get(0).energyRange[0]);
        i.putExtra("s1t2Tier", calcs[calcType].seasons.get(0).energyRange[1]);
        i.putExtra("s1t3Tier", calcs[calcType].seasons.get(0).energyRange[2]);
        i.putExtra("s1t4Tier", calcs[calcType].seasons.get(0).energyRange[3]);
        i.putExtra("s2t1Tier", calcs[calcType].seasons.get(1).energyRange[0]);
        i.putExtra("s2t2Tier", calcs[calcType].seasons.get(1).energyRange[1]);
        i.putExtra("s2t3Tier", calcs[calcType].seasons.get(1).energyRange[2]);
        i.putExtra("s2t4Tier", calcs[calcType].seasons.get(1).energyRange[3]);
        i.putExtra("s3t1Tier", calcs[calcType].seasons.get(2).energyRange[0]);
        i.putExtra("s3t2Tier", calcs[calcType].seasons.get(2).energyRange[1]);
        i.putExtra("s3t3Tier", calcs[calcType].seasons.get(2).energyRange[2]);
        i.putExtra("s3t4Tier", calcs[calcType].seasons.get(2).energyRange[3]);
        i.putExtra("s4t1Tier", calcs[calcType].seasons.get(3).energyRange[0]);
        i.putExtra("s4t2Tier", calcs[calcType].seasons.get(3).energyRange[1]);
        i.putExtra("s4t3Tier", calcs[calcType].seasons.get(3).energyRange[2]);
        i.putExtra("s4t4Tier", calcs[calcType].seasons.get(3).energyRange[3]);

        startActivityForResult(i, TIER_RANGES_ACTIVITY_REQUEST_CODE);
    }

    public void setSeasonDetails(View v) {
        Intent i = new Intent(this, SetSeasonDetailsActivity.class);

        i.putExtra("s1Name", calcs[calcType].seasons.get(0).name);
        i.putExtra("s2Name", calcs[calcType].seasons.get(1).name);
        i.putExtra("s3Name", calcs[calcType].seasons.get(2).name);
        i.putExtra("s4Name", calcs[calcType].seasons.get(3).name);

        i.putExtra("s1StartDate", calcs[calcType].seasons.get(0).startDate);
        i.putExtra("s2StartDate", calcs[calcType].seasons.get(1).startDate);
        i.putExtra("s3StartDate", calcs[calcType].seasons.get(2).startDate);
        i.putExtra("s4StartDate", calcs[calcType].seasons.get(3).startDate);

        i.putExtra("s1EndDate", calcs[calcType].seasons.get(0).endDate);
        i.putExtra("s2EndDate", calcs[calcType].seasons.get(1).endDate);
        i.putExtra("s3EndDate", calcs[calcType].seasons.get(2).endDate);
        i.putExtra("s4EndDate", calcs[calcType].seasons.get(3).endDate);

        i.putExtra("numSeasons", calcs[calcType].numSeasons);

        startActivityForResult(i, SEASON_DETAILS_ACTIVITY_REQUEST_CODE);
    }


    public void calculateTotal() {
//        calcs[calcType].a = Double.parseDouble(aVal.toString());
//        calcs[calcType].b = Double.parseDouble(bVal.toString());
//        calcs[calcType].c = Double.parseDouble(cVal.toString());
        calcs[calcType].a = a;
        calcs[calcType].b = b;
        calcs[calcType].c = c;
        double total = calcs[calcType].calculateTotal();
        ((TextView)findViewById(R.id.total)).setText(formatTotal(total));
    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
        Toast.makeText(getApplicationContext(), "",
                Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, calcs[calcType].startYear, calcs[calcType].startMonth, calcs[calcType].startDay);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    calcs[calcType].startYear = arg1;
                    calcs[calcType].startMonth = arg2 + 1;
                    calcs[calcType].startDay = arg3;
                    showDate();
                    calculateTotal();
                }
            };

    private void showDate() {
        dateView.setText(calcs[calcType].dateToString());
    }

    private String formatTotal(double total) {
        return (new StringBuilder().append("$").append(String.format("%.02f", total)).toString());
    }

    public void startZValUpdater() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_TIME_TICK);
        zUpdateReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                a = Double.parseDouble(aVal.getText().toString());
                b = Double.parseDouble(bVal.getText().toString());
                c = Double.parseDouble(cVal.getText().toString());
                z = a + b + c;
                zVal.setText(String.format("%.04f", z));
                calculateTotal();
            }
        };
        registerReceiver(zUpdateReceiver, intentFilter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        startZValUpdater();
    }

    @Override
    protected void onPause() {
        super.onPause();
        calcs[calcType].a = Double.parseDouble(aVal.getText().toString());
        calcs[calcType].b = Double.parseDouble(bVal.getText().toString());
        calcs[calcType].c = Double.parseDouble(cVal.getText().toString());
        calcs[calcType].z = calcs[calcType].a + calcs[calcType].b + calcs[calcType].c;
        unregisterReceiver(zUpdateReceiver);
    }


    public boolean dateExists() {
        return (calcs[calcType].startYear != 0 && calcs[calcType].startDay != 0 && calcs[calcType].startMonth != 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case FIXED_CHARGES_ACTIVITY_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    calcs[calcType].seasons.get(0).fixed = data.getDoubleExtra("s1Fixed", calcs[calcType].seasons.get(0).fixed);
                    calcs[calcType].seasons.get(1).fixed = data.getDoubleExtra("s2Fixed", calcs[calcType].seasons.get(1).fixed);
                    calcs[calcType].seasons.get(2).fixed = data.getDoubleExtra("s3Fixed", calcs[calcType].seasons.get(2).fixed);
                    calcs[calcType].seasons.get(3).fixed = data.getDoubleExtra("s4Fixed", calcs[calcType].seasons.get(3).fixed);
                }
            case REGULATORY_CHARGES_ACTIVITY_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    calcs[calcType].seasons.get(0).regulatory = data.getDoubleExtra("s1Reg", calcs[calcType].seasons.get(0).regulatory);
                    calcs[calcType].seasons.get(1).regulatory = data.getDoubleExtra("s2Reg", calcs[calcType].seasons.get(1).regulatory);
                    calcs[calcType].seasons.get(2).regulatory = data.getDoubleExtra("s3Reg", calcs[calcType].seasons.get(2).regulatory);
                    calcs[calcType].seasons.get(3).regulatory = data.getDoubleExtra("s4Reg", calcs[calcType].seasons.get(3).regulatory);
                }
            case DELIVERY_ACTIVITY_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    calcs[calcType].seasons.get(0).delivery = data.getDoubleExtra("s1Delivery", calcs[calcType].seasons.get(0).delivery);
                    calcs[calcType].seasons.get(1).delivery = data.getDoubleExtra("s2Delivery", calcs[calcType].seasons.get(1).delivery);
                    calcs[calcType].seasons.get(2).delivery = data.getDoubleExtra("s3Delivery", calcs[calcType].seasons.get(2).delivery);
                    calcs[calcType].seasons.get(3).delivery = data.getDoubleExtra("s4Delivery", calcs[calcType].seasons.get(3).delivery);
                }
            case TAX_RATES_ACTIVITY_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    calcs[calcType].seasons.get(0).taxRate = data.getDoubleExtra("s1Tax", calcs[calcType].seasons.get(0).taxRate);
                    calcs[calcType].seasons.get(1).taxRate = data.getDoubleExtra("s2Tax", calcs[calcType].seasons.get(1).taxRate);
                    calcs[calcType].seasons.get(2).taxRate = data.getDoubleExtra("s3Tax", calcs[calcType].seasons.get(2).taxRate);
                    calcs[calcType].seasons.get(3).taxRate = data.getDoubleExtra("s4Tax", calcs[calcType].seasons.get(3).taxRate);
                }
            case UTILITY_RATES_ACTIVITY_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    calcs[calcType].seasons.get(0).utilRates[0] = data.getDoubleExtra("s1t1Util", calcs[calcType].seasons.get(0).utilRates[0]);
                    calcs[calcType].seasons.get(0).utilRates[1] = data.getDoubleExtra("s1t2Util", calcs[calcType].seasons.get(0).utilRates[1]);
                    calcs[calcType].seasons.get(0).utilRates[2] = data.getDoubleExtra("s1t3Util", calcs[calcType].seasons.get(0).utilRates[2]);
                    calcs[calcType].seasons.get(0).utilRates[3] = data.getDoubleExtra("s1t4Util", calcs[calcType].seasons.get(0).utilRates[3]);
                    calcs[calcType].seasons.get(1).utilRates[0] = data.getDoubleExtra("s2t1Util", calcs[calcType].seasons.get(1).utilRates[0]);
                    calcs[calcType].seasons.get(1).utilRates[1] = data.getDoubleExtra("s2t2Util", calcs[calcType].seasons.get(1).utilRates[1]);
                    calcs[calcType].seasons.get(1).utilRates[2] = data.getDoubleExtra("s2t3Util", calcs[calcType].seasons.get(1).utilRates[2]);
                    calcs[calcType].seasons.get(1).utilRates[3] = data.getDoubleExtra("s2t4Util", calcs[calcType].seasons.get(1).utilRates[3]);
                    calcs[calcType].seasons.get(2).utilRates[0] = data.getDoubleExtra("s3t1Util", calcs[calcType].seasons.get(2).utilRates[0]);
                    calcs[calcType].seasons.get(2).utilRates[1] = data.getDoubleExtra("s3t2Util", calcs[calcType].seasons.get(2).utilRates[1]);
                    calcs[calcType].seasons.get(2).utilRates[2] = data.getDoubleExtra("s3t3Util", calcs[calcType].seasons.get(2).utilRates[2]);
                    calcs[calcType].seasons.get(2).utilRates[3] = data.getDoubleExtra("s3t4Util", calcs[calcType].seasons.get(2).utilRates[3]);
                    calcs[calcType].seasons.get(3).utilRates[0] = data.getDoubleExtra("s4t1Util", calcs[calcType].seasons.get(3).utilRates[0]);
                    calcs[calcType].seasons.get(3).utilRates[1] = data.getDoubleExtra("s4t2Util", calcs[calcType].seasons.get(3).utilRates[1]);
                    calcs[calcType].seasons.get(3).utilRates[2] = data.getDoubleExtra("s4t3Util", calcs[calcType].seasons.get(3).utilRates[2]);
                    calcs[calcType].seasons.get(3).utilRates[3] = data.getDoubleExtra("s4t4Util", calcs[calcType].seasons.get(3).utilRates[3]);
                }
            case TIER_RANGES_ACTIVITY_REQUEST_CODE:
                if (resultCode == RESULT_OK) {

                    calcs[calcType].seasons.get(0).tier = data.getIntExtra("s1Tier", calcs[calcType].seasons.get(0).tier);
                    calcs[calcType].seasons.get(1).tier = data.getIntExtra("s2Tier", calcs[calcType].seasons.get(1).tier);
                    calcs[calcType].seasons.get(2).tier = data.getIntExtra("s3Tier", calcs[calcType].seasons.get(2).tier);
                    calcs[calcType].seasons.get(3).tier = data.getIntExtra("s4Tier", calcs[calcType].seasons.get(3).tier);

                    calcs[calcType].seasons.get(0).energyRange[0] = data.getDoubleExtra("s1t1Tier", calcs[calcType].seasons.get(0).energyRange[0]);
                    calcs[calcType].seasons.get(0).energyRange[1] = data.getDoubleExtra("s1t2Tier", calcs[calcType].seasons.get(0).energyRange[1]);
                    calcs[calcType].seasons.get(0).energyRange[2] = data.getDoubleExtra("s1t3Tier", calcs[calcType].seasons.get(0).energyRange[2]);
                    calcs[calcType].seasons.get(0).energyRange[3] = data.getDoubleExtra("s1t4Tier", calcs[calcType].seasons.get(0).energyRange[3]);
                    calcs[calcType].seasons.get(1).energyRange[0] = data.getDoubleExtra("s2t1Tier", calcs[calcType].seasons.get(1).energyRange[0]);
                    calcs[calcType].seasons.get(1).energyRange[1] = data.getDoubleExtra("s2t2Tier", calcs[calcType].seasons.get(1).energyRange[1]);
                    calcs[calcType].seasons.get(1).energyRange[2] = data.getDoubleExtra("s2t3Tier", calcs[calcType].seasons.get(1).energyRange[2]);
                    calcs[calcType].seasons.get(1).energyRange[3] = data.getDoubleExtra("s2t4Tier", calcs[calcType].seasons.get(1).energyRange[3]);
                    calcs[calcType].seasons.get(2).energyRange[0] = data.getDoubleExtra("s3t1Tier", calcs[calcType].seasons.get(2).energyRange[0]);
                    calcs[calcType].seasons.get(2).energyRange[1] = data.getDoubleExtra("s3t2Tier", calcs[calcType].seasons.get(2).energyRange[1]);
                    calcs[calcType].seasons.get(2).energyRange[2] = data.getDoubleExtra("s3t3Tier", calcs[calcType].seasons.get(2).energyRange[2]);
                    calcs[calcType].seasons.get(2).energyRange[3] = data.getDoubleExtra("s3t4Tier", calcs[calcType].seasons.get(2).energyRange[3]);
                    calcs[calcType].seasons.get(3).energyRange[0] = data.getDoubleExtra("s4t1Tier", calcs[calcType].seasons.get(3).energyRange[0]);
                    calcs[calcType].seasons.get(3).energyRange[1] = data.getDoubleExtra("s4t2Tier", calcs[calcType].seasons.get(3).energyRange[1]);
                    calcs[calcType].seasons.get(3).energyRange[2] = data.getDoubleExtra("s4t3Tier", calcs[calcType].seasons.get(3).energyRange[2]);
                    calcs[calcType].seasons.get(3).energyRange[3] = data.getDoubleExtra("s4t4Tier", calcs[calcType].seasons.get(3).energyRange[3]);
                }
            case SEASON_DETAILS_ACTIVITY_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    calcs[calcType].numSeasons = data.getIntExtra("numSeasons", calcs[calcType].numSeasons);

                    calcs[calcType].seasons.get(0).name = data.getStringExtra("s1Name");
                    calcs[calcType].seasons.get(1).name = data.getStringExtra("s2Name");
                    calcs[calcType].seasons.get(2).name = data.getStringExtra("s3Name");
                    calcs[calcType].seasons.get(3).name = data.getStringExtra("s4Name");

                    calcs[calcType].seasons.get(0).startDate[0] = data.getIntExtra("s1StartDateMonth", calcs[calcType].seasons.get(0).startDate[0]);
                    calcs[calcType].seasons.get(1).startDate[0] = data.getIntExtra("s2StartDateMonth", calcs[calcType].seasons.get(1).startDate[0]);
                    calcs[calcType].seasons.get(2).startDate[0] = data.getIntExtra("s3StartDateMonth", calcs[calcType].seasons.get(2).startDate[0]);
                    calcs[calcType].seasons.get(3).startDate[0] = data.getIntExtra("s4StartDateMonth", calcs[calcType].seasons.get(3).startDate[0]);

                    calcs[calcType].seasons.get(0).endDate[0] = data.getIntExtra("s1EndDateMonth", calcs[calcType].seasons.get(0).endDate[0]);
                    calcs[calcType].seasons.get(1).endDate[0] = data.getIntExtra("s2EndDateMonth", calcs[calcType].seasons.get(1).endDate[0]);
                    calcs[calcType].seasons.get(2).endDate[0] = data.getIntExtra("s3EndDateMonth", calcs[calcType].seasons.get(2).endDate[0]);
                    calcs[calcType].seasons.get(3).endDate[0] = data.getIntExtra("s4EndDateMonth", calcs[calcType].seasons.get(3).endDate[0]);

                    calcs[calcType].seasons.get(0).startDate[1] = data.getIntExtra("s1StartDateDay", calcs[calcType].seasons.get(0).startDate[1]);
                    calcs[calcType].seasons.get(1).startDate[1] = data.getIntExtra("s2StartDateDay", calcs[calcType].seasons.get(1).startDate[1]);
                    calcs[calcType].seasons.get(2).startDate[1] = data.getIntExtra("s3StartDateDay", calcs[calcType].seasons.get(2).startDate[1]);
                    calcs[calcType].seasons.get(3).startDate[1] = data.getIntExtra("s4StartDateDay", calcs[calcType].seasons.get(3).startDate[1]);

                    calcs[calcType].seasons.get(0).endDate[1] = data.getIntExtra("s1EndDateDay", calcs[calcType].seasons.get(0).endDate[1]);
                    calcs[calcType].seasons.get(1).endDate[1] = data.getIntExtra("s2EndDateDay", calcs[calcType].seasons.get(1).endDate[1]);
                    calcs[calcType].seasons.get(2).endDate[1] = data.getIntExtra("s3EndDateDay", calcs[calcType].seasons.get(2).endDate[1]);
                    calcs[calcType].seasons.get(3).endDate[1] = data.getIntExtra("s4EndDateDay", calcs[calcType].seasons.get(3).endDate[1]);
                }
        }
        calcs[calcType].z = calcs[calcType].a + calcs[calcType].b + calcs[calcType].c;
        aVal.setText(String.format("%.04f", calcs[calcType].a));
        bVal.setText(String.format("%.04f", calcs[calcType].b));
        cVal.setText(String.format("%.04f", calcs[calcType].c));
        zVal.setText(String.format("%.04f", calcs[calcType].z));

        calculateTotal();
    }

    public void saveCurrentData(View v) {
        calcs[0].a = Double.parseDouble(aVal.getText().toString());
        calcs[0].b = Double.parseDouble(bVal.getText().toString());
        calcs[0].c = Double.parseDouble(cVal.getText().toString());
        calcs[0].z = calcs[0].a + calcs[0].b + calcs[0].c;

        calcs[calcType].monthsPerBillingCycle = billingFrequencySpinner.getSelectedItemPosition() + 1;

        Gson gson = new Gson();
        String dataJson = gson.toJson(calcs);
        Context context = getApplicationContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_DATA_FILE, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SHARED_PREFERENCES_DATA_KEY, dataJson);
        editor.commit();
        Toast.makeText(context, "Data has been saved", Toast.LENGTH_SHORT).show();
    }

    public void clearSavedData(View v) {
        Context context = getApplicationContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_DATA_FILE, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(SHARED_PREFERENCES_DATA_KEY);
        editor.commit();
        Toast.makeText(context, "Saved Data has been cleared", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch(parent.getId()) {
            case R.id.billingCycleSpinner:
                calcs[calcType].monthsPerBillingCycle = billingFrequencySpinner.getSelectedItemPosition() + 1;
                break;
            case R.id.billingStructureSpinner:
                calcType = parent.getSelectedItemPosition();
                switchToCalcType();
                break;
        }
        calculateTotal();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

    public void switchToCalcType() {
        switch (calcType) {
            case 0:
                findViewById(R.id.ToSetTierRanges).setVisibility(View.VISIBLE);
                break;
            case 1:
                findViewById(R.id.ToSetTierRanges).setVisibility(View.GONE);
            case 2:
                findViewById(R.id.ToSetTierRanges).setVisibility(View.GONE);
        }
    }

}