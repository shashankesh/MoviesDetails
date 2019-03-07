package com.shashankesh.moviesdetails.Detailed;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.shashankesh.moviesdetails.MovieDataCollection;
import com.shashankesh.moviesdetails.NetworkUtils;

/*
 *File WorkerLoaderDetThread
 *Created by Shashankesh Upadhyay
 *on Wednesday, March, 2019
 */public class WorkerLoaderDetThread extends AsyncTaskLoader<MovieDataCollection> {

    String movieId;

    public WorkerLoaderDetThread(Context context, String movieId) {
        super(context);
        this.movieId = movieId;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public MovieDataCollection loadInBackground() {
        NetworkUtils networkUtils = null;
        if (movieId != null) {
            networkUtils = new NetworkUtils(movieId);
        }
        MovieDataCollection movieDataCollection = null;
        if (networkUtils != null) {
            movieDataCollection = networkUtils.fetchDataMovieId(movieId);
        }
        return movieDataCollection;
    }
}
