package com.shashankesh.moviesdetails.Main;

import android.util.Log;

import com.shashankesh.moviesdetails.MovieDataCollection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/*
 *File JsonUtils
 *Created by Shashankesh Upadhyay
 *on Monday, March, 2019
 */public class JsonUtils {

    String jsonData;
    ArrayList<MovieDataCollection> movieDataCollections = new ArrayList<>();

    public JsonUtils(String jsonData) {
        this.jsonData = jsonData;
    }

    public ArrayList<MovieDataCollection> parseJsonString() {
        JSONObject root = null;
        try {
            root = new JSONObject(jsonData);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            JSONArray results = root.getJSONArray("results");

            for (int i = 0; i < results.length(); i++) {
                JSONObject resultRoot = results.getJSONObject(i);
                String poster_path = resultRoot.getString("poster_path");
                boolean adult = resultRoot.getBoolean("adult");
                String overview = resultRoot.getString("overview");
                String release_date = resultRoot.getString("release_date");
                JSONArray genre_ids = resultRoot.getJSONArray("genre_ids");
                ArrayList<Integer> genre_id = new ArrayList<Integer>();
                if (genre_ids.length() != 0) {
                    for (int j = 0; j < genre_ids.length(); j++) {
                        genre_id.add(genre_ids.getInt(j));
                    }
                }
                int id = resultRoot.getInt("id");
                String title = resultRoot.getString("title");
                Log.i(JsonUtils.this.toString(), "TEST: in parseJsonString with title = " + title);
                String backdrop_path = resultRoot.getString("backdrop_path");
                double popularity = resultRoot.getDouble("popularity");
                double vote_count = resultRoot.getDouble("vote_average");
                movieDataCollections.add(new MovieDataCollection(poster_path, adult, overview, release_date, genre_id, id, title, popularity, vote_count,backdrop_path));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movieDataCollections;
    }
}
