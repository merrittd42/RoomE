package com.inallofexistence.greatestdevelopersever.roome.ledger;

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
import com.inallofexistence.greatestdevelopersever.roome.model.Bill;
import com.inallofexistence.greatestdevelopersever.roome.model.User2;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by quintybox on 3/17/18.
 */

public class LedgerFragment extends Fragment implements View.OnClickListener {

    private Button createButton;
    private Button refreshButton;
    private ListView listView;
    ArrayList<Bill> billList;
    private ArrayAdapter<String> adapter;
    private HashMap<String, String> billNameUIDMap;
    private String hgID;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ledger, container, false);
        createButton = v.findViewById(R.id.createBillBtn);
        getActivity().setTitle("What everyone owes");

        refreshButton = v.findViewById(R.id.refreshLedgerBtn);
        listView = v.findViewById(R.id.ledger);
        Bundle bundle = this.getArguments();
        hgID = bundle.getString("hgID");
        createButton.setOnClickListener(this);
        refreshButton.setOnClickListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                String billName = ((TextView)view).getText().toString();
                                                Log.d("HelpLz", billName);
                                                Log.d("Helplz", billNameUIDMap.get(billName));

                                                Intent intent = new Intent(getActivity(), ViewBillActivity.class);
                                                intent.putExtra("billName", billName);
                                                intent.putExtra("billUID", billNameUIDMap.get(billName));
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

            case R.id.refreshLedgerBtn:
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

                        mDatabase.child("homegroups").child(hgID).child("ledger").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Log.d("helpMePlz", "Got bill reference!");
                                billList = new ArrayList<Bill>();
                                ArrayList<String> billTitleList = new ArrayList<>();
                                adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, billTitleList);
                                listView.setAdapter(adapter);
                                billNameUIDMap = new HashMap<>();
                                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                                    Log.d("helpMePlz", "Loaded bill in!");

                                    Bill tempBill = postSnapshot.getValue(Bill.class);
                                    billList.add(tempBill);
                                }

                                for(Bill bill: billList){
                                    String billTitle = bill.billName;
                                    String billUID = bill.UID;
                                    billNameUIDMap.put(billTitle,billUID);
                                    Log.d("helpMePlz", billTitle);
                                    billTitleList.add(billTitle);
                                }

                                adapter.notifyDataSetChanged();

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Log.e("SignIn", "Error when signing in!", databaseError.toException());
                            }
                        });





                break;


            case R.id.createBillBtn:
                Intent newBillIntent = new Intent(getActivity(), CreateBillActivity.class);
                startActivity(newBillIntent);
                break;

        }
    }

}
