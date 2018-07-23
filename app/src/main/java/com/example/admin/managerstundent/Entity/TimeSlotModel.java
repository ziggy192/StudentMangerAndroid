package com.example.admin.managerstundent.Entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TimeSlotModel implements Serializable {

    @SerializedName("Time")
    private String time;
    @SerializedName("Date")
    private String dayOfWeek;

    public TimeSlotModel(String time, String dayOfWeek) {
        this.time = time;
        this.dayOfWeek = dayOfWeek;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
}
