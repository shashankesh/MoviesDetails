package com.shashankesh.moviesdetails.Main;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.res.Resources;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.shashankesh.moviesdetails.Detailed.DeatiledView;
import com.shashankesh.moviesdetails.MovieDataCollection;
import com.shashankesh.moviesdetails.NetworkUtils;
import com.shashankesh.moviesdetails.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MoviesAdapter.MoviesAdapterOnClickHandler, LoaderManager.LoaderCallbacks<ArrayList<MovieDataCollection>> {


    MoviesAdapter moviesAdapter;
    RecyclerView recyclerView;
    ImageView backdropMain;
    ProgressBar progressBar;
    LinearLayout emptyView;
    TextView emptyViewText;
    boolean sortedByPopularity = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.rv_movies);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        //inflating menu for custom toolbar
        toolbar.inflateMenu(R.menu.main_activity);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.popular) {
                    Toast.makeText(getBaseContext(), "Sorting by popularity", Toast.LENGTH_SHORT).show();
                    if (!sortedByPopularity) {
                        sortedByPopularity = true;
                        execute(sortedByPopularity);
                        Toast.makeText(getBaseContext(), "Sorted by popularity", Toast.LENGTH_SHORT).show();
                    }else Toast.makeText(getBaseContext(), "Already sorted by popularity", Toast.LENGTH_SHORT).show();

                } else if (id == R.id.ratings) {
                    Toast.makeText(getBaseContext(), "Sorting by ratings", Toast.LENGTH_SHORT).show();
                    if (sortedByPopularity) {
                        sortedByPopularity = false;
                        execute(sortedByPopularity);
                        Toast.makeText(getBaseContext(), "Sorted by ratings", Toast.LENGTH_SHORT).show();

                    }else Toast.makeText(getBaseContext(), "Already sorted by ratings", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
        initCollapsingToolbar();
        backdropMain = findViewById(R.id.backdrop_main);
        try {
            Picasso.get().load(R.drawable.movies_ratings).into(backdropMain);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //setting progress bar
        progressBar = findViewById(R.id.progressBar);

        //initialising empty view
        emptyView = findViewById(R.id.empty_view);
        emptyViewText = findViewById(R.id.empty_view_text);
        if (!sortedByPopularity) {
            sortedByPopularity = false;
            execute(sortedByPopularity);
        }
    }

    @Override
    public void onClick(MovieDataCollection movieDataCollection) {
        Intent intent = new Intent(MainActivity.this, DeatiledView.class);
        intent.putExtra("parsable_data", movieDataCollection);
        startActivity(intent);
    }


    @Override
    public Loader<ArrayList<MovieDataCollection>> onCreateLoader(int i, Bundle bundle) {
        Log.i(MainActivity.class.getName(),"TEST2: inside onCreateLoader");
        progressBar.setVisibility(View.VISIBLE);
       return new WorkerLoaderThread(this,sortedByPopularity);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<MovieDataCollection>> loader, ArrayList<MovieDataCollection> movieDataCollections) {
        //checking for setting empty view
        Log.i(MainActivity.class.getName(),"TEST2: inside onLoaderFinished");
        if (movieDataCollections == null) {
            recyclerView.setVisibility(View.INVISIBLE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            ArrayList<String> imagePath = new ArrayList<>();
            for (int i = 0; i < movieDataCollections.size(); i++) {
                imagePath.add(movieDataCollections.get(i).getPoster_path());
            }
            moviesAdapter = new MoviesAdapter(movieDataCollections, MainActivity.this);
            recyclerView.setAdapter(moviesAdapter);
            recyclerView.setHasFixedSize(true);
        }
        progressBar.setVisibility(View.INVISIBLE);

    }

    @Override
    public void onLoaderReset(Loader<ArrayList<MovieDataCollection>> loader) {
        Log.i(MainActivity.class.getName(),"TEST2: inside loaderReset");
        moviesAdapter.clear();
    }

//    private class WorkerThread extends AsyncTask<Boolean, Void, ArrayList<MovieDataCollection>> {
//        @Override
//        protected void onPreExecute() {
//            progressBar.setVisibility(View.VISIBLE);
//        }
//
//        @Override
//        protected void onPostExecute(ArrayList<MovieDataCollection> movieDataCollection) {
//            super.onPostExecute(movieDataCollection);
//
//
//            //checking for setting empty view
//            if (movieDataCollection == null) {
//                recyclerView.setVisibility(View.INVISIBLE);
//                emptyView.setVisibility(View.VISIBLE);
//            } else {
//                ArrayList<String> imagePath = new ArrayList<>();
//                for (int i = 0; i < movieDataCollection.size(); i++) {
//                    imagePath.add(movieDataCollection.get(i).getPoster_path());
//                }
//                moviesAdapter = new MoviesAdapter(movieDataCollection, MainActivity.this);
//                recyclerView.setAdapter(moviesAdapter);
//                recyclerView.setHasFixedSize(true);
//            }
//            progressBar.setVisibility(View.INVISIBLE);
//
//        }
//
//        @Override
//        protected ArrayList<MovieDataCollection> doInBackground(Boolean... booleans) {
//            Log.i(MainActivity.this.toString(), "TEST: just in async doInBackGround");
//            ArrayList<MovieDataCollection> movieDataCollection;
//            if (!booleans[0]) {
//                movieDataCollection = new NetworkUtils().fetchDataTopRated();
//            } else {
//                movieDataCollection = new NetworkUtils().fetchDataPopular();
//            }
//
////            Log.i(MainActivity.this.toString(), "TEST: in async doInBackGround with" + movieDataCollection.get(0).getPoster_path());
//            return movieDataCollection;
//        }
//    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.main_activity, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.popular) {
//            Toast.makeText(this, "Sorting by popularity", Toast.LENGTH_LONG).show();
//        } else if (id == R.id.ratings) {
//            Toast.makeText(this, "Sorting by ratings", Toast.LENGTH_LONG).show();
//        }
//        return super.onOptionsItemSelected(item);
//    }

    /**
     * Initializing collapsing toolbar
     * Will show and hide the toolbar title on scroll
     */
    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                findViewById(R.id.collapsing_toolbar_main);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = findViewById(R.id.appbar_main);
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
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    void execute(Boolean bool) {
        if (isNetworkAvailable()) {
            progressBar.setVisibility(View.VISIBLE);
//            new WorkerThread().execute(bool);
            Log.i(MainActivity.class.getName(),"TEST2: just before loaderInit");
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(1,null,this);
            Log.i(this.toString(),"TEST2: just after loaderInit");
        } else {
            emptyViewText.setText("Please Check Network Connectivity");
            recyclerView.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
            emptyView.setVisibility(View.VISIBLE);
        }
    }

    //inflating menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.popular) {
            Toast.makeText(this, "Sorting by popularity", Toast.LENGTH_LONG).show();
        } else if (id == R.id.ratings) {
            Toast.makeText(this, "Sorting by ratings", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
