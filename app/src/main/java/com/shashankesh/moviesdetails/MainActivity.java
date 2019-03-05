package com.shashankesh.moviesdetails;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MoviesAdapter.MoviesAdapterOnClickHandler {


    MoviesAdapter moviesAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.rv_movies);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        new WorkerThread().execute();

    }

    @Override
    public void onClick(MovieDataCollection movieDataCollection) {
        Intent intent = new Intent(MainActivity.this,DeatiledView.class);
        intent.putExtra("parsable_data",movieDataCollection);
        startActivity(intent);
    }

    private class WorkerThread extends AsyncTask<Void,Void,ArrayList<MovieDataCollection>>{
        @Override
        protected void onPostExecute(ArrayList<MovieDataCollection> movieDataCollection) {
            super.onPostExecute(movieDataCollection);
            ArrayList<String> imagePath = new ArrayList<>();
            for(int i=0;i<movieDataCollection.size();i++){
                imagePath.add(movieDataCollection.get(i).getPoster_path());
            }
             moviesAdapter = new MoviesAdapter(movieDataCollection,MainActivity.this);
            recyclerView.setAdapter(moviesAdapter);
        }

        @Override
        protected ArrayList<MovieDataCollection> doInBackground(Void... voids) {
            Log.i(MainActivity.this.toString(),"TEST: just in async doInBackGround");
            ArrayList<MovieDataCollection> movieDataCollection = new NetworkUtils().fetchDataTopRated();
            Log.i(MainActivity.this.toString(),"TEST: in async doInBackGround with"+movieDataCollection.get(0).getPoster_path());
            return movieDataCollection;
        }
    }
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
}
