package com.inallofexistence.greatestdevelopersever.roome;

import android.content.Intent;
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
import com.inallofexistence.greatestdevelopersever.roome.chatroom.ChatActivity;
import com.inallofexistence.greatestdevelopersever.roome.model.Calendar;
import com.inallofexistence.greatestdevelopersever.roome.model.User2;
import com.inallofexistence.greatestdevelopersever.roome.rules.RulesActivity;
import com.inallofexistence.greatestdevelopersever.roome.shame.ShameActivity;
import com.inallofexistence.greatestdevelopersever.roome.ledger.LedgerActivity;
import com.inallofexistence.greatestdevelopersever.roome.shoppinglist.ShoppingListActivity;
import com.inallofexistence.greatestdevelopersever.roome.calendar.CalendarActivity;

/**
 * Created by quintybox on 3/17/18.
 */

public class HGMenuFragment extends Fragment implements View.OnClickListener {

    private Button shameButton;
    private Button ruleButton;
    private Button chatButton;
    private Button calendarButton;
    private Button ledgerButton;
    private Button shoppingButton;
    private Button signOutButton;
    private TextView hgIDField;
    private String hgID;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_hgmenu, container, false);
        getActivity().setTitle("Main Menu");
        hgIDField = v.findViewById(R.id.hgIDTXT);
        shameButton = v.findViewById(R.id.shameBtn);
        ruleButton = v.findViewById(R.id.rulesBtn);
        chatButton = v.findViewById(R.id.chatBtn);
        ledgerButton = v.findViewById(R.id.ledgerBtn);
        calendarButton = v.findViewById(R.id.calendarBtn);
        shoppingButton = v.findViewById(R.id.shopBtn);
        signOutButton = v.findViewById(R.id.signOutbtn);
        signOutButton.setOnClickListener(this);
        shameButton.setOnClickListener(this);
        ruleButton.setOnClickListener(this);
        chatButton.setOnClickListener(this);
        ledgerButton.setOnClickListener(this);
        shoppingButton.setOnClickListener(this);
        calendarButton.setOnClickListener(this);
        return v;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                User2 tempUser = dataSnapshot.getValue(User2.class);
                Log.d("helpMePlz", tempUser.hgID);
                hgIDField.setText(R.string.hg_homegroup_is + tempUser.hgID);
                hgID = tempUser.hgID;

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

            case R.id.shameBtn:
                Intent shameIntent = new Intent(getActivity(), ShameActivity.class);
                shameIntent.putExtra("hgID", hgID);
                shameIntent.putExtra("shameFlag", "Hey, if you read this line, you're really cool. I really appreciate you reading this code. Good for you B)");

                startActivity(shameIntent);
                break;

            case R.id.chatBtn:
                Intent chatIntent = new Intent(getActivity(), ChatActivity.class);
                chatIntent.putExtra("hgID", hgID);
                chatIntent.putExtra("chatFlag", "Hey, if you read this line, you're really cool. I really appreciate you reading this code. Good for you B)");
                startActivity(chatIntent);
                break;

            case R.id.rulesBtn:
                Intent rulesIntent = new Intent(getActivity(), RulesActivity.class);
                rulesIntent.putExtra("ruleFlag", "This one too man");
                rulesIntent.putExtra("hgID", hgID);
                startActivity(rulesIntent);
                break;

            case R.id.ledgerBtn:
                Intent ledgerIntent = new Intent(getActivity(), LedgerActivity.class);
                ledgerIntent.putExtra("hgID", hgID);
                ledgerIntent.putExtra("billFlag", "Hey, if you read this line, you're really cool. I really appreciate you reading this code. Good for you B)");

                startActivity(ledgerIntent);
                break;

            case R.id.calendarBtn:
                Intent calIntent = new Intent(getActivity(), CalendarActivity.class);
                calIntent.putExtra("hgID", hgID);
                calIntent.putExtra("eventFlag", "Hey, if you read this line, you're really cool. I really appreciate you reading this code. Good for you B)");

                startActivity(calIntent);
                break;

            case R.id.shopBtn:
                Intent shopIntent = new Intent(getActivity(), ShoppingListActivity.class);
                shopIntent.putExtra("hgID", hgID);
                shopIntent.putExtra("shopFlag", "Hey, if you read this line, you're really cool. I really appreciate you reading this code. Good for you B)");
                startActivity(shopIntent);
                break;

            case R.id.signOutbtn:
                FirebaseAuth.getInstance().signOut();
                this.getActivity().finish();
                break;

            //-L7qP02A2FrpBYVFxmG7
        }
    }

}
