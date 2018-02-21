package com.projects.daniel.moviesapp;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;


public class NetworkUtils {
    public static final String MOVIES_BASE_URL = "http://api.themoviedb.org/3";
    public static final String POSTER_BASE_URL = "http://image.tmdb.org/t/p";
    public static String POSTER_SIZE = "w185";
    public static final String POPULAR_MOVIES = "movie/popular";
    public static final String TOP_RATED_MOVIES = "movie/top_rated";
    private static final String PARAM_KEY = "api_key";

    public static URL getMoviesQueryUrl(String type) {
        Uri uri = Uri.parse(MOVIES_BASE_URL);
        if(type.equals(POPULAR_MOVIES) || type.equals(TOP_RATED_MOVIES)) {
            String str = uri.buildUpon().appendEncodedPath(type)
                    .appendQueryParameter(PARAM_KEY, Constants.API_KEY)
                    .build().toString();

            URL url = null;
            try {
                url = new URL(str);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            return url;
        } else {
            return null;
        }
    }

    public static URL getPosterQueryUrl(String posterString) {
        Uri uri = Uri.parse(POSTER_BASE_URL).buildUpon().appendPath(POSTER_SIZE)
                .appendPath(posterString).build();
        Log.d("getPosterURL", uri.toString());

        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

}
