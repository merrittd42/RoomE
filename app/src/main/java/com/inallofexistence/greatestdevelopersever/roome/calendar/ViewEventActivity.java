package com.inallofexistence.greatestdevelopersever.roome.calendar;

import android.support.v4.app.Fragment;

import com.inallofexistence.greatestdevelopersever.roome.SingleFragmentActivity;
import com.inallofexistence.greatestdevelopersever.roome.calendar.ViewEventFragment;


public class ViewEventActivity extends SingleFragmentActivity {


    @Override
    protected Fragment createFragment(){
        return new ViewEventFragment();
    }
}
