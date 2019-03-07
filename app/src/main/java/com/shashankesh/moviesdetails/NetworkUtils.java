package com.shashankesh.moviesdetails;

import android.net.Uri;
import android.util.Log;

import com.shashankesh.moviesdetails.Detailed.JsonUtilsDetailed;
import com.shashankesh.moviesdetails.Main.JsonUtils;
import com.shashankesh.moviesdetails.Main.MainActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/*
 *File NetworkUtils
 *Created by Shashankesh Upadhyay
 *on Monday, March, 2019
 */public class NetworkUtils {
    final private static String BASE_URL_POPULAR = "https://api.themoviedb.org/3/movie/popular";
    final private static String BASE_URL_TOP_RATED = "https://api.themoviedb.org/3/movie/top_rated";
    final private static String BASE_URL="https://api.themoviedb.org/3/movie";

    final private static String POPULAR = "popular";
    final private static String TOP_RATED = "top_rated";
    String movieId;

    final private static String API_KEY = "api_key";
    final private static String KEY = "bd7fa1082e5ff4ff65dc508565b45b7c";//language=en-US & page=1
    final private static String LANGUAGE = "language";
    final private static String LAN_VALUE = "en-US";
    final private static String PAGE = "page";
    final private static String PAGE_NO = "1";

    String jsonString;
    ArrayList<MovieDataCollection> movieDataCollection;

    public NetworkUtils() {
    }

    public NetworkUtils(String movieId) {
        this.movieId = movieId;
    }

    public ArrayList<MovieDataCollection> fetchDataTopRated() {
        Log.i(NetworkUtils.class.getName(),"TEST2: inside fetchDataTopRated");
        Uri uri = Uri.parse(BASE_URL).buildUpon()
                .appendPath(TOP_RATED)
                .appendQueryParameter(API_KEY, KEY)
                .appendQueryParameter(LANGUAGE, LAN_VALUE)
                //.appendQueryParameter(PAGE, PAGE_NO)
                .build();
        Log.i(NetworkUtils.this.toString(), "TEST: in fetchDataTopRated with uri = " + uri);
        URL url = buildUrl(uri);
        Log.i(NetworkUtils.this.toString(), "TEST: in fetchDataTopRated with url = " + url);
        if (url != null) {
            try {
                jsonString = getResponseFromHttp(url);
                //Log.i(NetworkUtils.this.toString(),"TEST: in fetchDataTopRated with jsonString = "+jsonString);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (jsonString != null) {
                JsonUtils jsonUtils = new JsonUtils(jsonString);
                movieDataCollection = jsonUtils.parseJsonString();
                Log.i(NetworkUtils.this.toString(), "TEST: in fetchDataTopRated with movieDataCollection = " + movieDataCollection.get(0).getTitle());
            }
        }
        return movieDataCollection;
    }

    public ArrayList<MovieDataCollection> fetchDataPopular() {
        Log.i(NetworkUtils.class.getName(),"TEST2: inside fetchDataPopular");
        Uri uri = Uri.parse(BASE_URL).buildUpon()
                .appendPath(POPULAR)
                .appendQueryParameter(API_KEY, KEY)
                .appendQueryParameter(LANGUAGE, LAN_VALUE)
                //.appendQueryParameter(PAGE, PAGE_NO)
                .build();
        Log.i(NetworkUtils.this.toString(), "TEST: in fetchDataTopRated with uri = " + uri);
        URL url = buildUrl(uri);
        Log.i(NetworkUtils.this.toString(), "TEST: in fetchDataTopRated with url = " + url);
        if (url != null) {
            try {
                jsonString = getResponseFromHttp(url);
                //Log.i(NetworkUtils.this.toString(),"TEST: in fetchDataTopRated with jsonString = "+jsonString);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (jsonString != null) {
                JsonUtils jsonUtils = new JsonUtils(jsonString);
                movieDataCollection = jsonUtils.parseJsonString();
                Log.i(NetworkUtils.this.toString(), "TEST: in fetchDataTopRated with movieDataCollection = " + movieDataCollection.get(0).getTitle());
            }
        }
        return movieDataCollection;
    }

    public MovieDataCollection fetchDataMovieId(String movieId) {
        MovieDataCollection movieDataCollection = null;
        Log.i(NetworkUtils.class.getName(),"TEST2: inside fetchDataMovieId");
        Uri uri = Uri.parse(BASE_URL).buildUpon()
                .appendPath(movieId)
                .appendQueryParameter(API_KEY, KEY)
                .appendQueryParameter(LANGUAGE, LAN_VALUE)
                //.appendQueryParameter(PAGE, PAGE_NO)
                .build();
        Log.i(NetworkUtils.this.toString(), "TEST: in fetchDataTopRated with uri = " + uri);
        URL url = buildUrl(uri);
        Log.i(NetworkUtils.this.toString(), "TEST: in fetchDataTopRated with url = " + url);
        if (url != null) {
            try {
                jsonString = getResponseFromHttp(url);
                //Log.i(NetworkUtils.this.toString(),"TEST: in fetchDataTopRated with jsonString = "+jsonString);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (jsonString != null) {
                JsonUtilsDetailed jsonUtils = new JsonUtilsDetailed(jsonString);
                movieDataCollection = jsonUtils.parseJsonString();
            }
        }
        return movieDataCollection;
    }

    private String getResponseFromHttp(URL url) throws IOException {
        if (url != null) {

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                Log.i(NetworkUtils.this.toString(), "TEST: in getResponseFromHttp with ResponseCode = " + urlConnection.getResponseCode());
                InputStream in = urlConnection.getInputStream();
                Scanner scanner = new Scanner(in);
                scanner.useDelimiter("\\A");

                boolean hasInput = scanner.hasNext();
                if (hasInput) {
                    return scanner.next();
                } else return null;
            } finally {
                urlConnection.disconnect();
            }
        } else return null;
    }

    private URL buildUrl(Uri uri) {
        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
