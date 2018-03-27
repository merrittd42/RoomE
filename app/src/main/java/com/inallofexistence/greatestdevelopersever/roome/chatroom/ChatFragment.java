package com.inallofexistence.greatestdevelopersever.roome.chatroom;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.inallofexistence.greatestdevelopersever.roome.R;
import com.inallofexistence.greatestdevelopersever.roome.model.Message;

/**
 * Created by quintybox on 3/17/18.
 */

public class ChatFragment extends Fragment {
    String hgID;
    private FirebaseListAdapter<Message> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chat, container, false);
        final EditText input = v.findViewById(R.id.input);
        Bundle bundle = this.getArguments();
        hgID = bundle.getString("hgID");
        FloatingActionButton fab =
                (FloatingActionButton)v.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // Read the input field and push a new instance
                // of ChatMessage to the Firebase database
                if(!input.getText().toString().isEmpty()) {
                    FirebaseDatabase.getInstance()
                            .getReference().child("homegroups").child(hgID).child("messages")
                            .push()
                            .setValue(new Message(input.getText().toString(),
                                    FirebaseAuth.getInstance()
                                            .getCurrentUser()
                                            .getDisplayName())
                            );
                }
                // Clear the input
                input.setText("");
                adapter.notifyDataSetChanged();
            }
        });

        ListView listOfMessages = v.findViewById(R.id.list_of_messages);
        Log.d("CHATHELP", hgID);

        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("homegroups")
                .child(hgID)
                .child("messages");


        FirebaseListOptions<Message> options = new FirebaseListOptions.Builder<Message>()
                .setQuery(query, Message.class).setLayout(R.layout.message)
                .build();

        Log.d("CHATHELP", "NOT BROKE HERE");
        adapter = new FirebaseListAdapter<Message>(options) {

            @Override
            protected void populateView(View v, Message model, int position) {
                // Get references to the views of message.xml
                TextView messageText = v.findViewById(R.id.message_text);
                TextView messageUser = v.findViewById(R.id.message_user);
                TextView messageTime = v.findViewById(R.id.message_time);

                // Set their text
                messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUser());

                // Format the date before showing it
                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
                        model.getMessageTime()));
            }
        };

        listOfMessages.setAdapter(adapter);
        adapter.startListening();
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

}
