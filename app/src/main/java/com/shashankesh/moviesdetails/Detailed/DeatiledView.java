package com.shashankesh.moviesdetails.Detailed;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.shashankesh.moviesdetails.MovieDataCollection;
import com.shashankesh.moviesdetails.R;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.HashMap;

public class DeatiledView extends AppCompatActivity implements LoaderManager.LoaderCallbacks<MovieDataCollection> {

    ImageView backdrop;
    String overview;
    double ratings;
    String releaseDate;
    MovieDataCollection movieDataCollection;
    CollapsingToolbarLayout collapsingToolbar;
    RecyclerView recyclerView;
    DetailedViewAdapter viewAdapter;
    HashMap<Integer, String> hashMap = new HashMap<>();
    int movieId;
    ProgressBar progressBar;
    LinearLayout emptyView;
    TextView emptyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deatiled_view);

        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("parsable_data"))
                movieDataCollection = (MovieDataCollection) intent.getSerializableExtra("parsable_data");
            collapsingToolbar =
                    findViewById(R.id.collapsing_toolbar);
            collapsingToolbar.setTitle(movieDataCollection.getTitle());
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Get a support ActionBar corresponding to this toolbar
        android.support.v7.app.ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);


        /**
         * Initializing collapsing toolbar
         * Will show and hide the toolbar title on scroll
         */

        AppBarLayout appBarLayout = findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(movieDataCollection.getTitle());
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(movieDataCollection.getTitle());
                    isShow = false;
                }
            }
        });
        hashMap.put(0, movieDataCollection.getOverview());
        hashMap.put(1, Double.toString(movieDataCollection.getVote_count()));
        hashMap.put(2, movieDataCollection.getRelease_date());
        backdrop = findViewById(R.id.backdrop);
        try {
            Picasso.get().load("http://image.tmdb.org/t/p/w185/" + movieDataCollection.getPoster_path()).into(backdrop);
        } catch (Exception e) {
            e.printStackTrace();
        }
        recyclerView = findViewById(R.id.rv_detailed);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        movieId = movieDataCollection.getId();
        progressBar = findViewById(R.id.progress_detailed);

        if (isNetworkAvailable()) {
//            new WorkerThread().execute(String.valueOf(movieId));
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(1,null,this);
        }else {
            showErrorMessage();
            emptyText.setText("please check your connectivity");
        }

        //empty view setting
        emptyView = findViewById(R.id.empty_view_detail);
        emptyText = findViewById(R.id.empty_view_detail_text);

    }

    @Override
    public Loader<MovieDataCollection> onCreateLoader(int i, Bundle bundle) {
        return new WorkerLoaderDetThread(DeatiledView.this,String.valueOf(movieId));
    }

    @Override
    public void onLoadFinished(Loader<MovieDataCollection> loader, MovieDataCollection movieDataCollection) {
        if (movieDataCollection != null) {
            //resetting the empty view
            showData();

            hashMap.put(0, movieDataCollection.getOverview());
            hashMap.put(1, Double.toString(movieDataCollection.getVote_count()));
            hashMap.put(2, movieDataCollection.getRelease_date());
            hashMap.put(3, "$" + NumberFormat.getInstance().format(movieDataCollection.getBudget()));
            int i = 0;
            hashMap.put(4, String.valueOf(movieDataCollection.getPopularity()));
            String temp = "";
            for (i = 0; i < movieDataCollection.getProduction_companies_name().size(); i++) {
                temp = hashMap.put(5, movieDataCollection.getProduction_companies_name().get(i));
                if (temp != null)
                    temp = ", " + temp;
            }
            hashMap.put(5, movieDataCollection.getProduction_companies_name().get(--i) + temp);
            hashMap.put(6, "$" + NumberFormat.getInstance().format(movieDataCollection.getRevenue()));
            hashMap.put(7, String.valueOf(movieDataCollection.getRuntime() + " minutes"));
            hashMap.put(8, movieDataCollection.getStatus());
            hashMap.put(9, movieDataCollection.getTagline());
            hashMap.put(10, NumberFormat.getInstance().format(movieDataCollection.getVote_count()));
            int j = 0;
            temp = "";
            for (j = 0; j < movieDataCollection.getGenres_name().size(); j++) {
                temp = hashMap.put((11), movieDataCollection.getGenres_name().get(j));
                if (temp != null)
                    temp = ", " + temp;
            }
            hashMap.put((11), movieDataCollection.getGenres_name().get(--j) + temp);
            viewAdapter = new DetailedViewAdapter(hashMap);
            recyclerView.setAdapter(viewAdapter);
            recyclerView.setHasFixedSize(true);
        } else {
            showErrorMessage();
        }
        progressBar.setVisibility(View.INVISIBLE);
    }


    @Override
    public void onLoaderReset(Loader<MovieDataCollection> loader) {
        viewAdapter.clear();
    }

