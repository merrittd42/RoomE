package com.inallofexistence.greatestdevelopersever.roome.shame;

import android.support.v4.app.Fragment;

import com.inallofexistence.greatestdevelopersever.roome.SingleFragmentActivity;

public class ShameActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment(){
        return new ShameFragment();
    }
}
