package com.inallofexistence.greatestdevelopersever.roome.shame;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by quintybox on 3/25/18.
 */

public class ImageItem {
    private ArrayList<String> urls;
    private ArrayList<String> titles;

    public ImageItem(ArrayList<String> urls, ArrayList<String> titles) {

        this.urls = urls;
        this.titles = titles;
    }

    public ArrayList<String> getUrls(){
        return urls;
    }

    public void setUrls(ArrayList<String> urls){
        this.urls = urls;
    }


    public ArrayList<String> getTitle() {
        return titles;
    }

    public void setTitle(ArrayList<String> titles) {
        this.titles = titles;
    }
}