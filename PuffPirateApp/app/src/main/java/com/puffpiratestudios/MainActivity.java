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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;

import com.puffpiratestudios.algos.*;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private static final int FIXED_CHARGES_ACTIVITY_REQUEST_CODE = 2;
    private static final int REGULATORY_CHARGES_ACTIVITY_REQUEST_CODE = 3;
    private static final int DELIVERY_ACTIVITY_REQUEST_CODE = 4;
    private static final int TAX_RATES_ACTIVITY_REQUEST_CODE = 5;
    private static final int UTILITY_RATES_ACTIVITY_REQUEST_CODE = 6;
    private static final int TIER_RANGES_ACTIVITY_REQUEST_CODE = 7;
    private static final int SEASON_DETAILS_ACTIVITY_REQUEST_CODE = 8;
    private static final int TOU_ZONES_ACTIVITY_REQUEST_CODE = 9;
    private static final int HOLIDAY_DETAILS_ACTIVITY_REQUEST_CODE = 10;
    private static final int TOU_DETAILS_ACTIVITY_REQUEST_CODE = 11;

    private static final String SHARED_PREFERENCES_DATA_FILE = "calcData";

    private static final String SHARED_PREFERENCES_DATA_KEY_TIER = "Calc_Data_Tier";
    private static final String SHARED_PREFERENCES_DATA_KEY_FLAT = "Calc_Data_Flat";
    private static final String SHARED_PREFERENCES_DATA_KEY_TOU = "Calc_Data_TOU";

    private DatePicker datePicker;
    private Calendar calendar;

    BroadcastReceiver zUpdateReceiver;

    TextView zVal;
    EditText aVal;
    EditText bVal;
    EditText cVal;

    Spinner billingFrequencySpinner;

    Spinner billingStructureSpinner;

    Calc[] calcs = new Calc[3];

    private int calcType; // tiered = 0, flat = 1, TOU = 2
    private double a, b, c, z;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadData();

        calcType = 0;

        // A B C AND Z VALUES TAKEN FROM STORED VALUES IN CALC FOR NOW
        // WHEN APP FINISHED, A,B,C VALUES SHOULD COME FROM SERVER EVERY SECOND
        a = calcs[0].a;
        b = calcs[0].b;
        c = calcs[0].c;
        z = calcs[0].z;


        // FORMAT BILLING INFO TITLE
        TextView billingInfo = (TextView) findViewById(R.id.BillingInfo);
        billingInfo.setBackgroundColor(Color.parseColor("#FF5555"));
        billingInfo.setTextColor(getResources().getColor(R.color.white));


        // SET a,b,c,z VALUES TO EXISTING VALUES AND FORMAT
        zVal = findViewById(R.id.zTextVal);
        zVal.setText(String.format("%.04f", z));

        aVal = findViewById(R.id.aEditText);
        bVal = findViewById(R.id.bEditText);
        cVal = findViewById(R.id.cEditText);

        aVal.setText(String.format("%.04f", a));
        bVal.setText(String.format("%.04f", b));
        cVal.setText(String.format("%.04f", c));


        // CREATE CALENDAR OF DAY
        calendar = Calendar.getInstance();

        // FOR EACH CALCULATOR, IF BILLING START DATE NOT ALREADY SET, SET THE DATE TO CURRENT DATE
        for (int x = 0; x < 3; x++) {
            if (!dateExists(x)) {
                calcs[calcType].startYear = calendar.get(Calendar.YEAR);
                calcs[calcType].startMonth = calendar.get(Calendar.MONTH) + 1;
                calcs[calcType].startDay = calendar.get(Calendar.DAY_OF_MONTH);
            }
        }

        // SET BUTTON TEXT TO SHOW BILLING CYCLE START DATE
        showDate();


        // CREATE BILLING CYCLE LENGTH SPINNER AND ADAPTER
        String[] frequencyOptions = new String[] {"Monthly", "Bi-Monthly", "Quarterly"};
        ArrayAdapter<String> billingFrequencyAdapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_spinner_item,frequencyOptions);
        billingFrequencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        billingFrequencySpinner = findViewById(R.id.billingCycleSpinner);
        billingFrequencySpinner.setAdapter(billingFrequencyAdapter);
        billingFrequencySpinner.setOnItemSelectedListener(this);
        billingFrequencySpinner.setSelection(calcs[calcType].monthsPerBillingCycle - 1);


        // CREATE BILLING STRUCTURE SPINNER AND ADAPTER
        String[] structureOptions = new String[] {"Tiered", "Flat", "Time of Use (TOU)"};
        ArrayAdapter<String> billingStructureAdapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_spinner_item,structureOptions);
        billingStructureAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        billingStructureSpinner = findViewById(R.id.billingStructureSpinner);
        billingStructureSpinner.setAdapter(billingStructureAdapter);
        billingStructureSpinner.setOnItemSelectedListener(this);
        billingStructureSpinner.setSelection(calcType);

        // MATCH MAIN LAYOUT WITH THE RESPECTIVE CALCULATOR TYPE
        switchToCalcType();
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



    /*
    ***** FUNCTIONS TO START RESPECTIVE ACTIVITIES, CALLED BY BUTTON CLICKS *****
    */

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

    public void setZoneDetails(View v) {
        Intent i = new Intent(this, SetTOUZonesActivity.class);
        i.putExtra("numSeasons", calcs[calcType].numSeasons);

        i.putExtra("numZones", ((TouCalc)calcs[2]).numZones);

        i.putExtra("z1Name", ((TouCalc)calcs[2]).zoneNames[0]);
        i.putExtra("z2Name", ((TouCalc)calcs[2]).zoneNames[1]);
        i.putExtra("z3Name", ((TouCalc)calcs[2]).zoneNames[2]);

        i.putExtra("s1z1Rate", calcs[calcType].seasons.get(0).zones[0].rate);
        i.putExtra("s2z1Rate", calcs[calcType].seasons.get(1).zones[0].rate);
        i.putExtra("s3z1Rate", calcs[calcType].seasons.get(2).zones[0].rate);
        i.putExtra("s4z1Rate", calcs[calcType].seasons.get(3).zones[0].rate);

        i.putExtra("s1z2Rate", calcs[calcType].seasons.get(0).zones[1].rate);
        i.putExtra("s2z2Rate", calcs[calcType].seasons.get(1).zones[1].rate);
        i.putExtra("s3z2Rate", calcs[calcType].seasons.get(2).zones[1].rate);
        i.putExtra("s4z2Rate", calcs[calcType].seasons.get(3).zones[1].rate);

        i.putExtra("s1z3Rate", calcs[calcType].seasons.get(0).zones[2].rate);
        i.putExtra("s2z3Rate", calcs[calcType].seasons.get(1).zones[2].rate);
        i.putExtra("s3z3Rate", calcs[calcType].seasons.get(2).zones[2].rate);
        i.putExtra("s4z3Rate", calcs[calcType].seasons.get(3).zones[2].rate);

        i.putExtra("s1OutRate", calcs[calcType].seasons.get(0).zones[3].rate);
        i.putExtra("s2OutRate", calcs[calcType].seasons.get(1).zones[3].rate);
        i.putExtra("s3OutRate", calcs[calcType].seasons.get(2).zones[3].rate);
        i.putExtra("s4OutRate", calcs[calcType].seasons.get(3).zones[3].rate);

        i.putExtra("s1_z1_from_hour", calcs[calcType].seasons.get(0).zones[0].fromHour);
        i.putExtra("s1_z1_from_min", calcs[calcType].seasons.get(0).zones[0].fromMinute);
        i.putExtra("s1_z2_from_hour", calcs[calcType].seasons.get(0).zones[1].fromHour);
        i.putExtra("s1_z2_from_min", calcs[calcType].seasons.get(0).zones[1].fromMinute);
        i.putExtra("s1_z3_from_hour", calcs[calcType].seasons.get(0).zones[2].fromHour);
        i.putExtra("s1_z3_from_min", calcs[calcType].seasons.get(0).zones[2].fromMinute);
        i.putExtra("s1_out_from_hour", calcs[calcType].seasons.get(0).zones[3].fromHour);
        i.putExtra("s1_out_from_min", calcs[calcType].seasons.get(0).zones[3].fromMinute);

        i.putExtra("s2_z1_from_hour", calcs[calcType].seasons.get(1).zones[0].fromHour);
        i.putExtra("s2_z1_from_min", calcs[calcType].seasons.get(1).zones[0].fromMinute);
        i.putExtra("s2_z2_from_hour", calcs[calcType].seasons.get(1).zones[1].fromHour);
        i.putExtra("s2_z2_from_min", calcs[calcType].seasons.get(1).zones[1].fromMinute);
        i.putExtra("s2_z3_from_hour", calcs[calcType].seasons.get(1).zones[2].fromHour);
        i.putExtra("s2_z3_from_min", calcs[calcType].seasons.get(1).zones[2].fromMinute);
        i.putExtra("s2_out_from_hour", calcs[calcType].seasons.get(1).zones[3].fromHour);
        i.putExtra("s2_out_from_min", calcs[calcType].seasons.get(1).zones[3].fromMinute);

        i.putExtra("s3_z1_from_hour", calcs[calcType].seasons.get(2).zones[0].fromHour);
        i.putExtra("s3_z1_from_min", calcs[calcType].seasons.get(2).zones[0].fromMinute);
        i.putExtra("s3_z2_from_hour", calcs[calcType].seasons.get(2).zones[1].fromHour);
        i.putExtra("s3_z2_from_min", calcs[calcType].seasons.get(2).zones[1].fromMinute);
        i.putExtra("s3_z3_from_hour", calcs[calcType].seasons.get(2).zones[2].fromHour);
        i.putExtra("s3_z3_from_min", calcs[calcType].seasons.get(2).zones[2].fromMinute);
        i.putExtra("s3_out_from_hour", calcs[calcType].seasons.get(2).zones[3].fromHour);
        i.putExtra("s3_out_from_min", calcs[calcType].seasons.get(2).zones[3].fromMinute);

        i.putExtra("s4_z1_from_hour", calcs[calcType].seasons.get(3).zones[0].fromHour);
        i.putExtra("s4_z1_from_min", calcs[calcType].seasons.get(3).zones[0].fromMinute);
        i.putExtra("s4_z2_from_hour", calcs[calcType].seasons.get(3).zones[1].fromHour);
        i.putExtra("s4_z2_from_min", calcs[calcType].seasons.get(3).zones[1].fromMinute);
        i.putExtra("s4_z3_from_hour", calcs[calcType].seasons.get(3).zones[2].fromHour);
        i.putExtra("s4_z3_from_min", calcs[calcType].seasons.get(3).zones[2].fromMinute);
        i.putExtra("s4_out_from_hour", calcs[calcType].seasons.get(3).zones[3].fromHour);
        i.putExtra("s4_out_from_min", calcs[calcType].seasons.get(3).zones[3].fromMinute);

        startActivityForResult(i, TOU_ZONES_ACTIVITY_REQUEST_CODE);
    }

    public void setHolidayDetails(View v) {
        Intent i = new Intent(this, SetHolidaysActivity.class);

        i.putExtra("country", ((TouCalc)calcs[2]).country);

        i.putExtra("datesUSA", ((TouCalc)calcs[2]).getHolidayDatesArray(0));
        i.putExtra("datesCA", ((TouCalc)calcs[2]).getHolidayDatesArray(1));

        i.putExtra("holidayNamesUSA", ((TouCalc)calcs[2]).holidayNames[0]);
        i.putExtra("holidayNamesCA", ((TouCalc)calcs[2]).holidayNames[1]);

        startActivityForResult(i, HOLIDAY_DETAILS_ACTIVITY_REQUEST_CODE);
    }

    public void setTouDetails(View v) {
        Intent i = new Intent(this, SetTouDetailsActivity.class);

        i.putExtra("saturday", ((TouCalc)calcs[2]).saturdaysOff);
        i.putExtra("sunday", ((TouCalc)calcs[2]).sundaysOff);
        i.putExtra("holiday", ((TouCalc)calcs[2]).holidaysOff);

        i.putExtra("country", ((TouCalc)calcs[2]).country);

        i.putExtra("z1pct", ((TouCalc)calcs[2]).pctInZones[0]);
        i.putExtra("z2pct", ((TouCalc)calcs[2]).pctInZones[1]);
        i.putExtra("z3pct", ((TouCalc)calcs[2]).pctInZones[2]);
        i.putExtra("zOutpct", ((TouCalc)calcs[2]).pctInZones[3]);

        i.putExtra("numZones", ((TouCalc)calcs[2]).numZones);

        startActivityForResult(i, TOU_DETAILS_ACTIVITY_REQUEST_CODE);
    }


    /*
    ***** CALCULATING AND DISPLAYING TOTAL COST RELATED *****
     */

    public void calculateTotal() {
        calcs[calcType].a = a;
        calcs[calcType].b = b;
        calcs[calcType].c = c;
        double total = calcs[calcType].calculateTotal();
        ((TextView)findViewById(R.id.total)).setText(formatTotal(total));
    }

    private String formatTotal(double total) {
        return (new StringBuilder().append("$").append(String.format("%.02f", total)).toString());
    }



    /*
    ***** BILLING START DATE RELATED *****
     */
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
        ((Button)findViewById(R.id.setDateButton)).setText(calcs[calcType].dateToString());
    }

    public boolean dateExists(int type) {
        return (calcs[type].startYear != 0 && calcs[type].startDay != 0 && calcs[type].startMonth != 0);
    }


    /*
    ***** FUNCTIONS FOR a,b,c,z VALUES *****
     */

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




    /*
    ***** RETRIEVING DATA FROM ACTIVITIES *****
     */

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
                break;
            case REGULATORY_CHARGES_ACTIVITY_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    calcs[calcType].seasons.get(0).regulatory = data.getDoubleExtra("s1Reg", calcs[calcType].seasons.get(0).regulatory);
                    calcs[calcType].seasons.get(1).regulatory = data.getDoubleExtra("s2Reg", calcs[calcType].seasons.get(1).regulatory);
                    calcs[calcType].seasons.get(2).regulatory = data.getDoubleExtra("s3Reg", calcs[calcType].seasons.get(2).regulatory);
                    calcs[calcType].seasons.get(3).regulatory = data.getDoubleExtra("s4Reg", calcs[calcType].seasons.get(3).regulatory);
                }
                break;
            case DELIVERY_ACTIVITY_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    calcs[calcType].seasons.get(0).delivery = data.getDoubleExtra("s1Delivery", calcs[calcType].seasons.get(0).delivery);
                    calcs[calcType].seasons.get(1).delivery = data.getDoubleExtra("s2Delivery", calcs[calcType].seasons.get(1).delivery);
                    calcs[calcType].seasons.get(2).delivery = data.getDoubleExtra("s3Delivery", calcs[calcType].seasons.get(2).delivery);
                    calcs[calcType].seasons.get(3).delivery = data.getDoubleExtra("s4Delivery", calcs[calcType].seasons.get(3).delivery);
                }
                break;
            case TAX_RATES_ACTIVITY_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    calcs[calcType].seasons.get(0).taxRate = data.getDoubleExtra("s1Tax", calcs[calcType].seasons.get(0).taxRate);
                    calcs[calcType].seasons.get(1).taxRate = data.getDoubleExtra("s2Tax", calcs[calcType].seasons.get(1).taxRate);
                    calcs[calcType].seasons.get(2).taxRate = data.getDoubleExtra("s3Tax", calcs[calcType].seasons.get(2).taxRate);
                    calcs[calcType].seasons.get(3).taxRate = data.getDoubleExtra("s4Tax", calcs[calcType].seasons.get(3).taxRate);
                }
                break;
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
                break;
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
                break;
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
                break;
            case TOU_ZONES_ACTIVITY_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    ((TouCalc)calcs[2]).numZones = data.getIntExtra("numZones", ((TouCalc)calcs[2]).numZones);

                    ((TouCalc)calcs[2]).zoneNames[0] = data.getStringExtra("z1Name");
                    ((TouCalc)calcs[2]).zoneNames[1] = data.getStringExtra("z2Name");
                    ((TouCalc)calcs[2]).zoneNames[2] = data.getStringExtra("z3Name");

                    calcs[calcType].seasons.get(0).zones[0].rate = data.getDoubleExtra("s1z1Rate", calcs[calcType].seasons.get(0).zones[0].rate);
                    calcs[calcType].seasons.get(1).zones[0].rate = data.getDoubleExtra("s2z1Rate", calcs[calcType].seasons.get(1).zones[0].rate);
                    calcs[calcType].seasons.get(2).zones[0].rate = data.getDoubleExtra("s3z1Rate", calcs[calcType].seasons.get(2).zones[0].rate);
                    calcs[calcType].seasons.get(3).zones[0].rate = data.getDoubleExtra("s4z1Rate", calcs[calcType].seasons.get(3).zones[0].rate);

                    calcs[calcType].seasons.get(0).zones[1].rate = data.getDoubleExtra("s1z2Rate", calcs[calcType].seasons.get(0).zones[1].rate);
                    calcs[calcType].seasons.get(1).zones[1].rate = data.getDoubleExtra("s2z2Rate", calcs[calcType].seasons.get(1).zones[1].rate);
                    calcs[calcType].seasons.get(2).zones[1].rate = data.getDoubleExtra("s3z2Rate", calcs[calcType].seasons.get(2).zones[1].rate);
                    calcs[calcType].seasons.get(3).zones[1].rate = data.getDoubleExtra("s4z2Rate", calcs[calcType].seasons.get(3).zones[1].rate);

                    calcs[calcType].seasons.get(0).zones[2].rate = data.getDoubleExtra("s1z3Rate", calcs[calcType].seasons.get(0).zones[2].rate);
                    calcs[calcType].seasons.get(1).zones[2].rate = data.getDoubleExtra("s2z3Rate", calcs[calcType].seasons.get(1).zones[2].rate);
                    calcs[calcType].seasons.get(2).zones[2].rate = data.getDoubleExtra("s3z3Rate", calcs[calcType].seasons.get(2).zones[2].rate);
                    calcs[calcType].seasons.get(3).zones[2].rate = data.getDoubleExtra("s4z3Rate", calcs[calcType].seasons.get(3).zones[2].rate);

                    calcs[calcType].seasons.get(0).zones[3].rate = data.getDoubleExtra("s1OutRate", calcs[calcType].seasons.get(0).zones[3].rate);
                    calcs[calcType].seasons.get(1).zones[3].rate = data.getDoubleExtra("s2OutRate", calcs[calcType].seasons.get(1).zones[3].rate);
                    calcs[calcType].seasons.get(2).zones[3].rate = data.getDoubleExtra("s3OutRate", calcs[calcType].seasons.get(2).zones[3].rate);
                    calcs[calcType].seasons.get(3).zones[3].rate = data.getDoubleExtra("s4OutRate", calcs[calcType].seasons.get(3).zones[3].rate);

                    calcs[calcType].seasons.get(0).zones[0].fromHour = data.getIntExtra("s1z1FromHour", calcs[calcType].seasons.get(0).zones[0].fromHour);
                    calcs[calcType].seasons.get(1).zones[0].fromHour = data.getIntExtra("s2z1FromHour", calcs[calcType].seasons.get(1).zones[0].fromHour);
                    calcs[calcType].seasons.get(2).zones[0].fromHour = data.getIntExtra("s3z1FromHour", calcs[calcType].seasons.get(2).zones[0].fromHour);
                    calcs[calcType].seasons.get(3).zones[0].fromHour = data.getIntExtra("s4z1FromHour", calcs[calcType].seasons.get(3).zones[0].fromHour);
                    calcs[calcType].seasons.get(0).zones[0].fromMinute = data.getIntExtra("s1z1FromMin", calcs[calcType].seasons.get(0).zones[0].fromMinute);
                    calcs[calcType].seasons.get(1).zones[0].fromMinute = data.getIntExtra("s2z1FromMin", calcs[calcType].seasons.get(1).zones[0].fromMinute);
                    calcs[calcType].seasons.get(2).zones[0].fromMinute = data.getIntExtra("s3z1FromMin", calcs[calcType].seasons.get(2).zones[0].fromMinute);
                    calcs[calcType].seasons.get(3).zones[0].fromMinute = data.getIntExtra("s4z1FromMin", calcs[calcType].seasons.get(3).zones[0].fromMinute);

                    calcs[calcType].seasons.get(0).zones[1].fromHour = data.getIntExtra("s1z2FromHour", calcs[calcType].seasons.get(0).zones[1].fromHour);
                    calcs[calcType].seasons.get(1).zones[1].fromHour = data.getIntExtra("s2z2FromHour", calcs[calcType].seasons.get(1).zones[1].fromHour);
                    calcs[calcType].seasons.get(2).zones[1].fromHour = data.getIntExtra("s3z2FromHour", calcs[calcType].seasons.get(2).zones[1].fromHour);
                    calcs[calcType].seasons.get(3).zones[1].fromHour = data.getIntExtra("s4z2FromHour", calcs[calcType].seasons.get(3).zones[1].fromHour);
                    calcs[calcType].seasons.get(0).zones[1].fromMinute = data.getIntExtra("s1z2FromMin", calcs[calcType].seasons.get(0).zones[1].fromMinute);
                    calcs[calcType].seasons.get(1).zones[1].fromMinute = data.getIntExtra("s2z2FromMin", calcs[calcType].seasons.get(1).zones[1].fromMinute);
                    calcs[calcType].seasons.get(2).zones[1].fromMinute = data.getIntExtra("s3z2FromMin", calcs[calcType].seasons.get(2).zones[1].fromMinute);
                    calcs[calcType].seasons.get(3).zones[1].fromMinute = data.getIntExtra("s4z2FromMin", calcs[calcType].seasons.get(3).zones[1].fromMinute);

                    calcs[calcType].seasons.get(0).zones[2].fromHour = data.getIntExtra("s1z3FromHour", calcs[calcType].seasons.get(0).zones[2].fromHour);
                    calcs[calcType].seasons.get(1).zones[2].fromHour = data.getIntExtra("s2z3FromHour", calcs[calcType].seasons.get(1).zones[2].fromHour);
                    calcs[calcType].seasons.get(2).zones[2].fromHour = data.getIntExtra("s3z3FromHour", calcs[calcType].seasons.get(2).zones[2].fromHour);
                    calcs[calcType].seasons.get(3).zones[2].fromHour = data.getIntExtra("s4z3FromHour", calcs[calcType].seasons.get(3).zones[2].fromHour);
                    calcs[calcType].seasons.get(0).zones[2].fromMinute = data.getIntExtra("s1z3FromMin", calcs[calcType].seasons.get(0).zones[2].fromMinute);
                    calcs[calcType].seasons.get(1).zones[2].fromMinute = data.getIntExtra("s2z3FromMin", calcs[calcType].seasons.get(1).zones[2].fromMinute);
                    calcs[calcType].seasons.get(2).zones[2].fromMinute = data.getIntExtra("s3z3FromMin", calcs[calcType].seasons.get(2).zones[2].fromMinute);
                    calcs[calcType].seasons.get(3).zones[2].fromMinute = data.getIntExtra("s4z3FromMin", calcs[calcType].seasons.get(3).zones[2].fromMinute);

                    calcs[calcType].seasons.get(0).zones[3].fromHour = data.getIntExtra("s1OutFromHour", calcs[calcType].seasons.get(0).zones[3].fromHour);
                    calcs[calcType].seasons.get(1).zones[3].fromHour = data.getIntExtra("s2OutFromHour", calcs[calcType].seasons.get(1).zones[3].fromHour);
                    calcs[calcType].seasons.get(2).zones[3].fromHour = data.getIntExtra("s3OutFromHour", calcs[calcType].seasons.get(2).zones[3].fromHour);
                    calcs[calcType].seasons.get(3).zones[3].fromHour = data.getIntExtra("s4OutFromHour", calcs[calcType].seasons.get(3).zones[3].fromHour);
                    calcs[calcType].seasons.get(0).zones[3].fromMinute = data.getIntExtra("s1OutFromMin", calcs[calcType].seasons.get(0).zones[3].fromMinute);
                    calcs[calcType].seasons.get(1).zones[3].fromMinute = data.getIntExtra("s2OutFromMin", calcs[calcType].seasons.get(1).zones[3].fromMinute);
                    calcs[calcType].seasons.get(2).zones[3].fromMinute = data.getIntExtra("s3OutFromMin", calcs[calcType].seasons.get(2).zones[3].fromMinute);
                    calcs[calcType].seasons.get(3).zones[3].fromMinute = data.getIntExtra("s4OutFromMin", calcs[calcType].seasons.get(3).zones[3].fromMinute);


                    calcs[calcType].seasons.get(0).zones[0].toHour = data.getIntExtra("s1z1ToHour", calcs[calcType].seasons.get(0).zones[0].toHour);
                    calcs[calcType].seasons.get(1).zones[0].toHour = data.getIntExtra("s2z1ToHour", calcs[calcType].seasons.get(1).zones[0].toHour);
                    calcs[calcType].seasons.get(2).zones[0].toHour = data.getIntExtra("s3z1ToHour", calcs[calcType].seasons.get(2).zones[0].toHour);
                    calcs[calcType].seasons.get(3).zones[0].toHour = data.getIntExtra("s4z1ToHour", calcs[calcType].seasons.get(3).zones[0].toHour);
                    calcs[calcType].seasons.get(0).zones[0].toMinute = data.getIntExtra("s1z1ToMin", calcs[calcType].seasons.get(0).zones[0].toMinute);
                    calcs[calcType].seasons.get(1).zones[0].toMinute = data.getIntExtra("s2z1ToMin", calcs[calcType].seasons.get(1).zones[0].toMinute);
                    calcs[calcType].seasons.get(2).zones[0].toMinute = data.getIntExtra("s3z1ToMin", calcs[calcType].seasons.get(2).zones[0].toMinute);
                    calcs[calcType].seasons.get(3).zones[0].toMinute = data.getIntExtra("s4z1ToMin", calcs[calcType].seasons.get(3).zones[0].toMinute);

                    calcs[calcType].seasons.get(0).zones[1].toHour = data.getIntExtra("s1z2ToHour", calcs[calcType].seasons.get(0).zones[1].toHour);
                    calcs[calcType].seasons.get(1).zones[1].toHour = data.getIntExtra("s2z2ToHour", calcs[calcType].seasons.get(1).zones[1].toHour);
                    calcs[calcType].seasons.get(2).zones[1].toHour = data.getIntExtra("s3z2ToHour", calcs[calcType].seasons.get(2).zones[1].toHour);
                    calcs[calcType].seasons.get(3).zones[1].toHour = data.getIntExtra("s4z2ToHour", calcs[calcType].seasons.get(3).zones[1].toHour);
                    calcs[calcType].seasons.get(0).zones[1].toMinute = data.getIntExtra("s1z2ToMin", calcs[calcType].seasons.get(0).zones[1].toMinute);
                    calcs[calcType].seasons.get(1).zones[1].toMinute = data.getIntExtra("s2z2ToMin", calcs[calcType].seasons.get(1).zones[1].toMinute);
                    calcs[calcType].seasons.get(2).zones[1].toMinute = data.getIntExtra("s3z2ToMin", calcs[calcType].seasons.get(2).zones[1].toMinute);
                    calcs[calcType].seasons.get(3).zones[1].toMinute = data.getIntExtra("s4z2ToMin", calcs[calcType].seasons.get(3).zones[1].toMinute);

                    calcs[calcType].seasons.get(0).zones[2].toHour = data.getIntExtra("s1z3ToHour", calcs[calcType].seasons.get(0).zones[2].toHour);
                    calcs[calcType].seasons.get(1).zones[2].toHour = data.getIntExtra("s2z3ToHour", calcs[calcType].seasons.get(1).zones[2].toHour);
                    calcs[calcType].seasons.get(2).zones[2].toHour = data.getIntExtra("s3z3ToHour", calcs[calcType].seasons.get(2).zones[2].toHour);
                    calcs[calcType].seasons.get(3).zones[2].toHour = data.getIntExtra("s4z3ToHour", calcs[calcType].seasons.get(3).zones[2].toHour);
                    calcs[calcType].seasons.get(0).zones[2].toMinute = data.getIntExtra("s1z3ToMin", calcs[calcType].seasons.get(0).zones[2].toMinute);
                    calcs[calcType].seasons.get(1).zones[2].toMinute = data.getIntExtra("s2z3ToMin", calcs[calcType].seasons.get(1).zones[2].toMinute);
                    calcs[calcType].seasons.get(2).zones[2].toMinute = data.getIntExtra("s3z3ToMin", calcs[calcType].seasons.get(2).zones[2].toMinute);
                    calcs[calcType].seasons.get(3).zones[2].toMinute = data.getIntExtra("s4z3ToMin", calcs[calcType].seasons.get(3).zones[2].toMinute);

                    calcs[calcType].seasons.get(0).zones[3].toHour = data.getIntExtra("s1OutToHour", calcs[calcType].seasons.get(0).zones[3].toHour);
                    calcs[calcType].seasons.get(1).zones[3].toHour = data.getIntExtra("s2OutToHour", calcs[calcType].seasons.get(1).zones[3].toHour);
                    calcs[calcType].seasons.get(2).zones[3].toHour = data.getIntExtra("s3OutToHour", calcs[calcType].seasons.get(2).zones[3].toHour);
                    calcs[calcType].seasons.get(3).zones[3].toHour = data.getIntExtra("s4OutToHour", calcs[calcType].seasons.get(3).zones[3].toHour);
                    calcs[calcType].seasons.get(0).zones[3].toMinute = data.getIntExtra("s1OutToMin", calcs[calcType].seasons.get(0).zones[3].toMinute);
                    calcs[calcType].seasons.get(1).zones[3].toMinute = data.getIntExtra("s2OutToMin", calcs[calcType].seasons.get(1).zones[3].toMinute);
                    calcs[calcType].seasons.get(2).zones[3].toMinute = data.getIntExtra("s3OutToMin", calcs[calcType].seasons.get(2).zones[3].toMinute);
                    calcs[calcType].seasons.get(3).zones[3].toMinute = data.getIntExtra("s4OutToMin", calcs[calcType].seasons.get(3).zones[3].toMinute);
                }
                break;
            case HOLIDAY_DETAILS_ACTIVITY_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    ((TouCalc)calcs[2]).updateHolidayNames(data.getStringArrayListExtra("holidayNamesUSA"), 0);
                    ((TouCalc)calcs[2]).updateHolidayNames(data.getStringArrayListExtra("holidayNamesCA"), 1);
                    ((TouCalc)calcs[2]).updateHolidayDates(data.getLongArrayExtra("datesUSA"), 0);
                    ((TouCalc)calcs[2]).updateHolidayDates(data.getLongArrayExtra("datesCA"), 1);
                }
                break;
            case TOU_DETAILS_ACTIVITY_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    ((TouCalc)calcs[2]).saturdaysOff = data.getBooleanExtra("saturday", false);
                    ((TouCalc)calcs[2]).sundaysOff = data.getBooleanExtra("sunday", false);
                    ((TouCalc)calcs[2]).holidaysOff = data.getBooleanExtra("holiday", false);

                    ((TouCalc)calcs[2]).country = data.getIntExtra("country", 0);

                    ((TouCalc)calcs[2]).pctInZones[0] = data.getDoubleExtra("z1pct", 0);
                    ((TouCalc)calcs[2]).pctInZones[1] = data.getDoubleExtra("z2pct", 0);
                    ((TouCalc)calcs[2]).pctInZones[2] = data.getDoubleExtra("z3pct", 0);
                    ((TouCalc)calcs[2]).pctInZones[3] = data.getDoubleExtra("zOutpct", 0);
                }
                break;


        }
        calcs[calcType].z = calcs[calcType].a + calcs[calcType].b + calcs[calcType].c;
        aVal.setText(String.format("%.04f", calcs[calcType].a));
        bVal.setText(String.format("%.04f", calcs[calcType].b));
        cVal.setText(String.format("%.04f", calcs[calcType].c));
        zVal.setText(String.format("%.04f", calcs[calcType].z));

        calculateTotal();
    }


    /*
    ***** PERMANENT DATA SAVING RELATED *****
     */

    public void saveCurrentData(View v) {
        calcs[0].a = Double.parseDouble(aVal.getText().toString());
        calcs[0].b = Double.parseDouble(bVal.getText().toString());
        calcs[0].c = Double.parseDouble(cVal.getText().toString());
        calcs[0].z = calcs[0].a + calcs[0].b + calcs[0].c;

        for (int i = 0; i < 3; i++) {
            storeDataFromMain(i);
        }

        Gson gson = new Gson();
        String dataTier = gson.toJson(calcs[0]);
        String dataFlat = gson.toJson(calcs[1]);
        String dataTOU = gson.toJson(calcs[2]);
        Context context = getApplicationContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_DATA_FILE, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SHARED_PREFERENCES_DATA_KEY_TIER, dataTier);
        editor.putString(SHARED_PREFERENCES_DATA_KEY_FLAT, dataFlat);
        editor.putString(SHARED_PREFERENCES_DATA_KEY_TOU, dataTOU);
        editor.commit();
        Toast.makeText(context, "Data has been saved", Toast.LENGTH_SHORT).show();
    }

    public void clearSavedData(View v) {
        Context context = getApplicationContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_DATA_FILE, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(SHARED_PREFERENCES_DATA_KEY_TIER);
        editor.remove(SHARED_PREFERENCES_DATA_KEY_FLAT);
        editor.remove(SHARED_PREFERENCES_DATA_KEY_TOU);
        editor.commit();
        Toast.makeText(context, "Saved Data has been cleared", Toast.LENGTH_SHORT).show();
    }

    private void loadData() {
        Context context = getApplicationContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_DATA_FILE, MODE_PRIVATE);

