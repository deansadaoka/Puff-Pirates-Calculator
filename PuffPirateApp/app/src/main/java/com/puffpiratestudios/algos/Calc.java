package com.puffpiratestudios.algos;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

public abstract class Calc {

    public ArrayList<Season> seasons;
    public int startYear;
    public int startMonth;
    public int startDay;

    public int numSeasons;

    public int monthsPerBillingCycle;

    public int billingStructure; //1 = tiered, 2 = flat, 3 = TOU

    public double a, b, c, z;

    protected GregorianCalendar startBC;
    protected GregorianCalendar endBC;


    public Calc() {
        this.seasons = new ArrayList<Season>();
        for (int i = 0; i < 4; i++) {
            seasons.add(new Season());
        }
        numSeasons = 1;
        startYear = 0;
        startDay = 0;
        startMonth = 0;
        monthsPerBillingCycle = 1;

        initGregorianCalendars();
    }

    /**
     * Returns String of Date in format Month Day, Year (ex: Dec 25, 2021)
     * @return
     */
    public String dateToString() {
        if (startYear == 0) {
            return "Set Date";
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
    public abstract double calculateTotal();

    /**
     * Creates an array list with the days each season overlaps the billing cycle (BC)
     * @return Integer ArrayList containing days overlapping BC
     */
    protected ArrayList<Integer> calcNumDaysPerSeason() {
        ArrayList<Integer> days = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            days.add(i, daysSeasonOverLapsBC(i));
        }
        return days;
    }

    /**
     * Calculates the number of days the season overlaps the billing cycle (BC)
     * @param n the index of the season (0-3, ex: n = 1 -> season 2)
     * @return the number of days the season overlaps the BC
     */
    protected int daysSeasonOverLapsBC(int n) {
        int start_month = seasons.get(n).startDate[0];
        int start_day = seasons.get(n).startDate[1];
        int end_month = seasons.get(n).endDate[0];
        int end_day = seasons.get(n).endDate[1];

        // if season dates are not set yet
        if (start_month == 0 || start_day == 0 || end_month == 0 || end_day == 0) {
            return 0;
        }

        GregorianCalendar startSeason = new GregorianCalendar(startBC.get(Calendar.YEAR), start_month-1, start_day);
        GregorianCalendar endSeason = new GregorianCalendar(startBC.get(Calendar.YEAR), end_month-1, end_day);

        // if season start date after BC end date, need to decrement season start date year by 1
        if (startSeason.after(endBC)) {
            startSeason.add(Calendar.YEAR, -1);
        }

        // if season overlaps a year, add 1 year
        if (startSeason.after(endSeason)) {
            endSeason.add(Calendar.YEAR, 1);
        }

        // if entire BC is in the season
        if (startBC.after(startSeason) && endBC.before(endSeason)) {
            return numDaysBetweenPeriod(startBC, endBC);
        }

        // if entire season is in the BC
        if (startBC.before(startSeason) && endBC.after(endSeason)) {
            return numDaysBetweenPeriod(startSeason, endSeason);
        }

        // if season starts before BC but ends before BC
        if (startSeason.before(startBC) && endSeason.before(endBC)) {
            return numDaysBetweenPeriod(startBC, endSeason);
        }

        // if season starts after BC but ends after BC
        if (startSeason.after(startBC) && endSeason.after(endBC)) {
            return numDaysBetweenPeriod(startSeason, endBC);
        }

        // at this point, we know 0 days overlap between the season and the BC
        return 0;
    }


    /**
     * Calculates the number of days between two GregorianCalendar
     * @param start start date (GregorianCalendar) (inclusive)
     * @param end end date (GregorianCalendar) (inclusive)
     * @return number of days between two dates
     */
    protected int numDaysBetweenPeriod(GregorianCalendar start, GregorianCalendar end) {

        long days = (end.getTime().getTime() - start.getTime().getTime()) / (1000 * 60 * 60 * 24);

        return (int)(days) + 1;
    }

    private GregorianCalendar createStartGC() {
        return new GregorianCalendar(startYear, startMonth - 1, startDay);
    }


    public void calcAndSetZ() {
        z = a + b + c;
    }

    protected double dailyEnergyUsage() {
        return z * 24;
    }

    private void initGregorianCalendars() {
        this.startBC = new GregorianCalendar();
        this.endBC = new GregorianCalendar();
    }

    protected void setGregorianCalendars() {
        startBC.set(Calendar.YEAR, startYear);
        startBC.set(Calendar.MONTH, startMonth-1);
        startBC.set(Calendar.DAY_OF_MONTH, startDay);

        endBC.setTime(startBC.getTime());
        endBC.add(Calendar.MONTH, monthsPerBillingCycle);
    }



}
