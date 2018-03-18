package com.inallofexistence.greatestdevelopersever.roome;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by quintybox on 3/17/18.
 */

public class CalendarFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_join_create_homegroup, container, false);
        setHasOptionsMenu(true);
        return v;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Log.d("UserInfo", user.getDisplayName());


    }

    @Override
    public void onResume(){
        super.onResume();
    }

}
