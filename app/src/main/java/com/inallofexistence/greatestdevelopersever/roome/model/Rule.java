package com.inallofexistence.greatestdevelopersever.roome.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by quintybox on 3/17/18.
 */

public class Rule {


    public String ruleName;
    public String ruleContent;
    public ArrayList<User2> yesVoters;
    public String UID;

    public Rule() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Rule(String ruleName, String ruleContent, ArrayList<User2> yesVoters, String UID) {
        this.ruleName = ruleName;
        this.ruleContent = ruleContent;
        this.yesVoters = yesVoters;
        this.UID = UID;
    }


}
