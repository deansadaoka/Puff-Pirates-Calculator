package com.puffpiratestudios.algos;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class TouCalc extends Calc{

    public int numZones;

    public String[] zoneNames;

    public double[] pctInZones;

    public ArrayList<String>[] holidayNames;
    public ArrayList<GregorianCalendar>[] holidays; // [USA, Canada]
    public int country; // 0 = USA, 1 = CANADA
    public boolean holidaysOff, sundaysOff, saturdaysOff;

    public TouCalc() {
        super();

        numZones = 2;

        zoneNames = new String[3];
        zoneNames[0] = "Name";
        zoneNames[1] = "Name";
        zoneNames[2] = "Name";

        pctInZones = new double[4];
        pctInZones[0] = 0;
        pctInZones[1] = 0;
        pctInZones[2] = 0;
        pctInZones[3] = 0;

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



    public double calculateTotal() {
        int totalDays = 0;
        double energyPerCycle;

        setGregorianCalendars();

        if (numSeasons == 1) {
            energyPerCycle = dailyEnergyUsage() * numDaysBetweenPeriod(startBC, endBC);
            return seasons.get(0).calcTOUTotal(energyPerCycle, pctInZones, numZones);
        }

        ArrayList<Integer> daysInSeasons = calcNumDaysPerSeason();
        double totalCost = 0;
        for (int i : daysInSeasons) {
            totalDays += i;
        }

        energyPerCycle = dailyEnergyUsage() * totalDays;

        for (int i = 0; i < numSeasons; i++) {
            double pctOfBC = (double) daysInSeasons.get(i) / totalDays;
            totalCost += (pctOfBC * seasons.get(i).calcTOUTotal(energyPerCycle, pctInZones, numZones));
        }

        return totalCost;
    }


    /**
     * Calculates number of days between two dates. <br>
     * Does not count Saturdays, Sundays, and/or holidays if applicable
     * @param start start date (GregorianCalendar)
     * @param end end date (GregorianCalendar) (exclusive)
     * @return number of days between two dates, not counting certain days if applicable
     */
    @Override
    protected int numDaysBetweenPeriod(GregorianCalendar start, GregorianCalendar end) {
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

    /**
     * Creates an array list with the days each season overlaps the billing cycle (BC)
     * @return Integer ArrayList containing days overlapping BC
     */
    @Override
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
    @Override
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
     * Returns a long array of holiday dates for the specified country
     * @param country 0 = USA, 1 = Canada
     * @return long array of the holiday dates for the specified country
     */
    public long[] getHolidayDatesArray(int country) {
        long[] ret = new long[holidays[country].size()];
        for (int i = 0; i < holidays[country].size(); i++) {
            ret[i] = holidays[country].get(i).getTime().getTime();
        }
        return ret;
    }

    /**
     * Update all the names in the holiday names array for the specified country
     * @param names String ArrayList containing the new names
     * @param country 0 = USA, 1 = Canada
     */
    public void updateHolidayNames(ArrayList<String> names, int country) {
        holidayNames[country].clear();
        for (String name : names) {
            holidayNames[country].add(name);
        }
    }

    /**
     * Update all the dates of the holiday for the specified country
     * @param dates long ArrayList of the dates to be added
     * @param country 0 = USA, 1 = Canada
     */
    public void updateHolidayDates(long[] dates, int country) {
        holidays[country].clear();
        for (int i = 0; i < dates.length; i++) {
            holidays[country].add(new GregorianCalendar());
            holidays[country].get(i).setTimeInMillis(dates[i]);
        }
    }
}
