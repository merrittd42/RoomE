package com.inallofexistence.greatestdevelopersever.roome.model;

import java.util.List;

/**
 * Created by quintybox on 3/17/18.
 */

public class Infraction {


    public String photoLoc;
    public String name;
    public String description;
    public String UID;

    public Infraction() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Infraction(String photoLoc, String name, String description, String UID) {
        this.photoLoc = photoLoc;
        this.name = name;
        this.description = description;
        this.UID = UID;
    }


}
