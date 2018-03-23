package com.inallofexistence.greatestdevelopersever.roome;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.inallofexistence.greatestdevelopersever.roome.model.HouseRules;
import com.inallofexistence.greatestdevelopersever.roome.model.Rule;
import com.inallofexistence.greatestdevelopersever.roome.model.User2;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by quintybox on 3/17/18.
 */

public class RulesFragment extends Fragment implements View.OnClickListener {

    private Button createButton;
    private Button refreshButton;
    private ListView listView;
    ArrayList<Rule> ruleList;
    private ArrayAdapter<String> adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_rules_list, container, false);
        createButton = v.findViewById(R.id.createRulesBtn);
        refreshButton = v.findViewById(R.id.refreshRulesBtn);
        listView = v.findViewById(R.id.ruleList);
        createButton.setOnClickListener(this);
        refreshButton.setOnClickListener(this);
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

            case R.id.refreshRulesBtn:
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                mDatabase.child("users").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        User2 tempUser = dataSnapshot.getValue(User2.class);
                        Log.d("helpMePlz", tempUser.hgID);

                        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                        mDatabase.child("homegroups").child(tempUser.hgID).child("rules").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Log.d("helpMePlz", "Got rule reference!");
                                ruleList = new ArrayList<Rule>();
                                ArrayList<String> ruleTitleList = new ArrayList<>();
                                adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, ruleTitleList);
                                listView.setAdapter(adapter);
                            for(DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                                Log.d("helpMePlz", "Loaded rule in!");

                                Rule tempRule = postSnapshot.getValue(Rule.class);
                                ruleList.add(tempRule);
                            }

                            for(Rule rule: ruleList){
                                String ruleTitle = rule.ruleName;
                                Log.d("helpMePlz", ruleTitle);
                                ruleTitleList.add(ruleTitle);
                            }

                            adapter.notifyDataSetChanged();

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Log.e("SignIn", "Error when signing in!", databaseError.toException());
                            }
                        });


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e("SignIn", "Error when signing in!", databaseError.toException());
                    }
                });

                break;


            case R.id.createRulesBtn:
                Intent rulesIntent = new Intent(getActivity(), CreateRuleActivity.class);
                startActivity(rulesIntent);
                break;

        }
    }

}
