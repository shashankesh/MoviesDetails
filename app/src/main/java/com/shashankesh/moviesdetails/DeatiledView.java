package com.shashankesh.moviesdetails;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DeatiledView extends AppCompatActivity {

    ImageView imageView;
    TextView overview;
    TextView ratings;
    TextView releaseDate;
    MovieDataCollection movieDataCollection;
    CollapsingToolbarLayout collapsingToolbar;

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
                    collapsingToolbar.setTitle(getString(R.string.app_name));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(movieDataCollection.getTitle());
                    isShow = false;
                }
            }
        });
        overview = findViewById(R.id.overview);
        overview.setText(movieDataCollection.getOverview());
        ratings = findViewById(R.id.user_ratings);
        ratings.setText(Double.toString(movieDataCollection.getVote_count()));
        releaseDate = findViewById(R.id.release_date);
        releaseDate.setText(movieDataCollection.getRelease_date());
        imageView = findViewById(R.id.backdrop);
        try {
            Picasso.get().load("http://image.tmdb.org/t/p/w185/" + movieDataCollection.getPoster_path()).into(imageView);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

