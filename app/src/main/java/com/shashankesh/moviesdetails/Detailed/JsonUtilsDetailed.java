package com.shashankesh.moviesdetails.Detailed;

import com.shashankesh.moviesdetails.MovieDataCollection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/*
 *File JsonUtilsDetailed
 *Created by Shashankesh Upadhyay
 *on Tuesday, March, 2019
 */public class JsonUtilsDetailed {
    String jsonData;
    //ArrayList<MovieDataCollection> movieDataCollections = new ArrayList<>();

    public JsonUtilsDetailed(String jsonData) {
        this.jsonData = jsonData;
    }

    public MovieDataCollection parseJsonString() {
        JSONObject root = null;
        try {
            root = new JSONObject(jsonData);

        int budget = root.getInt("budget");
        JSONArray genres = root.getJSONArray("genres");
        ArrayList<String> genres_name = new ArrayList<>();
        for (int i = 0; i < genres.length(); i++) {
            genres_name.add(genres.getJSONObject(i).getString("name"));
        }
        String overview = root.getString("overview");
        double popularity = root.getDouble("popularity");
        JSONArray production_companies = root.getJSONArray("production_companies");
        ArrayList<String> production_companies_name = new ArrayList<>();
        for (int i = 0; i < production_companies.length(); i++) {
            production_companies_name.add(production_companies.getJSONObject(i).getString("name"));
        }
        String release_date = root.getString("release_date");
        int revenue = root.getInt("revenue");
        int runtime = root.getInt("runtime");
        String status = root.getString("status");
        String tagline = root.getString("tagline");
        String title = root.getString("title");
        double vote_average = root.getDouble("vote_average");
        return new MovieDataCollection(overview, release_date, title, popularity, vote_average, budget, production_companies_name, revenue, runtime, status, tagline, genres_name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
