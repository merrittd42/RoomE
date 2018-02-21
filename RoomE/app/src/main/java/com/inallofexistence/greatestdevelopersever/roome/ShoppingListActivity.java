package com.inallofexistence.greatestdevelopersever.roome;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class ShoppingListActivity extends FragmentActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("lifeInfo", "Starting onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);
    }

    @Override
    protected void onResume(){
        Log.d("lifeInfo", "Starting onResume, launching activity");

        super.onResume();

    }
}
