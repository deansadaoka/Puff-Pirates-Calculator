package com.puffpiratestudios;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.Date;

public class Season {

    private static final int JAN_DAYS = 31;
    private static final int FEB_DAYS = 28;
    private static final int MAR_DAYS = 31;
    private static final int APR_DAYS = 30;
    private static final int MAY_DAYS = 31;
    private static final int JUN_DAYS = 30;
    private static final int JUL_DAYS = 31;
    private static final int AUG_DAYS = 31;
    private static final int SEP_DAYS = 30;
    private static final int OCT_DAYS = 31;
    private static final int NOV_DAYS = 30;
    private static final int DEC_DAYS = 31;

    public double[] energyRange;
    public double[] utilRates;
    public double delivery;
    public double fixed;
    public double regulatory;
    public double taxRate;

    public int tier;
    private double monthlyBill;
    private double tax;

    public int[] startDate; // [month, day]
    public int[] endDate; // [month, day]

    public String name;

    public Season(int tier) {
        this.tier = tier;
        this.monthlyBill = 0;
        this.tax = 0;
        this.delivery = 0;
        this.fixed = 0;
        this.regulatory = 0;
        this.taxRate = 0;
        this.energyRange = new double[4];
        this.utilRates = new double[4];
        this.startDate = new int[2];
        this.endDate = new int[2];
        name = "Name";
    }

    // If no tier, tier set to 2 by default
    public Season() {
        this.tier = 2;
        this.monthlyBill = 0;
        this.tax = 0;
        this.delivery = 0;
        this.fixed = 0;
        this.regulatory = 0;
        this.taxRate = 0;
        this.energyRange = new double[4];
        this.utilRates = new double[4];
        this.startDate = new int[2];
        this.endDate = new int[2];
        name = "Name";
    }

    protected Season(Parcel in) {
        energyRange = in.createDoubleArray();
        utilRates = in.createDoubleArray();
        delivery = in.readDouble();
        fixed = in.readDouble();
        regulatory = in.readDouble();
        taxRate = in.readDouble();
        tier = in.readInt();
        monthlyBill = in.readDouble();
        tax = in.readDouble();
        name = in.readString();
        startDate = in.createIntArray();
        endDate = in.createIntArray();
    }
    

    /**
     * Calculates the tax <br>
     * IMPORTANT: Monthly Bill must be calculated prior since tax value relies on value of monthly bill
     */
    public void calcTax() {
        tax = (taxRate * 0.01) * (monthlyBill + delivery + fixed + regulatory);
    }

    /**
     * Calculates the monthly bill for the season <br>
     * IMPORTANT: Energy Usage (kW/h for billing period) must be set prior
     */
    public void calcMonthlyBill(double energyUsage) {
        monthlyBill = 0;
        for (int t = 0; t < tier && t < 3; t++) {
            if (t == tier - 1) {
                monthlyBill += ((energyUsage - energyRange[t]) * utilRates[t]) > 0 ? ((energyUsage - energyRange[t]) * utilRates[t]) : 0;
            }
            else {
                monthlyBill += (energyRange[t+1] - 1 - energyRange[t]) * utilRates[t];
            }
        }
        if (tier == 4) {
            monthlyBill += ((energyUsage - energyRange[3]) * utilRates[3]) > 0 ? ((energyUsage - energyRange[3]) * utilRates[3]) : 0;
        }
    }

    /**
     * Sums all costs and returns total. <br>
     * All costs must be calculated prior
     * @return total cost of season
     */
    private double getTotal() {
        return monthlyBill + delivery + fixed + regulatory + tax;
    }

    /**
     * Calculates the monthly cost for the season
     * @return total cost
     */
    public double calcTotal(double energyUsage) {
        calcMonthlyBill(energyUsage);
        calcTax();
        return getTotal();
    }

    /**
     * Sets the Utility Rates for each tier
     * @param t1 Tier 1 Utility Rate
     * @param t2 Tier 2 Utility Rate
     * @param t3 Tier 3 Utility Rate
     * @param t4 Tier 4 Utility Rate
     */
    public void setUtilRates(double t1, double t2, double t3,double t4) {
        utilRates[0] = t1;
        utilRates[1] = t2;
        utilRates[2] = t3;
        utilRates[3] = t4;
    }

