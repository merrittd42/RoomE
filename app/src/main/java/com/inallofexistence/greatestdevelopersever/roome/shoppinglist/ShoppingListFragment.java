package com.inallofexistence.greatestdevelopersever.roome.shoppinglist;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inallofexistence.greatestdevelopersever.roome.R;

import java.util.ArrayList;

/**
 * Created by quinty on 2/14/18.
 */

public class ShoppingListFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("lifeInfo", "Starting onCreateView");
        View v = inflater.inflate(R.layout.shoppinglist_fragment, container, false);

        ArrayList<String> shoppingList = new ArrayList<>();
        shoppingList.add("Eggs");
        shoppingList.add("More eggs");
        shoppingList.add("Truck made of eggs");
        shoppingList.add("Milk");

        LinearLayout linearLayout = v.findViewById(R.id.linearLayoutShop);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        TextView title = new TextView(getActivity());
        title.setText("Shopping List");
        title.setTextColor(Color.BLUE);
        title.setTextSize(24);
        title.setGravity(Gravity.CENTER);
        linearLayout.addView(title);
        for(int i = 0; i <4; i++) {
            TextView textO = new TextView(getActivity());
            textO.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            textO.setText(shoppingList.get(i));
            linearLayout.addView(textO);
        }
        return v;
    }

}
