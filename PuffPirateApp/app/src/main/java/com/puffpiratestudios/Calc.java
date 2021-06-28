package com.puffpiratestudios;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

public class Calc {

    public ArrayList<Season> seasons;
    public int startYear;
    public int startMonth;
    public int startDay;

    public int numSeasons;

    public int monthsPerBillingCycle;

    public int numTiers;

    public int billingStructure; //1 = tiered, 2 = flat, 3 = TOU

    public double a, b, c, z;

    public String[] zoneNames;

    public ArrayList<String>[] holidayNames;
    public ArrayList<GregorianCalendar>[] holidays; // [USA, Canada]
    int country; // 0 = USA, 1 = CANADA
    boolean holidaysOff, sundaysOff, saturdaysOff;

    public Calc(int billingStructure) {
        this.seasons = new ArrayList<Season>();
        for (int i = 0; i < 4; i++) {
            seasons.add(new Season());
        }
        numSeasons = 1;
        startYear = 0;
        startDay = 0;
        startMonth = 0;
        monthsPerBillingCycle = 1;
        numTiers = 2;
        this.billingStructure = billingStructure;
        zoneNames = new String[3];
        zoneNames[0] = "Name";
        zoneNames[1] = "Name";
        zoneNames[2] = "Name";

        holidays = new ArrayList[2];
        holidays[0] = new ArrayList<>();
        holidays[1] = new ArrayList<>();

        holidayNames = new ArrayList[2];
        holidayNames[0] = new ArrayList<>();
        holidayNames[1] = new ArrayList<>();

        holidaysOff = false;
        sundaysOff = false;
        saturdaysOff = false;
        country = 0;
    }

    public void updateAllSeasonTiers(int new_tier) {
        for (int i = 0; i < 4; i ++) {
            seasons.get(i).tier = new_tier;
        }
    }

    /**
     * Returns String of Date in format Month Day, Year (ex: Dec 25, 2021)
     * @return
     */
    public String dateToString() {
        if (startYear == 0) {
            return "Date";
        }
        return formatDate(startYear, startMonth, startDay);
    }

    /**
     * returns String of date in format: Month Day, Year (ex: Dec 25, 2021)
     * @param y year
     * @param m month
     * @param d day
     * @return formatted date
     */
    public static String formatDate(int y, int m, int d) {
        String day = String.format("%02d", d);
        String year = String.format("%04d", y);
        String month;
        switch(m) {
            case 1:
                month = "Jan";
                break;
            case 2:
                month = "Feb";
                break;
            case 3:
                month = "Mar";
                break;
            case 4:
                month = "Apr";
                break;
            case 5:
                month = "May";
                break;
            case 6:
                month = "Jun";
                break;
            case 7:
                month = "Jul";
                break;
            case 8:
                month = "Aug";
                break;
            case 9:
                month = "Sep";
                break;
            case 10:
                month = "Oct";
                break;
            case 11:
                month = "Nov";
                break;
            case 12:
                month = "Dec";
                break;
            default:
                month = "Date";
                break;
        }
        return (new StringBuilder().append(month).append(" ")
                .append(day).append(", ").append(year)).toString();
    }

    public void dateStringToNum(String date) {
        switch(date.substring(0, 3)) {
            case "Jan":
                startMonth = 1;
                break;
            case "Feb":
                startMonth = 2;
                break;
            case "Mar":
                startMonth = 3;
                break;
            case "Apr":
                startMonth = 4;
                break;
            case "May":
                startMonth = 5;
                break;
            case "Jun":
                startMonth = 6;
                break;
            case "Jul":
                startMonth = 7;
                break;
            case "Aug":
                startMonth = 8;
                break;
            case "Sep":
                startMonth = 9;
                break;
            case "Oct":
                startMonth = 10;
                break;
            case "Nov":
                startMonth = 11;
                break;
            case "Dec":
                startMonth = 12;
                break;
            default:
                startMonth = 0;
        }
        try {
            startDay = Integer.parseInt(date.substring(4, 6));
            startYear = Integer.parseInt(date.substring(8));
        }
        catch (Exception e) {
            startDay = 0;
            startYear = 0;
        }
    }

