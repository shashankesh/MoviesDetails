package com.shashankesh.moviesdetails;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.ArrayMap;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class DeatiledView extends AppCompatActivity {

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
        progressBar.setVisibility(View.VISIBLE);
        new WorkerThread().execute(String.valueOf(movieId));

    }

    private class WorkerThread extends AsyncTask<String, Void, MovieDataCollection> {

        @Override
        protected MovieDataCollection doInBackground(String... movieId) {
            NetworkUtils networkUtils = null;
            if (movieId[0] != null || movieId.length != 0) {
                networkUtils = new NetworkUtils(movieId[0]);
            }
            MovieDataCollection movieDataCollection = null;
            if (networkUtils != null) {
                movieDataCollection = networkUtils.fetchDataMovieId(movieId[0]);
            }
            return movieDataCollection;
        }

        @Override
        protected void onPostExecute(MovieDataCollection movieDataCollection) {
            if (movieDataCollection != null) {
                hashMap.put(0, movieDataCollection.getOverview());
                hashMap.put(1, Double.toString(movieDataCollection.getVote_count()));
                hashMap.put(2, movieDataCollection.getRelease_date());
                hashMap.put(3, "$"+NumberFormat.getInstance().format(movieDataCollection.getBudget()));
                int i = 0;
                hashMap.put(4, String.valueOf(movieDataCollection.getPopularity()));
                String temp = "";
                for (i = 0; i < movieDataCollection.getProduction_companies_name().size(); i++) {
                        temp = hashMap.put(5, movieDataCollection.getProduction_companies_name().get(i));
                    if (temp!=null)
                        temp = ", "+temp;
                    }
                hashMap.put(5, movieDataCollection.getProduction_companies_name().get(--i)+temp);
                hashMap.put(6, "$"+NumberFormat.getInstance().format(movieDataCollection.getRevenue()));
                hashMap.put(7, String.valueOf(movieDataCollection.getRuntime()+" minutes"));
                hashMap.put(8, movieDataCollection.getStatus());
                hashMap.put(9, movieDataCollection.getTagline());
                hashMap.put(10, NumberFormat.getInstance().format(movieDataCollection.getVote_count()));
                int j = 0;
                temp = "";
                for (j = 0; j < movieDataCollection.getGenres_name().size(); j++) {
                    temp = hashMap.put((11), movieDataCollection.getGenres_name().get(j));
                    if (temp!=null)
                        temp = ", "+temp;
                }
                hashMap.put((11), movieDataCollection.getGenres_name().get(--j)+temp);
                viewAdapter = new DetailedViewAdapter(hashMap);
                recyclerView.setAdapter(viewAdapter);
                recyclerView.setHasFixedSize(true);
            }
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
}

