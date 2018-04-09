package com.inallofexistence.greatestdevelopersever.roome.shame;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by quintybox on 3/17/18.
 */

public class ViewInfractionFragment extends Fragment implements View.OnClickListener{

    private Button deleteButton;
    private TextView name;
    private TextView desc;
    private ImageView pic;
    private String hgID;
    private String shameUID;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_view_infraction, container, false);
        Bundle bundle = this.getArguments();

        shameUID = bundle.getString("shameUID");
        hgID = bundle.getString("hgID");


        deleteButton = v.findViewById(R.id.deleteShameBtn);
        name = v.findViewById(R.id.vShameNameTXT);
        desc = v.findViewById(R.id.shameDescTXT);
        deleteButton.setOnClickListener(this);
        pic = v.findViewById(R.id.shamePic);

        return v;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        Log.d("viewRule", shameUID);
        Log.d("viewRule", hgID);
        mDatabase.child("homegroups").child(hgID).child("infractions").child(shameUID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Infraction tempShame = dataSnapshot.getValue(Infraction.class);
                getActivity().setTitle(tempShame.name);

                name.setText(tempShame.name);
                desc.setText(tempShame.description);

                Picasso
                        .get()
                        .load(tempShame.photoLoc)
                        .resize(110, 110)
                        .centerCrop()
                        .into((ImageView) pic);


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

            case R.id.deleteShameBtn:
                final DatabaseReference mDatabase3 = FirebaseDatabase.getInstance().getReference();
                mDatabase3.child("homegroups").child(hgID).child("infraction").child(shameUID).setValue(null);
                getActivity().finish();

                break;


        }
    }

}
