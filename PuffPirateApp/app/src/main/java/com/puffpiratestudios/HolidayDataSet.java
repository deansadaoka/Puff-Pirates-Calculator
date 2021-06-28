package com.puffpiratestudios;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HolidayDataSet {

    public HashMap<String, Long> holidaysUSA;
    public HashMap<String, Long> holidaysCA;

    private ArrayList<String> retNamesUSA;
    private ArrayList<String> retNamesCA;

    private ArrayList<Long> retDatesUSA;
    private ArrayList<Long> retDatesCA;

    private String toBeAdded;
    
    public HolidayDataSet() {
        holidaysUSA = new HashMap<>();
        holidaysCA = new HashMap<>();

        retNamesUSA = new ArrayList<>();
        retNamesCA = new ArrayList<>();
        retDatesUSA = new ArrayList<>();
        retDatesCA = new ArrayList<>();

        toBeAdded = "";
    }
    
    public void addHoliday(String name, long date, int country) {
        if (country == 0) {
            holidaysUSA.put(name, date);
        }
        else {
            holidaysCA.put(name, date);
        }
    }

    public void removeHoliday(String name, int country) {
        if (country == 0) {
            holidaysUSA.remove(name);
        }
        else {
            holidaysCA.remove(name);
        }
    }

    public void editHoliday(String name, String newName, long newDate, int country) {
        if (country == 0) {
            holidaysUSA.remove(name);
            holidaysUSA.put(newName, newDate);
        }
        else {
            holidaysCA.remove(name);
            holidaysCA.put(newName, newDate);
        }
    }

    public void addIntentHolidays(ArrayList<String> names, long[] dates, int country) {
        if (country == 0) {
            for (int i = 0; i < names.size(); i++) {
                holidaysUSA.put(names.get(i), dates[i]);
            }
        }
        else {
            for (int i = 0; i < names.size(); i++) {
                holidaysCA.put(names.get(i), dates[i]);
            }
        }
    }

    public void prepReturnValues() {
        retNamesUSA.clear();
        retNamesCA.clear();
        retDatesUSA.clear();
        retDatesCA.clear();

        for (Map.Entry entry : holidaysUSA.entrySet()) {
            retNamesUSA.add((String)entry.getKey());
            retDatesUSA.add((Long)entry.getValue());
        }

        for (Map.Entry entry : holidaysCA.entrySet()) {
            retNamesCA.add((String)entry.getKey());
            retDatesCA.add((Long)entry.getValue());
        }
    }

    public ArrayList<String> getHolidayNames(int country) {
        if (country == 0) {
            return retNamesUSA;
        }
        else {
            return retNamesCA;
        }
    }

    public long[] getHolidayDates(int country) {
        long[] ret;
        if (country == 0) {
            ret = new long[retDatesUSA.size()];
            for (int i = 0; i < retDatesUSA.size(); i++) {
                ret[i] = retDatesUSA.get(i);
            }
        }
        else {
            ret = new long[retDatesCA.size()];
            for (int i = 0; i < retDatesCA.size(); i++) {
                ret[i] = retDatesCA.get(i);
            }
        }
        return ret;
    }

    public void queueString(String name) {
        toBeAdded = name;
    }

    public void addDateToName(long date, int country) {
        if (country == 0) {
            holidaysUSA.put(toBeAdded, date);
        }
        else {
            holidaysCA.put(toBeAdded, date);
        }
    }


}
