package com.inallofexistence.greatestdevelopersever.roome;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.inallofexistence.greatestdevelopersever.roome.model.User2;

import java.util.Arrays;

public class SignInActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 123;
    private DatabaseReference mDatabase;

    @Override
    protected void onStart(){
        super.onStart();

        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            //User2 either never joined/created a group, or has a group
            redirectToHGCreationOrHGMenu();
        } else {
            // not signed in
            startActivityForResult(
                    // Get an instance of AuthUI based on the default app
                    AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(Arrays.asList(new AuthUI.IdpConfig.EmailBuilder().build(), new AuthUI.IdpConfig.GoogleBuilder().build())).build(),
                    RC_SIGN_IN);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // RC_SIGN_IN is the request code you passed into startActivityForResult(...) when starting the sign in flow.
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            // Successfully signed in
            if (resultCode == RESULT_OK) {
                //Now need to join or make or get redirected to a group
               redirectToHGCreationOrHGMenu();
            } else {
                // Sign in failed
                if (response == null) {
                    return;
                }

                if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
                    return;
                }
                Log.e("SignInError", "Sign-in error: ", response.getError());
            }
        }
    }

    private void redirectToHGCreationOrHGMenu(){
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(user.getUid())){

                    User2 user2 = dataSnapshot.getValue(User2.class);
                    if(user2.hgID == null){
                        //Created account but never joined up with a group
                        startActivity(new Intent(SignInActivity.this, JoinCreateHomegroupActivity.class));
                    }else{
                        //Grouped user, happy path time!
                        startActivity(new Intent(SignInActivity.this, HGMenuActivity.class));
                    }

                }else{
                    //In this case, the user is not in the user node in Firebase, so we make a new user and have them make/join a group
                    createUserInFirebase(user);
                    startActivity(new Intent(SignInActivity.this, JoinCreateHomegroupActivity.class));
                }
                Log.d("SignIn", "User2 logged in good!");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        finish();
    }

    private void createUserInFirebase(FirebaseUser user){

        //We use a null hgID value as there should be no way they are in a homegroup
        User2 mUser = new User2(user.getEmail(), user.getDisplayName(), null);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").child(user.getUid()).setValue(mUser);
    }
}
