package com.puffpiratestudios;

import java.util.Date;

public class HolidayData {

    private String name;
    private long date;

    public HolidayData() {
        name = "";
        date = 0;
    }

    public HolidayData(String name, long date) {
        this.name = name;
        this.date = date;
    }

    public void setDate(long date) {
        this.date = date;
    }
    public void setName(String name) {
        this.name = name;
    }

    public long getDate() {
        return date;
    }
    public String getName() {
        return name;
    }

    public String getDateString() {
        Date d = new Date(date);
        return (new StringBuilder().append(d.toString().substring(4, 10)).append(", ")
        .append(d.toString().substring(24))).toString();
    }
}