    /**
     * Sets the Energy Range values for each tier <br>
     * Input the 'from' value (ex: Tier 3: 300 to 499, you would input t3 = 300)
     * @param t1 Tier 1 Energy Range value (from)
     * @param t2 Tier 2 Energy Range value (from)
     * @param t3 Tier 3 Energy Range value (from)
     * @param t4 Tier 4 Energy Range value (from)
     */
    public void setEnergyRange(double t1, double t2, double t3,double t4) {
        energyRange[0] = t1;
        energyRange[1] = t2;
        energyRange[2] = t3;
        energyRange[3] = t4;
    }

    /**
     * Replace the name of the season with a new name
     * @param new_name new name
     */
    public void rename(String new_name) {
        this.name.replace(name, "");
        this.name.concat(new_name);
    }

//    /**
//     * Sets the start date to the date provided
//     * @param y year
//     * @param m month
//     * @param d day
//     */
//    public void setStartDate(int y, int m, int d) {
//        startDate[0] = y;
//        startDate[1] = m;
//        startDate[2] = d;
//    }
//
//    /**
//     * Sets the end date to the date provided
//     * @param y year
//     * @param m month
//     * @param d day
//     */
//    public void setEndDate(int y, int m, int d) {
//        endDate[0] = y;
//        endDate[1] = m;
//        endDate[2] = d;
//    }
//
//    /**
//     * Creates string with start date
//     * @returna string with the date formatted
//     */
//    public String startDateToSting() {
//        if (startDate[0] == 0) {
//            return "Start Date";
//        }
//        return formatDate(startDate);
//    }
//
//    /**
//     * Creates string with end date
//     * @returna string with the date formatted
//     */
//    public String endDateToSting() {
//        if (endDate[0] == 0) {
//            return "End Date";
//        }
//        return formatDate(endDate);
//    }
//
//    private String formatDate(int[] date) {
//        String day = String.format("%02d", date[2]);
//        String year = String.format("%04d", date[0]);
//        String month;
//        switch(date[1]) {
//            case 1:
//                month = "Jan";
//            case 2:
//                month = "Feb";
//            case 3:
//                month = "Mar";
//            case 4:
//                month = "Apr";
//            case 5:
//                month = "May";
//            case 6:
//                month = "Jun";
//            case 7:
//                month = "Jul";
//            case 8:
//                month = "Aug";
//            case 9:
//                month = "Sep";
//            case 10:
//                month = "Oct";
//            case 11:
//                month = "Nov";
//            case 12:
//                month = "Dec";
//            default:
//                month = "Date";
//        }
//        return (new StringBuilder().append(month).append(" ")
//                .append(day).append(", ").append(year)).toString();
//    }

    /**
     * Returns string in number format YYYY/MM/DD
     * @return returns string of start date
     */
    public String startDateToStringNumFormat() {
        if (startDate[0] == 0) {
            return "MM/DD";
        }
        return (new StringBuilder().append(String.format("%02d", startDate[0])).append("/")
                .append(String.format("%02d", startDate[1])).toString());
    }

    /**
     * Returns string in number format YYYY/MM/DD
     * @return returns string of end date
     */
    public String endDateToStringNumFormat() {
        if (endDate[0] == 0) {
            return "MM/DD";
        }
        return (new StringBuilder().append(String.format("%02d", endDate[0])).append("/")
                .append(String.format("%02d", endDate[1])).toString());
    }


