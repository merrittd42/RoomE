package com.inallofexistence.greatestdevelopersever.roome.shame;

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
import android.widget.GridView;
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
import com.inallofexistence.greatestdevelopersever.roome.model.Infraction;
import com.inallofexistence.greatestdevelopersever.roome.model.Rule;
import com.inallofexistence.greatestdevelopersever.roome.model.User2;
import com.inallofexistence.greatestdevelopersever.roome.rules.ViewRuleActivity;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by quintybox on 3/17/18.
 */

public class ShameFragment extends Fragment implements View.OnClickListener {

    private Button createButton;
    private GridView gridView;
    private String hgID;
    private Button refreshButton;
    GridViewAdapter gridAdapter;
    private ArrayList<String> infracUIDList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_shame, container, false);
        createButton = v.findViewById(R.id.createShameBtn);
        createButton.setOnClickListener(this);
        refreshButton = v.findViewById(R.id.shameRefresh);
        refreshButton.setOnClickListener(this);
        infracUIDList = new ArrayList<>();
        gridView = (GridView) v.findViewById(R.id.shameGrid);
        gridAdapter = new GridViewAdapter(this.getActivity(), getData());
        gridView.setAdapter(gridAdapter);
        gridAdapter.notifyDataSetChanged();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                Intent intent = new Intent(getActivity(), ViewInfractionActivity.class);
                intent.putExtra("shameUID", infracUIDList.get(position));
                intent.putExtra("hgID", hgID);
                startActivity(intent);


            }
        });

        return v;
    }

    private ImageItem getData() {
        final ArrayList<String> urls = new ArrayList<>();
        final ArrayList<String> names = new ArrayList<>();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                User2 tempUser = dataSnapshot.getValue(User2.class);
                Log.d("helpMePlz", tempUser.hgID);
                hgID = tempUser.hgID;
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                mDatabase.child("homegroups").child(tempUser.hgID).child("infractions").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                            Infraction tempInfraction = postSnapshot.getValue(Infraction.class);
                            urls.add(tempInfraction.photoLoc);
                            names.add(tempInfraction.name);
                            Log.d("IMAGEHELP", tempInfraction.photoLoc);
                            infracUIDList.add(tempInfraction.UID);
                        }
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
        return new ImageItem(urls, names);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        gridAdapter.notifyDataSetChanged();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Log.d("UserInfo", user.getDisplayName());


    }

    @Override
    public void onResume(){
        super.onResume();
        gridAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v){

        switch(v.getId()){

            case R.id.createShameBtn:
                Intent rulesIntent = new Intent(getActivity(), CreateInfractionActivity.class);
                startActivity(rulesIntent);
                break;

            case R.id.shameRefresh:

                gridAdapter.notifyDataSetChanged();
                break;
        }
    }

}
