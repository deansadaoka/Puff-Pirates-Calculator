package com.puffpiratestudios;

import android.os.Parcel;
import android.os.Parcelable;

public class Holiday implements Parcelable {
    public int[] date; // [month, day]
    public String name;

    public Holiday() {
        date = new int[2];
        date[0] = 0;
        date[1] = 0;
    }

    protected Holiday(Parcel in) {
        date = in.createIntArray();
        name = in.readString();
    }

    public static final Creator<Holiday> CREATOR = new Creator<Holiday>() {
        @Override
        public Holiday createFromParcel(Parcel in) {
            return new Holiday(in);
        }

        @Override
        public Holiday[] newArray(int size) {
            return new Holiday[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeIntArray(date);
        dest.writeString(name);
    }

    public void setEqualTo(Holiday newHoliday) {
        this.date[0] = newHoliday.date[0];
        this.date[1] = newHoliday.date[1];
        this.name = newHoliday.name;
    }
}
