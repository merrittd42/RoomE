package com.inallofexistence.greatestdevelopersever.roome;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class JoinCreateHomegroupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_create_homegroup);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        Log.d("HEY", user.getDisplayName() );
    }
}
