package com.puffpiratestudios;

import androidx.annotation.Nullable;
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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

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

    TierCalc calc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Context context = getApplicationContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_DATA_FILE, MODE_PRIVATE);

        if (sharedPreferences.contains(SHARED_PREFERENCES_DATA_KEY)) {
            Gson gson = new Gson();
            String calcJson = sharedPreferences.getString(SHARED_PREFERENCES_DATA_KEY, "NaN");
            if (calcJson.equals("NaN")) {
                calc = new TierCalc();
            }
            else {
                calc = gson.fromJson(calcJson, TierCalc.class);
            }
        }
        else {
            calc = new TierCalc();
        }


        TextView billingInfo = (TextView) findViewById(R.id.BillingInfo);
        billingInfo.setBackgroundColor(Color.parseColor("#FF5555"));
        billingInfo.setTextColor(getResources().getColor(R.color.white));

        zVal = findViewById(R.id.zTextVal);
        zVal.setText(String.format("%.04f", calc.z));

        aVal = findViewById(R.id.aEditText);
        bVal = findViewById(R.id.bEditText);
        cVal = findViewById(R.id.cEditText);

        aVal.setText(String.format("%.04f", calc.a));
        bVal.setText(String.format("%.04f", calc.b));
        cVal.setText(String.format("%.04f", calc.c));

        dateView = (TextView) findViewById(R.id.dateText);
        calendar = Calendar.getInstance();

        if (!dateExists()) {
            calc.startYear = calendar.get(Calendar.YEAR);
            calc.startMonth = calendar.get(Calendar.MONTH) + 1;
            calc.startDay = calendar.get(Calendar.DAY_OF_MONTH);
        }
        showDate();


        String[] frequencyOptions = new String[] {"Monthly", "Bi-Monthly", "Quarterly"};
        ArrayAdapter<String> billingFrequencyAdapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_spinner_item,frequencyOptions);
        billingFrequencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        billingFrequencySpinner = findViewById(R.id.billingCycleSpinner);
        billingFrequencySpinner.setAdapter(billingFrequencyAdapter);
        billingFrequencySpinner.setOnItemSelectedListener(this);
        billingFrequencySpinner.setSelection(calc.monthsPerBillingCycle - 1);

    }

    public void setFixedCharges(View v) {
        Intent i = new Intent(this, SetFixedChargesActivity.class);
        i.putExtra("numSeasons", calc.numSeasons);
        i.putExtra("s1Fixed", calc.seasons.get(0).fixed);
        i.putExtra("s2Fixed", calc.seasons.get(1).fixed);
        i.putExtra("s3Fixed", calc.seasons.get(2).fixed);
        i.putExtra("s4Fixed", calc.seasons.get(3).fixed);
        startActivityForResult(i, FIXED_CHARGES_ACTIVITY_REQUEST_CODE);
    }

    public void setRegCharges(View v) {
        Intent i = new Intent(this, SetRegulatoryChargesActivity.class);
        i.putExtra("numSeasons", calc.numSeasons);
        i.putExtra("s1Reg", calc.seasons.get(0).regulatory);
        i.putExtra("s2Reg", calc.seasons.get(1).regulatory);
        i.putExtra("s3Reg", calc.seasons.get(2).regulatory);
        i.putExtra("s4Reg", calc.seasons.get(3).regulatory);
        startActivityForResult(i, REGULATORY_CHARGES_ACTIVITY_REQUEST_CODE);
    }

    public void setDelivery(View v) {
        Intent i = new Intent(this, SetDeliveryActivity.class);

        i.putExtra("numSeasons", calc.numSeasons);

        i.putExtra("s1Delivery", calc.seasons.get(0).delivery);
        i.putExtra("s2Delivery", calc.seasons.get(1).delivery);
        i.putExtra("s3Delivery", calc.seasons.get(2).delivery);
        i.putExtra("s4Delivery", calc.seasons.get(3).delivery);
        startActivityForResult(i, DELIVERY_ACTIVITY_REQUEST_CODE);
    }

    public void setTaxRates(View v) {
        Intent i = new Intent(this, SetTaxRatesActivity.class);
        i.putExtra("numSeasons", calc.numSeasons);
        i.putExtra("s1Tax", calc.seasons.get(0).taxRate);
        i.putExtra("s2Tax", calc.seasons.get(1).taxRate);
        i.putExtra("s3Tax", calc.seasons.get(2).taxRate);
        i.putExtra("s4Tax", calc.seasons.get(3).taxRate);
        startActivityForResult(i, TAX_RATES_ACTIVITY_REQUEST_CODE);
    }

    public void setUtilityRates(View v) {
        Intent i = new Intent(this, SetUtilityRatesActivity.class);
        i.putExtra("numSeasons", calc.numSeasons);

        i.putExtra("s1Tier", calc.seasons.get(0).tier);
        i.putExtra("s2Tier", calc.seasons.get(1).tier);
        i.putExtra("s3Tier", calc.seasons.get(2).tier);
        i.putExtra("s4Tier", calc.seasons.get(3).tier);

        i.putExtra("s1t1Util", calc.seasons.get(0).utilRates[0]);
        i.putExtra("s1t2Util", calc.seasons.get(0).utilRates[1]);
        i.putExtra("s1t3Util", calc.seasons.get(0).utilRates[2]);
        i.putExtra("s1t4Util", calc.seasons.get(0).utilRates[3]);
        i.putExtra("s2t1Util", calc.seasons.get(1).utilRates[0]);
        i.putExtra("s2t2Util", calc.seasons.get(1).utilRates[1]);
        i.putExtra("s2t3Util", calc.seasons.get(1).utilRates[2]);
        i.putExtra("s2t4Util", calc.seasons.get(1).utilRates[3]);
        i.putExtra("s3t1Util", calc.seasons.get(2).utilRates[0]);
        i.putExtra("s3t2Util", calc.seasons.get(2).utilRates[1]);
        i.putExtra("s3t3Util", calc.seasons.get(2).utilRates[2]);
        i.putExtra("s3t4Util", calc.seasons.get(2).utilRates[3]);
        i.putExtra("s4t1Util", calc.seasons.get(3).utilRates[0]);
        i.putExtra("s4t2Util", calc.seasons.get(3).utilRates[1]);
        i.putExtra("s4t3Util", calc.seasons.get(3).utilRates[2]);
        i.putExtra("s4t4Util", calc.seasons.get(3).utilRates[3]);

        startActivityForResult(i, UTILITY_RATES_ACTIVITY_REQUEST_CODE);
    }

    public void setTierRanges(View v) {
        Intent i = new Intent(this, SetTierRangesActivity.class);

        i.putExtra("numSeasons", calc.numSeasons);

        i.putExtra("s1Tier", calc.seasons.get(0).tier);
        i.putExtra("s2Tier", calc.seasons.get(1).tier);
        i.putExtra("s3Tier", calc.seasons.get(2).tier);
        i.putExtra("s4Tier", calc.seasons.get(3).tier);

        i.putExtra("s1t1Tier", calc.seasons.get(0).energyRange[0]);
        i.putExtra("s1t2Tier", calc.seasons.get(0).energyRange[1]);
        i.putExtra("s1t3Tier", calc.seasons.get(0).energyRange[2]);
        i.putExtra("s1t4Tier", calc.seasons.get(0).energyRange[3]);
        i.putExtra("s2t1Tier", calc.seasons.get(1).energyRange[0]);
        i.putExtra("s2t2Tier", calc.seasons.get(1).energyRange[1]);
        i.putExtra("s2t3Tier", calc.seasons.get(1).energyRange[2]);
        i.putExtra("s2t4Tier", calc.seasons.get(1).energyRange[3]);
        i.putExtra("s3t1Tier", calc.seasons.get(2).energyRange[0]);
        i.putExtra("s3t2Tier", calc.seasons.get(2).energyRange[1]);
        i.putExtra("s3t3Tier", calc.seasons.get(2).energyRange[2]);
        i.putExtra("s3t4Tier", calc.seasons.get(2).energyRange[3]);
        i.putExtra("s4t1Tier", calc.seasons.get(3).energyRange[0]);
        i.putExtra("s4t2Tier", calc.seasons.get(3).energyRange[1]);
        i.putExtra("s4t3Tier", calc.seasons.get(3).energyRange[2]);
        i.putExtra("s4t4Tier", calc.seasons.get(3).energyRange[3]);

        startActivityForResult(i, TIER_RANGES_ACTIVITY_REQUEST_CODE);
    }

    public void setSeasonDetails(View v) {
        Intent i = new Intent(this, SetSeasonDetailsActivity.class);

        i.putExtra("s1Name", calc.seasons.get(0).name);
        i.putExtra("s2Name", calc.seasons.get(1).name);
        i.putExtra("s3Name", calc.seasons.get(2).name);
        i.putExtra("s4Name", calc.seasons.get(3).name);

        i.putExtra("s1StartDate", calc.seasons.get(0).startDate);
        i.putExtra("s2StartDate", calc.seasons.get(1).startDate);
        i.putExtra("s3StartDate", calc.seasons.get(2).startDate);
        i.putExtra("s4StartDate", calc.seasons.get(3).startDate);

        i.putExtra("s1EndDate", calc.seasons.get(0).endDate);
        i.putExtra("s2EndDate", calc.seasons.get(1).endDate);
        i.putExtra("s3EndDate", calc.seasons.get(2).endDate);
        i.putExtra("s4EndDate", calc.seasons.get(3).endDate);

        i.putExtra("numSeasons", calc.numSeasons);

        startActivityForResult(i, SEASON_DETAILS_ACTIVITY_REQUEST_CODE);
    }

    public void calculateTotal() {
        double total = calc.calculateTotal();
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
                    myDateListener, calc.startYear, calc.startMonth, calc.startDay);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    calc.startYear = arg1;
                    calc.startMonth = arg2 + 1;
                    calc.startDay = arg3;
                    showDate();
                    calculateTotal();
                }
            };

    private void showDate() {
        dateView.setText(calc.dateToString());
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
                calc.a = Double.parseDouble(aVal.getText().toString());
                calc.b = Double.parseDouble(bVal.getText().toString());
                calc.c = Double.parseDouble(cVal.getText().toString());
                calc.z = calc.a + calc.b + calc.c;
                zVal.setText(String.format("%.04f", calc.z));
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
        calc.a = Double.parseDouble(aVal.getText().toString());
        calc.b = Double.parseDouble(bVal.getText().toString());
        calc.c = Double.parseDouble(cVal.getText().toString());
        calc.z = calc.a + calc.b + calc.c;
        unregisterReceiver(zUpdateReceiver);
    }


    public boolean dateExists() {
        return (calc.startYear != 0 && calc.startDay != 0 && calc.startMonth != 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case FIXED_CHARGES_ACTIVITY_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    calc.seasons.get(0).fixed = data.getDoubleExtra("s1Fixed", calc.seasons.get(0).fixed);
                    calc.seasons.get(1).fixed = data.getDoubleExtra("s2Fixed", calc.seasons.get(1).fixed);
                    calc.seasons.get(2).fixed = data.getDoubleExtra("s3Fixed", calc.seasons.get(2).fixed);
                    calc.seasons.get(3).fixed = data.getDoubleExtra("s4Fixed", calc.seasons.get(3).fixed);
                }
            case REGULATORY_CHARGES_ACTIVITY_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    calc.seasons.get(0).regulatory = data.getDoubleExtra("s1Reg", calc.seasons.get(0).regulatory);
                    calc.seasons.get(1).regulatory = data.getDoubleExtra("s2Reg", calc.seasons.get(1).regulatory);
                    calc.seasons.get(2).regulatory = data.getDoubleExtra("s3Reg", calc.seasons.get(2).regulatory);
                    calc.seasons.get(3).regulatory = data.getDoubleExtra("s4Reg", calc.seasons.get(3).regulatory);
                }
            case DELIVERY_ACTIVITY_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    calc.seasons.get(0).delivery = data.getDoubleExtra("s1Delivery", calc.seasons.get(0).delivery);
                    calc.seasons.get(1).delivery = data.getDoubleExtra("s2Delivery", calc.seasons.get(1).delivery);
                    calc.seasons.get(2).delivery = data.getDoubleExtra("s3Delivery", calc.seasons.get(2).delivery);
                    calc.seasons.get(3).delivery = data.getDoubleExtra("s4Delivery", calc.seasons.get(3).delivery);
                }
            case TAX_RATES_ACTIVITY_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    calc.seasons.get(0).taxRate = data.getDoubleExtra("s1Tax", calc.seasons.get(0).taxRate);
                    calc.seasons.get(1).taxRate = data.getDoubleExtra("s2Tax", calc.seasons.get(1).taxRate);
                    calc.seasons.get(2).taxRate = data.getDoubleExtra("s3Tax", calc.seasons.get(2).taxRate);
                    calc.seasons.get(3).taxRate = data.getDoubleExtra("s4Tax", calc.seasons.get(3).taxRate);
                }
            case UTILITY_RATES_ACTIVITY_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    calc.seasons.get(0).utilRates[0] = data.getDoubleExtra("s1t1Util", calc.seasons.get(0).utilRates[0]);
                    calc.seasons.get(0).utilRates[1] = data.getDoubleExtra("s1t2Util", calc.seasons.get(0).utilRates[1]);
                    calc.seasons.get(0).utilRates[2] = data.getDoubleExtra("s1t3Util", calc.seasons.get(0).utilRates[2]);
                    calc.seasons.get(0).utilRates[3] = data.getDoubleExtra("s1t4Util", calc.seasons.get(0).utilRates[3]);
                    calc.seasons.get(1).utilRates[0] = data.getDoubleExtra("s2t1Util", calc.seasons.get(1).utilRates[0]);
                    calc.seasons.get(1).utilRates[1] = data.getDoubleExtra("s2t2Util", calc.seasons.get(1).utilRates[1]);
                    calc.seasons.get(1).utilRates[2] = data.getDoubleExtra("s2t3Util", calc.seasons.get(1).utilRates[2]);
                    calc.seasons.get(1).utilRates[3] = data.getDoubleExtra("s2t4Util", calc.seasons.get(1).utilRates[3]);
                    calc.seasons.get(2).utilRates[0] = data.getDoubleExtra("s3t1Util", calc.seasons.get(2).utilRates[0]);
                    calc.seasons.get(2).utilRates[1] = data.getDoubleExtra("s3t2Util", calc.seasons.get(2).utilRates[1]);
                    calc.seasons.get(2).utilRates[2] = data.getDoubleExtra("s3t3Util", calc.seasons.get(2).utilRates[2]);
                    calc.seasons.get(2).utilRates[3] = data.getDoubleExtra("s3t4Util", calc.seasons.get(2).utilRates[3]);
                    calc.seasons.get(3).utilRates[0] = data.getDoubleExtra("s4t1Util", calc.seasons.get(3).utilRates[0]);
                    calc.seasons.get(3).utilRates[1] = data.getDoubleExtra("s4t2Util", calc.seasons.get(3).utilRates[1]);
                    calc.seasons.get(3).utilRates[2] = data.getDoubleExtra("s4t3Util", calc.seasons.get(3).utilRates[2]);
                    calc.seasons.get(3).utilRates[3] = data.getDoubleExtra("s4t4Util", calc.seasons.get(3).utilRates[3]);
                }
            case TIER_RANGES_ACTIVITY_REQUEST_CODE:
                if (resultCode == RESULT_OK) {

                    calc.seasons.get(0).tier = data.getIntExtra("s1Tier", calc.seasons.get(0).tier);
                    calc.seasons.get(1).tier = data.getIntExtra("s2Tier", calc.seasons.get(1).tier);
                    calc.seasons.get(2).tier = data.getIntExtra("s3Tier", calc.seasons.get(2).tier);
                    calc.seasons.get(3).tier = data.getIntExtra("s4Tier", calc.seasons.get(3).tier);

                    calc.seasons.get(0).energyRange[0] = data.getDoubleExtra("s1t1Tier", calc.seasons.get(0).energyRange[0]);
                    calc.seasons.get(0).energyRange[1] = data.getDoubleExtra("s1t2Tier", calc.seasons.get(0).energyRange[1]);
                    calc.seasons.get(0).energyRange[2] = data.getDoubleExtra("s1t3Tier", calc.seasons.get(0).energyRange[2]);
                    calc.seasons.get(0).energyRange[3] = data.getDoubleExtra("s1t4Tier", calc.seasons.get(0).energyRange[3]);
                    calc.seasons.get(1).energyRange[0] = data.getDoubleExtra("s2t1Tier", calc.seasons.get(1).energyRange[0]);
                    calc.seasons.get(1).energyRange[1] = data.getDoubleExtra("s2t2Tier", calc.seasons.get(1).energyRange[1]);
                    calc.seasons.get(1).energyRange[2] = data.getDoubleExtra("s2t3Tier", calc.seasons.get(1).energyRange[2]);
                    calc.seasons.get(1).energyRange[3] = data.getDoubleExtra("s2t4Tier", calc.seasons.get(1).energyRange[3]);
                    calc.seasons.get(2).energyRange[0] = data.getDoubleExtra("s3t1Tier", calc.seasons.get(2).energyRange[0]);
                    calc.seasons.get(2).energyRange[1] = data.getDoubleExtra("s3t2Tier", calc.seasons.get(2).energyRange[1]);
                    calc.seasons.get(2).energyRange[2] = data.getDoubleExtra("s3t3Tier", calc.seasons.get(2).energyRange[2]);
                    calc.seasons.get(2).energyRange[3] = data.getDoubleExtra("s3t4Tier", calc.seasons.get(2).energyRange[3]);
                    calc.seasons.get(3).energyRange[0] = data.getDoubleExtra("s4t1Tier", calc.seasons.get(3).energyRange[0]);
                    calc.seasons.get(3).energyRange[1] = data.getDoubleExtra("s4t2Tier", calc.seasons.get(3).energyRange[1]);
                    calc.seasons.get(3).energyRange[2] = data.getDoubleExtra("s4t3Tier", calc.seasons.get(3).energyRange[2]);
                    calc.seasons.get(3).energyRange[3] = data.getDoubleExtra("s4t4Tier", calc.seasons.get(3).energyRange[3]);
                }
            case SEASON_DETAILS_ACTIVITY_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    calc.numSeasons = data.getIntExtra("numSeasons", calc.numSeasons);

                    calc.seasons.get(0).name = data.getStringExtra("s1Name");
                    calc.seasons.get(1).name = data.getStringExtra("s2Name");
                    calc.seasons.get(2).name = data.getStringExtra("s3Name");
                    calc.seasons.get(3).name = data.getStringExtra("s4Name");

                    calc.seasons.get(0).startDate[0] = data.getIntExtra("s1StartDateMonth", calc.seasons.get(0).startDate[0]);
                    calc.seasons.get(1).startDate[0] = data.getIntExtra("s2StartDateMonth", calc.seasons.get(1).startDate[0]);
                    calc.seasons.get(2).startDate[0] = data.getIntExtra("s3StartDateMonth", calc.seasons.get(2).startDate[0]);
                    calc.seasons.get(3).startDate[0] = data.getIntExtra("s4StartDateMonth", calc.seasons.get(3).startDate[0]);

                    calc.seasons.get(0).endDate[0] = data.getIntExtra("s1EndDateMonth", calc.seasons.get(0).endDate[0]);
                    calc.seasons.get(1).endDate[0] = data.getIntExtra("s2EndDateMonth", calc.seasons.get(1).endDate[0]);
                    calc.seasons.get(2).endDate[0] = data.getIntExtra("s3EndDateMonth", calc.seasons.get(2).endDate[0]);
                    calc.seasons.get(3).endDate[0] = data.getIntExtra("s4EndDateMonth", calc.seasons.get(3).endDate[0]);

                    calc.seasons.get(0).startDate[1] = data.getIntExtra("s1StartDateDay", calc.seasons.get(0).startDate[1]);
                    calc.seasons.get(1).startDate[1] = data.getIntExtra("s2StartDateDay", calc.seasons.get(1).startDate[1]);
                    calc.seasons.get(2).startDate[1] = data.getIntExtra("s3StartDateDay", calc.seasons.get(2).startDate[1]);
                    calc.seasons.get(3).startDate[1] = data.getIntExtra("s4StartDateDay", calc.seasons.get(3).startDate[1]);

                    calc.seasons.get(0).endDate[1] = data.getIntExtra("s1EndDateDay", calc.seasons.get(0).endDate[1]);
                    calc.seasons.get(1).endDate[1] = data.getIntExtra("s2EndDateDay", calc.seasons.get(1).endDate[1]);
                    calc.seasons.get(2).endDate[1] = data.getIntExtra("s3EndDateDay", calc.seasons.get(2).endDate[1]);
                    calc.seasons.get(3).endDate[1] = data.getIntExtra("s4EndDateDay", calc.seasons.get(3).endDate[1]);
                }
        }
        calc.z = calc.a + calc.b + calc.c;
        aVal.setText(String.format("%.04f", calc.a));
        bVal.setText(String.format("%.04f", calc.b));
        cVal.setText(String.format("%.04f", calc.c));
        zVal.setText(String.format("%.04f", calc.z));

        calculateTotal();
    }

    public void saveCurrentData(View v) {
        calc.a = Double.parseDouble(aVal.getText().toString());
        calc.b = Double.parseDouble(bVal.getText().toString());
        calc.c = Double.parseDouble(cVal.getText().toString());
        calc.z = calc.a + calc.b + calc.c;

        calc.monthsPerBillingCycle = billingFrequencySpinner.getSelectedItemPosition() + 1;

        Gson gson = new Gson();
        String dataJson = gson.toJson(calc);
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
        calc.monthsPerBillingCycle = billingFrequencySpinner.getSelectedItemPosition() + 1;
        calculateTotal();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}
}