package com.puffpiratestudios.algos;

public class Holiday{
    public int[] date; // [month, day]
    public String name;

    public Holiday() {
        date = new int[2];
        date[0] = 0;
        date[1] = 0;
    }

    public void setEqualTo(Holiday newHoliday) {
        this.date[0] = newHoliday.date[0];
        this.date[1] = newHoliday.date[1];
        this.name = newHoliday.name;
    }
}
