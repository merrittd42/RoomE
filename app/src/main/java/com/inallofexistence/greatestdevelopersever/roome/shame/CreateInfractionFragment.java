package com.inallofexistence.greatestdevelopersever.roome.shame;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.inallofexistence.greatestdevelopersever.roome.R;
import com.inallofexistence.greatestdevelopersever.roome.model.Infraction;
import com.inallofexistence.greatestdevelopersever.roome.model.Rule;
import com.inallofexistence.greatestdevelopersever.roome.model.User2;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

/**
 * Created by quintybox on 3/17/18.
 */

public class CreateInfractionFragment extends Fragment implements View.OnClickListener{
    private Button submitButton;
    private Button cameraButton;
    private StorageReference mStorage;
    private EditText infractionNameView;
    private EditText infractionContentView;
    private Infraction infraction;
    private ImageView mImageView;
    String fbUploadpath;
    static final int REQUEST_TAKE_PHOTO = 1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_create_infraction, container, false);
        getActivity().setTitle("Put the blame on 'em!");
        infractionNameView = v.findViewById(R.id.infractionNameTXT);
        infractionContentView = v.findViewById(R.id.infractionContentTXT);
        mImageView = v.findViewById(R.id.inImage);
        submitButton = v.findViewById(R.id.infractionSubmitBtn);
        cameraButton = v.findViewById(R.id.cameraBtn);
        submitButton.setOnClickListener(this);
        cameraButton.setOnClickListener(this);
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

            case R.id.infractionSubmitBtn:

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
                        infraction = new Infraction(fbUploadpath, infractionNameView.getText().toString(), infractionContentView.getText().toString(), null);

                        String ruleUID = mDatabase.child("homegroups").child(tempUser.hgID).child("infractions").push().getKey();
                        infraction.UID = ruleUID;
                        mDatabase.child("homegroups").child(tempUser.hgID).child("infractions").child(ruleUID).setValue(infraction);


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e("SignIn", "Error when signing in!", databaseError.toException());
                    }
                });


                this.getActivity().finish();

                break;


            case R.id.cameraBtn:

                dispatchTakePictureIntent();


                break;
        }
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] dataBAOS = baos.toByteArray();


            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference();
            StorageReference filepath = storageRef.child("Photos").child("photoData" +new Date().getTime()+".jpg");

            filepath.putBytes(dataBAOS).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Log.d("IMAGEHELP", taskSnapshot.getDownloadUrl().toString());
                    fbUploadpath = taskSnapshot.getDownloadUrl().toString();
                }
            });


            mImageView.setImageBitmap(bitmap);

        }
    }

}
