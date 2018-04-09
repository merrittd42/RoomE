package com.inallofexistence.greatestdevelopersever.roome.shoppinglist;

import android.content.Context;
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
import com.inallofexistence.greatestdevelopersever.roome.model.User2;
import android.app.AlertDialog;
import android.widget.EditText;
import android.content.DialogInterface;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by quintybox on 3/17/18.
 */

public class ShoppingListFragment extends Fragment implements View.OnClickListener {

    private Button createButton;
    private Button refreshButton;
    private ListView listView;
    ArrayList<String> itemList;
    private ArrayAdapter<String> adapter;
    private HashMap<String, String> itemNameUIDMap;
    private String hgID;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_shoppinglist, container, false);
        createButton = v.findViewById(R.id.createItemBtn);
        getActivity().setTitle("Shopping List");

        refreshButton = v.findViewById(R.id.refreshShoppinglistBtn);
        listView = v.findViewById(R.id.shoppinglist);
        Bundle bundle = this.getArguments();
        hgID = bundle.getString("hgID");
        createButton.setOnClickListener(this);
        refreshButton.setOnClickListener(this);
        final Context context = this.getContext();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                final String itemName = ((TextView)view).getText().toString();

                                                LayoutInflater li = LayoutInflater.from(context);
                                                View promptsView = li.inflate(R.layout.prompt_shoppinglist, null);
                                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                                        context);
                                                alertDialogBuilder.setView(promptsView);
                                                final EditText userInput = (EditText) promptsView
                                                        .findViewById(R.id.editTextDialogUserInput);
                                                userInput.setText(itemName);
                                                alertDialogBuilder
                                                        .setCancelable(true)
                                                        .setPositiveButton("OK",
                                                                new DialogInterface.OnClickListener() {
                                                                    public void onClick(DialogInterface dialog,int id) {
                                                                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                                                        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                                                                        mDatabase.child("users").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                                            @Override
                                                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                                                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                                                                                mDatabase.child("homegroups").child(hgID).child("shoppingList").child(itemNameUIDMap.get(itemName)).setValue(userInput.getText().toString());

                                                                            }

                                                                            @Override
                                                                            public void onCancelled(DatabaseError databaseError) {
                                                                                Log.e("SignIn", "Error when signing in!", databaseError.toException());
                                                                            }
                                                                        });
                                                                    }
                                                                })
                                                        .setNegativeButton("Remove",
                                                                new DialogInterface.OnClickListener() {
                                                                    public void onClick(DialogInterface dialog,int id) {
                                                                        final DatabaseReference mDatabase3 = FirebaseDatabase.getInstance().getReference();
                                                                        mDatabase3.child("homegroups").child(hgID).child("shoppingList").child(itemNameUIDMap.get(itemName)).setValue(null);
                                                                    }
                                                                });
                                                AlertDialog alertDialog = alertDialogBuilder.create();

                                                alertDialog.show();
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

            case R.id.refreshShoppinglistBtn:
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                        mDatabase.child("homegroups").child(hgID).child("shoppingList").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Log.d("helpMePlz", "Got item reference!");
                                itemList = new ArrayList<String>();
                                adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, itemList);
                                listView.setAdapter(adapter);
                                itemNameUIDMap = new HashMap<>();
                                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                                    Log.d("helpMePlz", "Loaded item in!");

                                    String tempItem = postSnapshot.getValue(String.class);
                                    itemList.add(tempItem);
                                    itemNameUIDMap.put(tempItem, postSnapshot.getKey());
                                }
                                adapter.notifyDataSetChanged();



                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Log.e("SignIn", "Error when signing in!", databaseError.toException());
                            }
                        });



                break;


            case R.id.createItemBtn:
                LayoutInflater li = LayoutInflater.from(this.getContext());
                View promptsView = li.inflate(R.layout.prompt_shoppinglist, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        this.getContext());
                alertDialogBuilder.setView(promptsView);
                final EditText userInput = (EditText) promptsView
                        .findViewById(R.id.editTextDialogUserInput);
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                                        mDatabase.child("users").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {

                                                User2 tempUser = dataSnapshot.getValue(User2.class);
                                                Log.d("helpMePlz", tempUser.hgID);
                                                String item = userInput.getText().toString();
                                                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                                                String itemUID = mDatabase.child("homegroups").child(tempUser.hgID).child("shoppingList").push().getKey();
                                                mDatabase.child("homegroups").child(tempUser.hgID).child("shoppingList").child(itemUID).setValue(item);


                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {
                                                Log.e("SignIn", "Error when signing in!", databaseError.toException());
                                            }
                                        });
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alertDialog = alertDialogBuilder.create();

                alertDialog.show();

        }
    }

}
