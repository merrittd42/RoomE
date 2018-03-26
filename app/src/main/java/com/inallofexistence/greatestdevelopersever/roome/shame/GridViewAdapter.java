package com.inallofexistence.greatestdevelopersever.roome.shame;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.inallofexistence.greatestdevelopersever.roome.R;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

public class GridViewAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;

    private ArrayList<String> imageUrls;
    private ArrayList<String> names;

    public GridViewAdapter(Context context, ImageItem imageItem) {

        this.context = context;
        this.imageUrls = imageItem.getUrls();
        this.names = imageItem.getTitle();


    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return names.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View grid = new View(context);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView == null){
        grid = inflater.inflate(R.layout.grid_item_layout,null);
        ImageView mImage =grid.findViewById(R.id.imageG);
        TextView mText = grid.findViewById(R.id.textG);

        mText.setText(this.names.get(position));
        Log.d("IMAGEHELPGVA", this.names.get(position));
        Log.d("IMAGEHELPGVA", this.imageUrls.get(position));
        Picasso
                .get()
                .load(imageUrls.get(position))
                .resize(110, 110)
                .centerCrop()
                .into((ImageView) mImage);
        }else{
            grid = (View) convertView;
        }
        return grid;
    }
}