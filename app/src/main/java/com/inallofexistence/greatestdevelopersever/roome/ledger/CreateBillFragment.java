package com.inallofexistence.greatestdevelopersever.roome.ledger;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

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
import java.util.List;

/**
 * Created by quintybox on 3/17/18.
 */

public class CreateBillFragment extends Fragment implements View.OnClickListener{
    private Button submitButton;
    private EditText billNameView;
    private EditText billAmountView;
    private Bill bill;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_create_bill, container, false);
        billNameView = v.findViewById(R.id.billNameTXT);
        billAmountView = v.findViewById(R.id.billAmount);
        submitButton = v.findViewById(R.id.billSubmitBtn);
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

            case R.id.billSubmitBtn:

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                mDatabase.child("users").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        final User2 tempUser = dataSnapshot.getValue(User2.class);
                        Log.d("helpMePlz", tempUser.hgID);

                        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        final String username = user.getDisplayName();
                        final List<String> stillOwes = new ArrayList<String>();
                        FirebaseDatabase.getInstance().getReference().child("homegroups").child(tempUser.hgID).child("userList")
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                            User2 user = snapshot.getValue(User2.class);
                                            stillOwes.add(user.email);
                                            Log.d("viewBillD", "Adding user " + user.email);
                                        }
                                        stillOwes.remove(username);
                                        bill = new Bill(billNameView.getText().toString(), Double.parseDouble(
                                                billAmountView.getText().toString()), username, stillOwes ,null);
                                        Log.d("viewBillD", stillOwes.toString());

                                        String billUID = mDatabase.child("homegroups").child(tempUser.hgID).child("ledger").push().getKey();
                                        bill.UID = billUID;
                                        mDatabase.child("homegroups").child(tempUser.hgID).child("ledger").child(billUID).setValue(bill);
                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                    }
                                });



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
