package com.inallofexistence.greatestdevelopersever.roome.calendar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.inallofexistence.greatestdevelopersever.roome.R;
import com.inallofexistence.greatestdevelopersever.roome.model.Event;

/**
 * Created by quintybox on 3/17/18.
 */

public class ViewEventFragment extends Fragment implements View.OnClickListener{
    private Button deleteButton;
    private TextView name;
    private TextView startTime;
    private TextView endTime;
    private TextView startDate;
    private TextView endDate;
    private String hgID;
    private String eventName;
    private String eventStartTime;
    private String eventEndTime;
    private String eventStartDate;
    private String eventEndDate;
    private String eventID;

    private Event event;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_view_event, container, false);
        Bundle bundle = this.getArguments();
        eventName = bundle.getString("eventName");

        eventID = bundle.getString("eventUID");
        hgID = bundle.getString("hgID");

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("homegroups").child(hgID).child("calendar").child(eventID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Event tempEvent = dataSnapshot.getValue(Event.class);
                Log.d("viewEvent", String.valueOf(tempEvent.startTime == null));

                startTime.setText(tempEvent.startTime);
                startDate.setText(tempEvent.startDate);
                endTime.setText(tempEvent.endTime);
                endDate.setText(tempEvent.endDate);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("SignIn", "Error when signing in!", databaseError.toException());
            }
        });



        Log.d("Intent test", eventName);

        deleteButton = v.findViewById(R.id.deleteEventBtn);
        name = v.findViewById(R.id.vEventNameTXT);
        startTime = v.findViewById(R.id.eventStartTime);
        startDate = v.findViewById(R.id.eventStartDate);
        endTime = v.findViewById(R.id.eventEndTime);
        endDate = v.findViewById(R.id.eventEndDate);

        deleteButton.setOnClickListener(this);
        name.setText(eventName);
        startTime.setText(eventStartTime);
        endTime.setText(eventEndTime);
        startDate.setText(eventStartDate);
        endDate.setText(eventEndDate);

        return v;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        Log.d("viewEvent", eventID);
        Log.d("viewEvent", hgID);
        mDatabase.child("homegroups").child(hgID).child("calendar").child(eventID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Event tempEvent = dataSnapshot.getValue(Event.class);
                Log.d("viewEvent", tempEvent.eventName);

                name.setText(tempEvent.eventName);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("SignIn", "Error when signing in!", databaseError.toException());
            }
        });
        Log.d("UserInfo", user.getDisplayName());


    }

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){

            case R.id.deleteEventBtn:
                final DatabaseReference mDatabase3 = FirebaseDatabase.getInstance().getReference();
                mDatabase3.child("homegroups").child(hgID).child("calendar").child(eventID).setValue(null);
                getActivity().finish();

                break;


        }
    }

}
