package com.inallofexistence.greatestdevelopersever.roome;

import android.support.v4.app.Fragment;

public class LedgerActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment(){
        return new LedgerFragment();
    }
}
