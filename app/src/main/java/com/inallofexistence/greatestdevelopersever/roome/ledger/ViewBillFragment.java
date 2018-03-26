package com.inallofexistence.greatestdevelopersever.roome.ledger;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.auth.data.model.User;
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
import java.util.Map;

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
    private ListView listView;
    ArrayList<String> owesList;
    Map<String, String> owesUIDMap;
    private ArrayAdapter<String> adapter;

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
        amount = v.findViewById(R.id.billAmountTXT);
        listView = v.findViewById(R.id.owesList);
        //adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, owesList);
        //listView.setAdapter(adapter);
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        mDatabase.child("users").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                User2 tempUser = dataSnapshot.getValue(User2.class);
                Log.d("helpMePlz", tempUser.hgID);
                hgID = tempUser.hgID;
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                mDatabase.child("homegroups").child(tempUser.hgID).child("ledger").child(billID).child("stillOwes").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.d("helpMePlz", "Got stillOwes reference!");
                        owesList = new ArrayList<String>();
                        ArrayList<String> ruleTitleList = new ArrayList<>();
                        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, owesList);
                        listView.setAdapter(adapter);
                        owesUIDMap = new HashMap<>();
                        for(DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                            Log.d("helpMePlz", "Loaded rule in!");

                            String owes = postSnapshot.getValue(String.class);
                            owesList.add(owes);
                            owesUIDMap.put(owes, postSnapshot.getKey());
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

        final Context context = this.getContext();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                final String itemName = ((TextView)view).getText().toString();

                                                LayoutInflater li = LayoutInflater.from(context);
                                                View promptsView = li.inflate(R.layout.prompt_ledger, null);
                                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                                        context);
                                                alertDialogBuilder.setView(promptsView);
                                                alertDialogBuilder
                                                        .setCancelable(true)
                                                        .setPositiveButton("Yes",
                                                                new DialogInterface.OnClickListener() {
                                                                    public void onClick(DialogInterface dialog,int id) {
                                                                        final DatabaseReference mDatabase3 = FirebaseDatabase.getInstance().getReference();
                                                                        mDatabase3.child("homegroups").child(hgID).child("ledger").child(billID).child("stillOwes").child(owesUIDMap.get(itemName)).setValue(null);
                                                                        owesList.remove(itemName);
                                                                        adapter.notifyDataSetChanged();
                                                                    }
                                                                })
                                                        .setNegativeButton("No",
                                                                new DialogInterface.OnClickListener() {
                                                                    public void onClick(DialogInterface dialog,int id) {
                                                                       dialog.cancel();
                                                                    }
                                                                });
                                                AlertDialog alertDialog = alertDialogBuilder.create();

                                                alertDialog.show();
                                            }
                                        }
        );
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
                Log.d("viewBill", String.valueOf(tempBill.billAmount == null));

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