//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.remove(SHARED_PREFERENCES_DATA_KEY_TIER);
//        editor.commit();

        // only need to check for tiered since saving data saves all three calcs
        if (sharedPreferences.contains(SHARED_PREFERENCES_DATA_KEY_TIER)) {
            Gson gson = new Gson();
            String jsonTier = sharedPreferences.getString(SHARED_PREFERENCES_DATA_KEY_TIER, "NaN");
            if (jsonTier.equals("NaN")) {
                calcs[0] = new TierCalc();
                calcs[1] = new FlatCalc();
                calcs[2] = new TouCalc();
            }
            else {
                calcs[0] = gson.fromJson(jsonTier, TierCalc.class);
                calcs[1] = gson.fromJson(sharedPreferences.getString(SHARED_PREFERENCES_DATA_KEY_FLAT, "NaN"), FlatCalc.class);
                calcs[2] = gson.fromJson(sharedPreferences.getString(SHARED_PREFERENCES_DATA_KEY_TOU, "NaN"), TouCalc.class);
            }
        }
        else {
            calcs[0] = new TierCalc();
            calcs[1] = new FlatCalc();
            calcs[2] = new TouCalc();
        }
    }



    /*
    ***** SPINNER RELATED ******
     */

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



    /*
    ****** MAIN LAYOUT RELATED *****
     */

    public void switchToCalcType() {
        switch (calcType) {
            case 0:
                findViewById(R.id.ToSetTierRanges).setVisibility(View.VISIBLE);
                findViewById(R.id.ToSetZoneDetails).setVisibility(View.GONE);
                findViewById(R.id.ToSetUtilityRates).setVisibility(View.VISIBLE);
                findViewById(R.id.ZoneHolidayLayout).setVisibility(View.GONE);
                findViewById(R.id.ToSetTOUDetails).setVisibility(View.GONE);
                break;
            case 1:
                findViewById(R.id.ToSetTierRanges).setVisibility(View.GONE);
                findViewById(R.id.ToSetZoneDetails).setVisibility(View.GONE);
                findViewById(R.id.ToSetUtilityRates).setVisibility(View.VISIBLE);
                findViewById(R.id.ZoneHolidayLayout).setVisibility(View.GONE);
                findViewById(R.id.ToSetTOUDetails).setVisibility(View.GONE);
                break;
            case 2:
                findViewById(R.id.ToSetTierRanges).setVisibility(View.GONE);
                findViewById(R.id.ToSetZoneDetails).setVisibility(View.VISIBLE);
                findViewById(R.id.ToSetUtilityRates).setVisibility(View.GONE);
                findViewById(R.id.ZoneHolidayLayout).setVisibility(View.VISIBLE);
                findViewById(R.id.ToSetTOUDetails).setVisibility(View.VISIBLE);
                break;
        }
        showDate();
    }



    /*
    ***** MISC HELPER FUNCTIONS ******
     */

    public void storeDataFromMain(int curCalc) {
        calcs[curCalc].monthsPerBillingCycle = billingFrequencySpinner.getSelectedItemPosition() + 1;
    }

}