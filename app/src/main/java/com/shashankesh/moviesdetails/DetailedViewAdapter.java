package com.shashankesh.moviesdetails;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

/*
 *File DetailedViewAdapter
 *Created by Shashankesh Upadhyay
 *on Tuesday, March, 2019
 */public class DetailedViewAdapter extends RecyclerView.Adapter<DetailedViewAdapter.DetailedViewAdapterViewHolder>{

    String overview;
    double ratings;
    String releaseDate;
    HashMap<Integer , String> hashMap = new HashMap<>();


    public DetailedViewAdapter( double ratings, String overview, String releaseDate) {
        this.overview = overview;
        this.ratings = ratings;
        this.releaseDate = releaseDate;
    }

    public DetailedViewAdapter(HashMap<Integer, String> hashMap) {
        this.hashMap = hashMap;
    }

    @NonNull
    @Override
    public DetailedViewAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.detailed_list_item,parent,false);
        return new DetailedViewAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailedViewAdapterViewHolder holder, int position) {
        switch (position){
            case 0:
                holder.heading.setText("Overview");
                break;
            case 1:
                holder.heading.setText("Ratings");
                break;
            case 2:
                holder.heading.setText("Releasing On");
                break;

        }
        holder.detail.setText(hashMap.get(position));
    }

    @Override
    public int getItemCount() {
        return hashMap.size();
    }

    class DetailedViewAdapterViewHolder extends RecyclerView.ViewHolder{
        TextView heading;
        TextView detail;

        public DetailedViewAdapterViewHolder(View itemView) {
            super(itemView);
            heading = itemView.findViewById(R.id.heading);
            detail = itemView.findViewById(R.id.description);
        }
    }
}
