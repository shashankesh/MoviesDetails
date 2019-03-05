package com.shashankesh.moviesdetails;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/*
 *File MoviesAdapter
 *Created by Shashankesh Upadhyay
 *on Monday, March, 2019
 */public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesAdapterViewHolder> {
    ArrayList<String> imagePath;
    ArrayList<String> title;
    ArrayList<String> vote_count;
    ArrayList<MovieDataCollection> movieDataCollection;

    MoviesAdapterOnClickHandler moviesAdapterOnClickHandler;

//    public MoviesAdapter(ArrayList<String> imagePath) {
//        this.imagePath = imagePath;
//    }

    public MoviesAdapter(ArrayList<MovieDataCollection> movieDataCollection, MoviesAdapterOnClickHandler moviesAdapterOnClickHandler) {
        this(movieDataCollection);
        this.moviesAdapterOnClickHandler = moviesAdapterOnClickHandler;
    }

    public MoviesAdapter(ArrayList<MovieDataCollection> movieDataCollection) {
        this.movieDataCollection = movieDataCollection;
    }

    public interface MoviesAdapterOnClickHandler {
        void onClick(MovieDataCollection movieDataCollection);
    }

    @NonNull
    @Override
    public MoviesAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_item, parent, false);

        return new MoviesAdapterViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MoviesAdapterViewHolder holder, int position) {
        Picasso.get().load("http://image.tmdb.org/t/p/w185/" + movieDataCollection.get(position).getPoster_path()).into(holder.imageView);
        holder.title.setText(movieDataCollection.get(position).getTitle());
        holder.count.setText("Rating: " + Double.toString(movieDataCollection.get(position).getVote_count()));
    }

    @Override
    public int getItemCount() {
        return movieDataCollection.size();
    }

    class MoviesAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView title, count;

        public MoviesAdapterViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_movie);
            title = itemView.findViewById(R.id.title);
            count = itemView.findViewById(R.id.count);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            MovieDataCollection movieDataCollection = MoviesAdapter.this.movieDataCollection.get(adapterPosition);
            moviesAdapterOnClickHandler.onClick(movieDataCollection);
        }
    }
}
