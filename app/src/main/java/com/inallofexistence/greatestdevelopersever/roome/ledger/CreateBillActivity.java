package com.inallofexistence.greatestdevelopersever.roome.ledger;

import android.support.v4.app.Fragment;

import com.inallofexistence.greatestdevelopersever.roome.SingleFragmentActivity;


public class CreateBillActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment(){
        return new CreateBillFragment();
    }
}
