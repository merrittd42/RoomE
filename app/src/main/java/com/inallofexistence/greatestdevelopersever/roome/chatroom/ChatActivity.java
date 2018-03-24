package com.inallofexistence.greatestdevelopersever.roome.chatroom;

import android.support.v4.app.Fragment;

import com.inallofexistence.greatestdevelopersever.roome.SingleFragmentActivity;

public class ChatActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment(){
        return new ChatFragment();
    }
}
