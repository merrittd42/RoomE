package com.inallofexistence.greatestdevelopersever.roome.rules;

import android.support.v4.app.Fragment;

import com.inallofexistence.greatestdevelopersever.roome.SingleFragmentActivity;


public class ViewRuleActivity extends SingleFragmentActivity {


    @Override
    protected Fragment createFragment(){
        return new ViewRuleFragment();
    }
}
