package com.shashankesh.moviesdetails;

import java.io.Serializable;
import java.util.ArrayList;

/*
 *File MovieDataCollection
 *Created by Shashankesh Upadhyay
 *on Monday, March, 2019
 */public class MovieDataCollection implements Serializable {
    ArrayList<String> genres_name;
    String poster_path;
    boolean adult;
    String overview;
    String release_date;
    ArrayList<Integer> genre_ids;
    int id;
    String title;
    double popularity;
    double vote_count;
    String backdrop_path;
    int budget;
    ArrayList<String> production_companies_name;
    int revenue;
    int runtime;
    String status;
    String tagline;

    public int getBudget() {
        return budget;
    }

    public ArrayList<String> getProduction_companies_name() {
        return production_companies_name;
    }

    public int getRevenue() {
        return revenue;
    }

    public int getRuntime() {
        return runtime;
    }

    public String getStatus() {
        return status;
    }

    public String getTagline() {
        return tagline;
    }

    public MovieDataCollection(String poster_path, boolean adult, String overview, String release_date, ArrayList<Integer> genre_ids, int id, String title, double popularity, double vote_count) {
        this.poster_path = poster_path;
        this.adult = adult;
        this.overview = overview;
        this.release_date = release_date;
        this.genre_ids = genre_ids;
        this.id = id;
        this.title = title;
        this.popularity = popularity;
        this.vote_count = vote_count;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public MovieDataCollection(String poster_path, boolean adult, String overview, String release_date, ArrayList<Integer> genre_ids, int id, String title, double popularity, double vote_count, String backdrop_path) {
        this(poster_path, adult, overview, release_date, genre_ids, id, title, popularity, vote_count);
        this.backdrop_path = backdrop_path;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public ArrayList<Integer> getGenre_ids() {
        return genre_ids;
    }

    public void setGenre_ids(ArrayList<Integer> genre_ids) {
        this.genre_ids = genre_ids;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public double getVote_count() {
        return vote_count;
    }

    public void setVote_count(double vote_count) {
        this.vote_count = vote_count;
    }

    public ArrayList<String> getGenres_name() {
        return genres_name;
    }

    public MovieDataCollection(String overview, String release_date, String title, double popularity, double vote_count, int budget, ArrayList<String> production_companies_name, int revenue, int runtime, String status, String tagline, ArrayList<String> genres_name) {
        this.overview = overview;
        this.release_date = release_date;
        this.title = title;
        this.popularity = popularity;
        this.vote_count = vote_count;
        this.budget = budget;
        this.production_companies_name = production_companies_name;
        this.revenue = revenue;
        this.runtime = runtime;
        this.status = status;
        this.tagline = tagline;
        this.genres_name = genres_name;
    }
}
