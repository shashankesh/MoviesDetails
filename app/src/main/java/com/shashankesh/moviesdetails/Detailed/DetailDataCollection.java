package com.shashankesh.moviesdetails.Detailed;

/*
 *File DetailDataCollection
 *Created by Shashankesh Upadhyay
 *on Tuesday, March, 2019
 */public class DetailDataCollection {
    String overview;
    double ratings;
    String releaseDate;

    public DetailDataCollection(String overview, double ratings, String releaseDate) {
        this.overview = overview;
        this.ratings = ratings;
        this.releaseDate = releaseDate;
    }

    public String getOverview() {
        return overview;
    }

    public double getRatings() {
        return ratings;
    }

    public String getReleaseDate() {
        return releaseDate;
    }
}
