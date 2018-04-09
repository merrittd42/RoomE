package com.inallofexistence.greatestdevelopersever.roome.rules;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.inallofexistence.greatestdevelopersever.roome.R;
import com.inallofexistence.greatestdevelopersever.roome.model.Rule;
import com.inallofexistence.greatestdevelopersever.roome.model.User2;

import java.util.ArrayList;

/**
 * Created by quintybox on 3/17/18.
 */

public class ViewRuleFragment extends Fragment implements View.OnClickListener{
    private Button yeaButton;
    private Button nayButton;
    private Button deleteButton;
    private TextView name;
    private TextView desc;
    private TextView supporters;
    private String hgID;
    private String ruleName;
    private String ruleID;

    private Rule rule;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_view_rule, container, false);
        Bundle bundle = this.getArguments();
        ruleName = bundle.getString("ruleName");
        ruleID = bundle.getString("ruleUID");
        hgID = bundle.getString("hgID");
        Log.d("Intent test", ruleName);

        yeaButton = v.findViewById(R.id.yeaButton);
        nayButton = v.findViewById(R.id.nayRuleBtn);
        deleteButton = v.findViewById(R.id.deleteRuleBtn);
        name = v.findViewById(R.id.vRuleNameTXT);
        desc = v.findViewById(R.id.ruleDescTXT);
        supporters = v.findViewById(R.id.supporterTXT);

        yeaButton.setOnClickListener(this);
        nayButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);
        name.setText(ruleName);



        return v;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        Log.d("viewRule", ruleID);
        Log.d("viewRule", hgID);
        mDatabase.child("homegroups").child(hgID).child("rules").child(ruleID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Rule tempRule = dataSnapshot.getValue(Rule.class);
                Log.d("viewRule", tempRule.ruleName);
                getActivity().setTitle(tempRule.ruleName);

                desc.setText(tempRule.ruleContent);
                String userList = "Supporting users: 0";
                if(tempRule.yesVoters != null){
                     userList = "Supporting users: " + tempRule.yesVoters.size();
                }else{
                     userList = "Supporting users: 0" ;
                }
                supporters.setText(userList);
                Log.d("viewRule", userList);

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


            case R.id.yeaButton:
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                mDatabase.child("homegroups").child(hgID).child("rules").child(ruleID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(final DataSnapshot dataSnapshot) {

                        final Rule tempRule = dataSnapshot.getValue(Rule.class);
                        boolean change = true;

                        if(tempRule.yesVoters != null) {
                            for (User2 user : tempRule.yesVoters) {

                                if (user.UID.equals(FirebaseAuth.getInstance().getCurrentUser().getUid().toString())) {
                                    change = false;
                                }
                            }

                            if (change) {
                                String numOfSup = supporters.getText().toString();
                                Integer intOfSup = Integer.parseInt(numOfSup.substring(numOfSup.lastIndexOf(" ") + 1));
                                intOfSup++;
                                supporters.setText("Supporting users: " + String.valueOf(intOfSup));

                                mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {

                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot1) {
                                        User2 user2 = dataSnapshot1.getValue(User2.class);
                                        ArrayList<User2> tempList = tempRule.yesVoters;
                                        tempList.add(user2);
                                        rule = new Rule(tempRule.ruleName, tempRule.ruleContent, tempList, tempRule.UID);
                                        mDatabase.child("homegroups").child(hgID).child("rules").child(ruleID).setValue(rule);
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError e) {

                                    }

                                });
                            }


                        }else{
                            String numOfSup = supporters.getText().toString();
                            Integer intOfSup = Integer.parseInt(numOfSup.substring(numOfSup.lastIndexOf(" ") + 1));
                            intOfSup++;
                            supporters.setText("Supporting users: " + String.valueOf(intOfSup));

                            mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {

                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot1) {
                                    User2 user2 = dataSnapshot1.getValue(User2.class);
                                    ArrayList<User2> tempList = new ArrayList<>();
                                    tempList.add(user2);
                                    rule = new Rule(tempRule.ruleName, tempRule.ruleContent, tempList, tempRule.UID);
                                    mDatabase.child("homegroups").child(hgID).child("rules").child(ruleID).setValue(rule);
                                }

                                @Override
                                public void onCancelled(DatabaseError e) {

                                }

                            });
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e("SignIn", "Error when signing in!", databaseError.toException());
                    }
                });

                break;

            case R.id.nayRuleBtn:
                final FirebaseUser user3 = FirebaseAuth.getInstance().getCurrentUser();
                final DatabaseReference mDatabase2 = FirebaseDatabase.getInstance().getReference();
                mDatabase2.child("homegroups").child(hgID).child("rules").child(ruleID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(final DataSnapshot dataSnapshot) {

                        final Rule tempRule = dataSnapshot.getValue(Rule.class);
                        boolean change = false;
                        int indexOfRemoval = 0;
                        if(tempRule.yesVoters != null) {
                            for (int i = 0; i < tempRule.yesVoters.size(); i++) {

                                if (tempRule.yesVoters.get(i).UID.equals(FirebaseAuth.getInstance().getCurrentUser().getUid().toString())) {
                                    change = true;
                                    indexOfRemoval = i;
                                }
                            }

                            if (change) {
                                String numOfSup = supporters.getText().toString();
                                Integer intOfSup = Integer.parseInt(numOfSup.substring(numOfSup.lastIndexOf(" ") + 1));
                                intOfSup--;
                                supporters.setText("Supporting users: " + String.valueOf(intOfSup));
                                tempRule.yesVoters.remove(indexOfRemoval);
                                mDatabase2.child("homegroups").child(hgID).child("rules").child(ruleID).setValue(tempRule);
                            }
                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e("SignIn", "Error when signing in!", databaseError.toException());
                    }
                });

                break;

            case R.id.deleteRuleBtn:
                final DatabaseReference mDatabase3 = FirebaseDatabase.getInstance().getReference();
                mDatabase3.child("homegroups").child(hgID).child("rules").child(ruleID).setValue(null);
                getActivity().finish();

                break;


        }
    }

}
