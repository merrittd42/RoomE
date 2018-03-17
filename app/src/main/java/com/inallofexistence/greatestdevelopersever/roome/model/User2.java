package com.inallofexistence.greatestdevelopersever.roome.model;

/**
 * Created by quintybox on 3/17/18.
 */

public class User2 {


    public String username;
    public String email;
    public String hgID;

    public User2() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User2(String username, String email, String hgID) {
        this.username = username;
        this.email = email;
        this.hgID = hgID;
    }


}
