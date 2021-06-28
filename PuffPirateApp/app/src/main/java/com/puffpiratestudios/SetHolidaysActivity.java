package com.puffpiratestudios;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;

public class SetHolidaysActivity extends AppCompatActivity {

    ArrayList<Holiday> holidaysUSA = new ArrayList<>();
    ArrayList<Holiday> holidaysCA = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_holidays);




    }
}