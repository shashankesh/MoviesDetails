package com.shashankesh.moviesdetails;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.ArrayMap;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deatiled_view);

        Intent intent = getIntent();
        if(intent!=null) {
            if(intent.hasExtra("parsable_data"))
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
        hashMap.put(0,movieDataCollection.getOverview()) ;
        hashMap.put(1,Double.toString(movieDataCollection.getVote_count()));
        hashMap.put(2,movieDataCollection.getRelease_date());
        backdrop = findViewById(R.id.backdrop);
        try {
            Picasso.get().load("http://image.tmdb.org/t/p/w185/" + movieDataCollection.getPoster_path()).into(backdrop);
        }catch (Exception e){
            e.printStackTrace();
        }
        recyclerView = findViewById(R.id.rv_detailed);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        viewAdapter = new DetailedViewAdapter(hashMap);
        recyclerView.setAdapter(viewAdapter);
    }
}

