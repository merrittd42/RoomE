package com.inallofexistence.greatestdevelopersever.roome.ledger;

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
import com.inallofexistence.greatestdevelopersever.roome.model.Bill;
import com.inallofexistence.greatestdevelopersever.roome.model.User2;

import java.util.ArrayList;

/**
 * Created by quintybox on 3/17/18.
 */

public class ViewBillFragment extends Fragment implements View.OnClickListener{
    private Button deleteButton;
    private TextView name;
    private TextView amount;
    private String hgID;
    private String billName;
    private String billID;

    private Bill bill;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_view_bill, container, false);
        Bundle bundle = this.getArguments();
        billName = bundle.getString("billName");
        billID = bundle.getString("billUID");
        hgID = bundle.getString("hgID");
        Log.d("Intent test", billName);

        deleteButton = v.findViewById(R.id.deleteBillBtn);
        name = v.findViewById(R.id.vBillNameTXT);
        amount = v.findViewById(R.id.billAmount);

        deleteButton.setOnClickListener(this);
        name.setText(billName);

        return v;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        Log.d("viewBill", billID);
        Log.d("viewBill", hgID);
        mDatabase.child("homegroups").child(hgID).child("ledger").child(billID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Bill tempBill = dataSnapshot.getValue(Bill.class);
                Log.d("viewBill", tempBill.billName);

                amount.setText(Double.toString(tempBill.billAmount));

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

            case R.id.deleteBillBtn:
                final DatabaseReference mDatabase3 = FirebaseDatabase.getInstance().getReference();
                mDatabase3.child("homegroups").child(hgID).child("ledger").child(billID).setValue(null);
                getActivity().finish();

                break;


        }
    }

}