    /**
     * Calculates the total cost for the specified start date
     * @return total cost
     */
    public double calculateTotal() {
        HashMap<Integer, Integer> sMap = daysInSeasonsInBillingCycle();
        int totalDays = 0;
        double totalCost = 0;
        for (int days : sMap.values()) {
            totalDays += days;
        }

        double energyPerCycle = dailyEnergyUsage() * totalDays;

        for (Map.Entry<Integer, Integer> s : sMap.entrySet()) {
            double pctOfBC = ((double)s.getValue() / totalDays);
            totalCost += (pctOfBC * seasons.get(s.getKey()).calcTotal(energyPerCycle, billingStructure));
        }

        return totalCost;
    }

    /**
     * Creates a hash map that includes the number of days the billing cycle is in a season
     * @return hash map with data map[season # (1-4), days in season]
     */
    private HashMap<Integer, Integer> daysInSeasonsInBillingCycle() {
        HashMap<Integer, Integer> sMap = new HashMap<Integer, Integer>();
        boolean leap = isLeapYear(startYear);

        if (numSeasons == 1) {
            int endMonth = (startMonth + monthsPerBillingCycle) % 12;
            endMonth = endMonth == 0 ? 12 : endMonth;
            sMap.put(0, Season.daysBetweenDates(new int[]{startMonth, startDay}, new int[]{endMonth, startDay}, leap));
            return sMap;
        }

        for (int i = 0; i < 4; i++) {
            sMap.put(i, seasons.get(i).daysInSeason(new int[]{startMonth, startDay}, monthsPerBillingCycle, leap));
        }
        return sMap;
    }

    private boolean isLeapYear(int y) {
        if (y % 4 == 0) {
            if (y % 100 == 0) {
                if (y % 400 == 0) {
                    return false;
                }
                else {
                    return true;
                }
            }
            else {
                return true;
            }
        }
        else {
            return false;
        }
    }

    public void calcAndSetZ() {
        z = a + b + c;
    }

    private double dailyEnergyUsage() {
        return z * 24;
    }

    public int numDaysBetweenPeriod(GregorianCalendar start, GregorianCalendar end) {
        long numHolidays = 0;

        final int startW = start.get(Calendar.DAY_OF_WEEK);
        final int endW = end.get(Calendar.DAY_OF_WEEK);

        long days = (end.getTime().getTime() - start.getTime().getTime()) / (1000 * 60 * 60 * 24);

        long numSat = saturdaysOff ? (days + startW) / 7 : 0;
        long numSun = sundaysOff ? ((days + startW) / 7) + (startW == Calendar.SUNDAY ? 1 : 0) + (endW == Calendar.SUNDAY ? 1 : 0): 0;

        if (holidaysOff) {
            for (GregorianCalendar ld : holidays[country]) {
                if ((start.before(ld) || start.equals(ld)) && end.after(ld)) {
                    numHolidays++;
                }
            }
        }

        return (int)(days - numSat - numSun - numHolidays);
    }

    public long[] getHolidayDatesArray(int country) {
        long[] ret = new long[holidays[country].size()];
        for (int i = 0; i < holidays[country].size(); i++) {
            ret[i] = holidays[country].get(i).getTime().getTime();
        }
        return ret;
    }

    public void updateHolidayNames(ArrayList<String> names, int country) {
        holidayNames[country].clear();
        for (String name : names) {
            holidayNames[country].add(name);
        }
    }

    public void updateHolidayDates(long[] dates, int country) {
        holidays[country].clear();
        for (int i = 0; i < dates.length; i++) {
            holidays[country].add(new GregorianCalendar());
            holidays[country].get(i).setTimeInMillis(dates[i]);
        }
    }


}
