package com.inallofexistence.greatestdevelopersever.roome;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;


public abstract class SingleFragmentActivity extends AppCompatActivity {

    protected abstract Fragment createFragment();

    @LayoutRes
    protected int getLayoutResId() {
        return R.layout.activity_fragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        startFragWithData();


    }

    private void startFragWithData(){

        Intent intent = getIntent();
        String ruleName = intent.getStringExtra("ruleName");
        String ruleUID = intent.getStringExtra("ruleUID");
        String hgID = intent.getStringExtra("hgID");
        String billID = intent.getStringExtra("billUID");
        String billName = intent.getStringExtra("billName");
        String eventID = intent.getStringExtra("eventUID");
        String eventName = intent.getStringExtra("eventName");

        Bundle bundle = new Bundle();
        if(ruleName != null && ruleUID != null && hgID != null) {


            bundle.putString("ruleName", ruleName);
            bundle.putString("ruleUID", ruleUID);
            bundle.putString("hgID", hgID);

            FragmentManager fm = getSupportFragmentManager();
            Fragment fragment = fm.findFragmentById(R.id.fragment_container);

            if (fragment == null) {
                fragment = createFragment();
                fragment.setArguments(bundle);
                fm.beginTransaction()
                        .add(R.id.fragment_container, fragment)
                        .commit();
            }
        }else if(hgID != null && billID != null && billName != null){
            bundle.putString("billName", billName);
            bundle.putString("billUID", billID);
            bundle.putString("hgID", hgID);

            FragmentManager fm = getSupportFragmentManager();
            Fragment fragment = fm.findFragmentById(R.id.fragment_container);

            if (fragment == null) {
                fragment = createFragment();
                fragment.setArguments(bundle);
                fm.beginTransaction()
                        .add(R.id.fragment_container, fragment)
                        .commit();
            }


        }else if(eventName != null && eventID != null && hgID != null){
            bundle.putString("eventName", eventName);
            bundle.putString("eventUID", eventID);
            bundle.putString("hgID", hgID);

            FragmentManager fm = getSupportFragmentManager();
            Fragment fragment = fm.findFragmentById(R.id.fragment_container);

            if (fragment == null) {
                fragment = createFragment();
                fragment.setArguments(bundle);
                fm.beginTransaction()
                        .add(R.id.fragment_container, fragment)
                        .commit();
            }


        }else{

            FragmentManager fm = getSupportFragmentManager();
            Fragment fragment = fm.findFragmentById(R.id.fragment_container);

            if (fragment == null) {
                fragment = createFragment();
                fm.beginTransaction()
                        .add(R.id.fragment_container, fragment)
                        .commit();

            }

        }

    }
}
