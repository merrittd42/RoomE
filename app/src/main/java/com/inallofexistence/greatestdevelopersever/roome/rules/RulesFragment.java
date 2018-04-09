package com.inallofexistence.greatestdevelopersever.roome.rules;

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
import com.inallofexistence.greatestdevelopersever.roome.model.Rule;
import com.inallofexistence.greatestdevelopersever.roome.model.User2;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by quintybox on 3/17/18.
 */

public class RulesFragment extends Fragment implements View.OnClickListener {

    private Button createButton;
    private Button refreshButton;
    private ListView listView;
    ArrayList<Rule> ruleList;
    private ArrayAdapter<String> adapter;
    private HashMap<String, String> ruleNameUIDMap;
    private String hgID;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_rules_list, container, false);
        getActivity().setTitle("The Lawbook");

        createButton = v.findViewById(R.id.createRulesBtn);
        refreshButton = v.findViewById(R.id.refreshRulesBtn);
        listView = v.findViewById(R.id.ruleList);
        createButton.setOnClickListener(this);
        Bundle bundle = this.getArguments();
        hgID = bundle.getString("hgID");
        refreshButton.setOnClickListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String ruleName = ((TextView)view).getText().toString();
                Log.d("HelpLz", ruleName);
                Log.d("Helplz", ruleNameUIDMap.get(ruleName));

                Intent intent = new Intent(getActivity(), ViewRuleActivity.class);
                intent.putExtra("ruleName", ruleName);
                intent.putExtra("ruleUID", ruleNameUIDMap.get(ruleName));
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

            case R.id.refreshRulesBtn:
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

                        mDatabase.child("homegroups").child(hgID).child("rules").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Log.d("helpMePlz", "Got rule reference!");
                                ruleList = new ArrayList<Rule>();
                                ArrayList<String> ruleTitleList = new ArrayList<>();
                                adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, ruleTitleList);
                                listView.setAdapter(adapter);
                                ruleNameUIDMap = new HashMap<>();
                            for(DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                                Log.d("helpMePlz", "Loaded rule in!");

                                Rule tempRule = postSnapshot.getValue(Rule.class);
                                ruleList.add(tempRule);
                            }

                            for(Rule rule: ruleList){
                                String ruleTitle = rule.ruleName;
                                String ruleUID = rule.UID;
                                ruleNameUIDMap.put(ruleTitle,ruleUID);
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




                break;


            case R.id.createRulesBtn:
                Intent rulesIntent = new Intent(getActivity(), CreateRuleActivity.class);
                startActivity(rulesIntent);
                break;

        }
    }

}
