package com.inallofexistence.greatestdevelopersever.roome.shame;

import android.support.v4.app.Fragment;

import com.inallofexistence.greatestdevelopersever.roome.SingleFragmentActivity;
import com.inallofexistence.greatestdevelopersever.roome.rules.ViewRuleFragment;


public class ViewInfractionActivity extends SingleFragmentActivity {


    @Override
    protected Fragment createFragment(){
        return new ViewInfractionFragment();
    }
}
