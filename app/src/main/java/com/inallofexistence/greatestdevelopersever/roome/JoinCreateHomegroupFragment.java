package com.inallofexistence.greatestdevelopersever.roome;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.inallofexistence.greatestdevelopersever.roome.model.Bill;
import com.inallofexistence.greatestdevelopersever.roome.model.Calendar;
import com.inallofexistence.greatestdevelopersever.roome.model.ChatMessages;
import com.inallofexistence.greatestdevelopersever.roome.model.Chatroom;
import com.inallofexistence.greatestdevelopersever.roome.model.Event;
import com.inallofexistence.greatestdevelopersever.roome.model.Homegroup;
import com.inallofexistence.greatestdevelopersever.roome.model.HouseRules;
import com.inallofexistence.greatestdevelopersever.roome.model.Infraction;
import com.inallofexistence.greatestdevelopersever.roome.model.Ledger;
import com.inallofexistence.greatestdevelopersever.roome.model.Rule;
import com.inallofexistence.greatestdevelopersever.roome.model.ShameWall;
import com.inallofexistence.greatestdevelopersever.roome.model.ShoppingList;
import com.inallofexistence.greatestdevelopersever.roome.model.User;
import com.inallofexistence.greatestdevelopersever.roome.model.User2;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by quintybox on 3/17/18.
 */

public class JoinCreateHomegroupFragment extends Fragment implements View.OnClickListener {

    private Button joinButton;
    private EditText hgIDEnter;
    private Button createButton;
    private DatabaseReference mDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_join_create_homegroup, container, false);

        joinButton = v.findViewById(R.id.submitBtn);
        createButton = v.findViewById(R.id.createHG);
        hgIDEnter = v.findViewById(R.id.hgIDtxt);
        joinButton.setOnClickListener(this);
        createButton.setOnClickListener(this);

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){

            case R.id.submitBtn:

                final String hgID = hgIDEnter.getText().toString();

                final FirebaseUser userSub = FirebaseAuth.getInstance().getCurrentUser();
                final User2 userSub2 = new User2(userSub.getEmail(), userSub.getDisplayName(), hgID, userSub.getUid());
                mDatabase = FirebaseDatabase.getInstance().getReference();
                mDatabase.child("homegroups").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if(dataSnapshot.hasChild(hgID)){
                            ArrayList<User2> users = (ArrayList) dataSnapshot.child(hgID).child("userList").getValue();
                            users.add(userSub2);
                            mDatabase.child("homegroups").child(hgID).child("userList").setValue(users);

                        }else{
                            Log.d("HGerror", "Homegroup doesn't exist!");
                        }
                        Log.d("SignIn", "User2 logged in good!");
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e("SignIn", "Error when signing in!", databaseError.toException());
                    }
                });




                mDatabase.child("users").child(userSub2.UID).setValue(userSub2);

                startActivity(new Intent(this.getActivity(), HGMenuActivity.class));
                this.getActivity().finish();

                break;

            case R.id.createHG:

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                User2 user2 = new User2(user.getEmail(), user.getDisplayName(), null, user.getUid());
                ArrayList<User2> initList = new ArrayList<User2>();
                initList.add(user2);
                Homegroup hg = new Homegroup(new Ledger(new ArrayList<Bill>()),new Calendar(new ArrayList<Event>()), new ShoppingList(new ArrayList<String>()), new HouseRules(new ArrayList<Rule>()), new ShameWall(new ArrayList<Infraction>()), initList, new Chatroom(new ArrayList<ChatMessages>()));
                mDatabase = FirebaseDatabase.getInstance().getReference();
               String hgIDSetter = mDatabase.child("homegroups").push().getKey();
               mDatabase.child("homegroups").child(hgIDSetter).setValue(hg);

               user2.hgID = hgIDSetter;
               mDatabase.child("users").child(user2.UID).setValue(user2);
                Intent intent = new Intent(this.getActivity(), HGMenuActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

                this.getActivity().finish();

                break;
        }
    }



}