    /**
     * Calculates the number of days the billing cycle is in a season
     * @param bcStart start date of billing cycle [month, day]
     * @param monthsInBC number of months in the billing cycle (ex: monthly = 1, bimonthly = 2, etc)
     * @return number of days billing cycle is in the season
     */
    public int daysInSeason(int[] bcStart, int monthsInBC, boolean leapYear) {

        checkDays(leapYear);

        if (startDate[0] == 0 || endDate[0] == 0) {
            return 0;
        }
        int[] bcEnd = new int[2];
        //bcEnd month is start month + 1, if start month = dec, then end month is 1
//        bcEnd[0] = bcStart[0] == 12 ? 1 : bcStart[0] + 1;
        bcEnd[0] = (bcStart[0] + monthsInBC) % 12;
        if (bcEnd[0] == 0) {
            bcEnd[0] = 12;
        }
        bcEnd[1] = bcStart[1];


        if (startDate[0] == 2 && startDate[1] == 29 && !leapYear) {
            startDate[1] = 28;
        }
        if (endDate[0] == 2 && endDate[1] == 29 && !leapYear) {
            endDate[1] = 28;
        }

        boolean startInSeason = dateInSeason(bcStart);
        boolean endInSeason = dateInSeason(bcEnd);

        // if bill cycle only in season, return number of days for 1 month from billing cycle start
        if (startInSeason && endInSeason) {
            return daysBetweenDates(bcStart, bcEnd, leapYear);
        }
        // if bill cycle starts in season but ends out of season
        else if (startInSeason) {
            return daysBetweenDates(bcStart, endDate, leapYear);
        }
        // if bill cycle starts out of season and ends in season
        else if (endInSeason) {
            return daysBetweenDates(startDate, bcEnd, leapYear);
        }
        // if bill cycle not in season at all, return 0
        else {
            return 0;
        }
    }

    /**
     * Returns the number of days in the month
     * @param month number of month (Jan = 1; Feb = 2; Mar = 3; etc)
     * @param leapYear boolean of whether current year is a leap year or not
     * @return the month number
     */
    public static int daysOfMonth(int month, boolean leapYear) {
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            case 2:
                return leapYear ? 29 : 28;
            default:
                return 0;
        }
    }

    public static int daysBetweenDates(int[] from, int[] to, boolean leapYear) {
        int m1 = from[0];
        int m2 = to[0];
        int days = 0;
        // if date interval cross new year, add 12 (2 (Feb) becomes 14)
        if (m2 < m1) {
            m2 += 12;
        }
        // days left of current month
        days += (daysOfMonth(m1, leapYear) - from[1]);
        // days in last month
        days += to[1];
        // days in months in between
        for (int i = m1 + 1; i <= m2 - 1; i++) {
            if (i > 12) {
                days += daysOfMonth(i % 12, leapYear);
            }
            else {
                days += daysOfMonth(i, leapYear);
            }
        }
        return days;
    }

    private boolean dateInSeason(int[] d) {
        int[] tempEnd = new int[2];
        tempEnd[0] = endDate[0];
        tempEnd[1] = endDate[1];

        int[] tempDate = new int[2];
        tempDate[0] = d[0];
        tempDate[1] = d[1];

        // if season crosses new year (ex: Dec to Feb)
        if (startDate[0] > endDate[0]) {
            tempEnd[0] += 12; // ex: Feb would become 14 instead of 2
            // if the month of the date to check is less than or equal to the end date month
            // ex: end date month = Feb, date to check month = Jan
            if (tempDate[0] <= endDate[0]) {
                tempDate[0] += 12;
            }
        }

        // So if season crosses new year, months in the new year are now from 13-24
        // Otherwise, all months are still 1-12
        // ex: Season Range (11 - 2), Date (1) -> Season Range (11 - 14), Date (13)

         return (isDateBeforeOrEqual(startDate, tempDate) && isDateAfterOrEqual(tempEnd, tempDate));

    }

    private boolean isDateBeforeOrEqual(int[] d1, int[] d2) {
        if (d1[0] < d2[0]) {
            return true;
        }
        if (d1[0] > d2[0]) {
            return false;
        }
        // months are equal
        // now checking days
        if (d1[1] <= d2[1]) {
            return true;
        }
        return false;
    }

    private boolean isDateAfterOrEqual(int[] d1, int[] d2) {
        if (d1[0] > d2[0]) {
            return true;
        }
        if (d1[0] < d2[0]) {
            return false;
        }
        // months are equal
        // now checking days
        if (d1[1] >= d2[1]) {
            return true;
        }
        return false;
    }

    private void checkDays(boolean leapYear) {
        if (startDate[1] > daysOfMonth(startDate[0], leapYear)) {
            startDate[1] = daysOfMonth(startDate[0], leapYear);
        }
        if (endDate[1] > daysOfMonth(endDate[0], leapYear)) {
            endDate[1] = daysOfMonth(endDate[0], leapYear);
        }
    }


}
