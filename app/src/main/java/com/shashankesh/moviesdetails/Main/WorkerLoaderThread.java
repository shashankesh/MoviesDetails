package com.shashankesh.moviesdetails.Main;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.shashankesh.moviesdetails.MovieDataCollection;
import com.shashankesh.moviesdetails.NetworkUtils;

import java.util.ArrayList;

/*
 *File WorkerLoaderThread
 *Created by Shashankesh Upadhyay
 *on Thursday, March, 2019
 */public class WorkerLoaderThread extends AsyncTaskLoader<ArrayList<MovieDataCollection>> {
    private boolean booleans;

    public WorkerLoaderThread(Context context, Boolean booleans) {
        super(context);
        this.booleans = booleans;
        Log.i(MainActivity.class.getName(),"TEST2: inside threadConstructor");
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
        Log.i(MainActivity.class.getName(),"TEST2: inside onStartLoading");
    }

    @Override
    public ArrayList<MovieDataCollection> loadInBackground() {
        Log.i(MainActivity.class.getName(),"TEST2: inside loadInBackground");
        ArrayList<MovieDataCollection> movieDataCollection;
        if (!booleans) {
            movieDataCollection = new NetworkUtils().fetchDataTopRated();
        } else {
            movieDataCollection = new NetworkUtils().fetchDataPopular();
        }
        return movieDataCollection;
    }
}
