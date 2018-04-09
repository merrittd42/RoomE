package com.inallofexistence.greatestdevelopersever.roome.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.inallofexistence.greatestdevelopersever.roome.R;
import com.inallofexistence.greatestdevelopersever.roome.calendar.CreateEventActivity;
import com.inallofexistence.greatestdevelopersever.roome.calendar.ViewEventActivity;
import com.inallofexistence.greatestdevelopersever.roome.model.Event;
import com.inallofexistence.greatestdevelopersever.roome.model.User2;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by quintybox on 3/17/18.
 */

public class CalendarFragment extends Fragment implements View.OnClickListener {

    private Button createButton;
    private Button refreshButton;
    private ListView listView;
    ArrayList<Event> eventList;
    private ArrayAdapter<String> adapter;
    private HashMap<String, String> eventNameUIDMap;
    private String hgID;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_calendar, container, false);
        createButton = v.findViewById(R.id.createEventBtn);
        refreshButton = v.findViewById(R.id.refreshCalendarBtn);
        getActivity().setTitle("Here's What's Going On!");

        listView = v.findViewById(R.id.calendar);
        Bundle bundle = this.getArguments();
        hgID = bundle.getString("hgID");
        createButton.setOnClickListener(this);
        refreshButton.setOnClickListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                String eventName = ((TextView)view).getText().toString();
                                                Log.d("HelpLz", eventName);
                                                Log.d("Helplz", eventNameUIDMap.get(eventName));

                                                Intent intent = new Intent(getActivity(), ViewEventActivity.class);
                                                intent.putExtra("eventName", eventName);
                                                intent.putExtra("eventUID", eventNameUIDMap.get(eventName));
                                                intent.putExtra("hgID", hgID);
                                                startActivity(intent);
                                            }
                                        }
        );
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

        switch(v.getId()){

            case R.id.refreshCalendarBtn:
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                        mDatabase.child("homegroups").child(hgID).child("calendar").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Log.d("helpMePlz", "Got event reference!");
                                eventList = new ArrayList<Event>();
                                ArrayList<String> eventTitleList = new ArrayList<>();
                                adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, eventTitleList);
                                listView.setAdapter(adapter);
                                eventNameUIDMap = new HashMap<>();
                                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                                    Log.d("helpMePlz", "Loaded event in!");

                                    Event tempEvent = postSnapshot.getValue(Event.class);
                                    eventList.add(tempEvent);
                                }

                                for(Event event: eventList){
                                    String eventTitle = event.eventName;
                                    String eventUID = event.UID;
                                    eventNameUIDMap.put(eventTitle,eventUID);
                                    Log.d("helpMePlz", eventTitle);
                                    eventTitleList.add(eventTitle);
                                }

                                adapter.notifyDataSetChanged();

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Log.e("SignIn", "Error when signing in!", databaseError.toException());
                            }
                        });



                break;


            case R.id.createEventBtn:
                Intent newEventIntent = new Intent(getActivity(), CreateEventActivity.class);
                startActivity(newEventIntent);
                break;

        }
    }

}
