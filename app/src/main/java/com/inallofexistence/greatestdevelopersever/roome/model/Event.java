package com.inallofexistence.greatestdevelopersever.roome.model;

/**
 * Created by quintybox on 3/17/18.
 */

public class Event {


    public String eventName;
    public String startDate;
    public String startTime;
    public String endDate;
    public String endTime;
    public String UID;

    public Event() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Event(String eventName, String startDate, String startTime, String endDate, String endTime, String UID) {
        this.eventName = eventName;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
        this.UID = UID;
    }


}
