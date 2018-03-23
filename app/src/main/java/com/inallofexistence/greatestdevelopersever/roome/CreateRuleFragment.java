package com.inallofexistence.greatestdevelopersever.roome;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.inallofexistence.greatestdevelopersever.roome.model.Rule;
import com.inallofexistence.greatestdevelopersever.roome.model.User2;

import java.util.ArrayList;

/**
 * Created by quintybox on 3/17/18.
 */

public class CreateRuleFragment extends Fragment implements View.OnClickListener{
    private Button submitButton;
    private EditText ruleNameView;
    private EditText ruleContentView;
    private Rule rule;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_create_rule, container, false);
        ruleNameView = v.findViewById(R.id.ruleNameTXT);
        ruleContentView = v.findViewById(R.id.ruleContentTXT);
        submitButton = v.findViewById(R.id.ruleSubmitBtn);
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

            case R.id.ruleSubmitBtn:

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                mDatabase.child("users").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        User2 tempUser = dataSnapshot.getValue(User2.class);
                        Log.d("helpMePlz", tempUser.hgID);

                        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                        ArrayList<User2> tempList = new ArrayList<>();
                        tempList.add(tempUser);
                        rule = new Rule(ruleNameView.getText().toString(), ruleContentView.getText().toString(),tempList,null);

                        String ruleUID = mDatabase.child("homegroups").child(tempUser.hgID).child("rules").push().getKey();
                        rule.UID = ruleUID;
                        mDatabase.child("homegroups").child(tempUser.hgID).child("rules").child(ruleUID).setValue(rule);


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
