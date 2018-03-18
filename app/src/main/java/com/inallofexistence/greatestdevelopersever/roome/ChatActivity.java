package com.inallofexistence.greatestdevelopersever.roome;

import android.support.v4.app.Fragment;

public class ChatActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment(){
        return new ChatFragment();
    }
}
