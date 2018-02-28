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
    public static String POSTER_SIZE = "w500";
    public static final String POPULAR_MOVIES = "movie/popular";
    public static final String TOP_RATED_MOVIES = "movie/top_rated";
    public static final String MOVIE_PARAM = "movie";
    public static final String TRAILERS_PARAM = "trailers";
    private static final String PARAM_KEY = "api_key";

    public static final String YOUTUBE_BASE_LINK = "www.youtube.com";
    public static final String YOUTUBE_PARAM_WATCH = "watch";
    public static final String YOUTUBE_ID_KEY = "v";


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

    public static URL getTrailersUrl(int id) {
        Uri uri = Uri.parse(MOVIES_BASE_URL);
        String str = uri.buildUpon().appendEncodedPath(MOVIE_PARAM)
                .appendEncodedPath(String.valueOf(id))
                .appendEncodedPath(TRAILERS_PARAM)
                .appendQueryParameter(PARAM_KEY, Constants.API_KEY)
                .build().toString();

        URL url = null;
        try {
            url = new URL(str);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }
}
