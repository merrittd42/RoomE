package com.inallofexistence.greatestdevelopersever.roome.rules;

import android.support.v4.app.Fragment;

import com.inallofexistence.greatestdevelopersever.roome.SingleFragmentActivity;

public class RulesActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment(){
        return new RulesFragment();
    }
}
