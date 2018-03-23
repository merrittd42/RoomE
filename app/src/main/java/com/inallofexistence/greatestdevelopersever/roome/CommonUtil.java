package com.inallofexistence.greatestdevelopersever.roome;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ValueEventListener;
import com.inallofexistence.greatestdevelopersever.roome.model.User2;

/**
 * Created by quintybox on 3/22/18.
 */



public class CommonUtil {

    private DatabaseReference mDatabase;
    private DatabaseReference homeGroup;
    private String hgID;
    private User2 user;

    public DatabaseReference getHomegroup(){

        FirebaseUser userSub = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase.child("users").child(userSub.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User2.class);
                hgID = user.hgID;

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return mDatabase.child("homegroups").child(hgID);
    }
}
