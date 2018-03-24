package com.inallofexistence.greatestdevelopersever.roome.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by quintybox on 3/17/18.
 */

public class Bill {


    public String billName;
    public Double billAmount;
    public String postedBy;
    public List<String> stillOwes;
    public String UID;
    public boolean isPaid;

    public Bill() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Bill(String billName, Double billAmount, String postedBy, List<String> stillOwes, String UID) {
        this.billName = billName;
        this.billAmount = billAmount;
        this.postedBy = postedBy;
        this.stillOwes = stillOwes;
        this.UID = UID;
        this.isPaid = false;
    }


}
