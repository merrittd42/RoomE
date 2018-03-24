package com.inallofexistence.greatestdevelopersever.roome.calendar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.inallofexistence.greatestdevelopersever.roome.R;
import com.inallofexistence.greatestdevelopersever.roome.model.Event;
import com.inallofexistence.greatestdevelopersever.roome.model.User2;

/**
 * Created by quintybox on 3/17/18.
 */

public class CreateEventFragment extends Fragment implements View.OnClickListener{
    private Button submitButton;
    private EditText eventNameView;
    private EditText eventStartTimeView;
    private EditText eventEndTimeView;
    private EditText eventStartDateView;
    private EditText eventEndDateView;
    private Event event;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_create_event, container, false);
        eventNameView = v.findViewById(R.id.eventNameTXT);
        eventStartTimeView = v.findViewById(R.id.startTime);
        eventEndTimeView = v.findViewById(R.id.endTime);
        eventStartDateView = v.findViewById(R.id.startDate);
        eventEndDateView = v.findViewById(R.id.endDate);
        submitButton = v.findViewById(R.id.eventSubmitBtn);
        submitButton.setOnClickListener(this);

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

    @Override
    public void onClick(View v){
        switch (v.getId()){

            case R.id.eventSubmitBtn:

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                mDatabase.child("users").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        User2 tempUser = dataSnapshot.getValue(User2.class);
                        Log.d("helpMePlz", tempUser.hgID);

                        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                        event = new Event(eventNameView.getText().toString(), eventStartDateView.getText().toString(),
                                eventStartTimeView.getText().toString(), eventEndDateView.getText().toString(),
                                eventStartTimeView.getText().toString(), null);

                        String eventUID = mDatabase.child("homegroups").child(tempUser.hgID).child("calendar").push().getKey();
                        event.UID = eventUID;
                        mDatabase.child("homegroups").child(tempUser.hgID).child("calendar").child(eventUID).setValue(event);


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e("SignIn", "Error when signing in!", databaseError.toException());
                    }
                });


                this.getActivity().finish();
                break;
        }
    }

}
