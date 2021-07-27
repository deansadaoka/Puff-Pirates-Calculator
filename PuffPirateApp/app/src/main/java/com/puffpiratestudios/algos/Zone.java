package com.puffpiratestudios.algos;

public class Zone {
    public int fromHour;
    public int fromMinute;
    public int toHour;
    public int toMinute;
    public double rate;

    public Zone() {
        fromHour = 0;
        fromMinute = 0;
        toHour = 0;
        toMinute = 0;
        rate = 0;
    }

    /**
     * Use this function if percentage in each zone is not given and must be calculated <br>
     * using the inputted time intervals for the zone
     * @return Percentage (0 to 1) of time the zone takes up in a day
     */
    public double getPercentageOfDay() {
        int hours = hourDifference(fromHour, toHour);
        int minutes = minuteDifference(fromMinute, toMinute);

        double totalTime = hours + ((double)minutes / 60);

        return totalTime / 24;
    }

    private int minuteDifference(int min1, int min2) {
        // minutes span same hour (ex: 3:15 -> 3:50)
        if (min2 > min1) {
            return min2 - min1;
        }
        // minutes are the same, so 0 minutes
        else if (min1 == min2) {
            return 0;
        }
        // min1 > min2, so the time spans two different days (ex: 3:45 -> 4:30)
        else {
            return (min2 + 60) - min1;
        }
    }

    private int hourDifference(int hour1, int hour2) {
        // hours span same day (ex: 4:00 -> 13:00)
        if (hour2 > hour1) {
            return hour2 - hour1;
        }
        // hours are the same, so 0 hours
        else if (hour1 == hour2) {
            return 0;
        }
        // hour1 > hour2, so the time spans two different days (ex: 19:00 -> 2:00)
        else {
            return (hour2 + 24) - hour1;
        }
    }
}
