package com.inallofexistence.greatestdevelopersever.roome.shoppinglist;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.inallofexistence.greatestdevelopersever.roome.R;
import com.inallofexistence.greatestdevelopersever.roome.SingleFragmentActivity;
import com.inallofexistence.greatestdevelopersever.roome.rules.CreateRuleFragment;

import java.util.ArrayList;

public class ShoppingListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment(){
        return new ShoppingListFragment();
    }

}