//    private class WorkerThread extends AsyncTask<String, Void, MovieDataCollection> {
//        @Override
//        protected void onPreExecute() {
//            progressBar.setVisibility(View.VISIBLE);
//        }
//
//        @Override
//        protected MovieDataCollection doInBackground(String... movieId) {
//            NetworkUtils networkUtils = null;
//            if (movieId[0] != null || movieId.length != 0) {
//                networkUtils = new NetworkUtils(movieId[0]);
//            }
//            MovieDataCollection movieDataCollection = null;
//            if (networkUtils != null) {
//                movieDataCollection = networkUtils.fetchDataMovieId(movieId[0]);
//            }
//            return movieDataCollection;
//        }
//
//        @Override
//        protected void onPostExecute(MovieDataCollection movieDataCollection) {
//            if (movieDataCollection != null) {
//                //resetting the empty view
//                showData();
//
//                hashMap.put(0, movieDataCollection.getOverview());
//                hashMap.put(1, Double.toString(movieDataCollection.getVote_count()));
//                hashMap.put(2, movieDataCollection.getRelease_date());
//                hashMap.put(3, "$" + NumberFormat.getInstance().format(movieDataCollection.getBudget()));
//                int i = 0;
//                hashMap.put(4, String.valueOf(movieDataCollection.getPopularity()));
//                String temp = "";
//                for (i = 0; i < movieDataCollection.getProduction_companies_name().size(); i++) {
//                    temp = hashMap.put(5, movieDataCollection.getProduction_companies_name().get(i));
//                    if (temp != null)
//                        temp = ", " + temp;
//                }
//                hashMap.put(5, movieDataCollection.getProduction_companies_name().get(--i) + temp);
//                hashMap.put(6, "$" + NumberFormat.getInstance().format(movieDataCollection.getRevenue()));
//                hashMap.put(7, String.valueOf(movieDataCollection.getRuntime() + " minutes"));
//                hashMap.put(8, movieDataCollection.getStatus());
//                hashMap.put(9, movieDataCollection.getTagline());
//                hashMap.put(10, NumberFormat.getInstance().format(movieDataCollection.getVote_count()));
//                int j = 0;
//                temp = "";
//                for (j = 0; j < movieDataCollection.getGenres_name().size(); j++) {
//                    temp = hashMap.put((11), movieDataCollection.getGenres_name().get(j));
//                    if (temp != null)
//                        temp = ", " + temp;
//                }
//                hashMap.put((11), movieDataCollection.getGenres_name().get(--j) + temp);
//                viewAdapter = new DetailedViewAdapter(hashMap);
//                recyclerView.setAdapter(viewAdapter);
//                recyclerView.setHasFixedSize(true);
//            } else {
//                showErrorMessage();
//            }
//            progressBar.setVisibility(View.INVISIBLE);
//        }
//    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    void showData(){
        emptyView.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
    }
    void showErrorMessage(){
        recyclerView.setVisibility(View.INVISIBLE);
        emptyView.setVisibility(View.VISIBLE);
        emptyText.setText("Opps! something went wrong");
    }
}

