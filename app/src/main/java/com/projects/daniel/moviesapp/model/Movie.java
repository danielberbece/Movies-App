package com.projects.daniel.moviesapp.model;


import android.graphics.Bitmap;

import org.json.JSONObject;

import java.io.Serializable;

public class Movie implements Serializable {

    private String originalTitle;
    private String posterId;
    private String plot;
    private String releaseDate;
    private double rating;
    private Bitmap posterImage;

    private static final String JSON_ORIGINAL_TITLE_KEY = "original_title";
    private static final String JSON_POSTER_KEY = "poster_path";
    private static final String JSON_PLOT_KEY = "overview";
    private static final String JSON_RELEASE_DATE_KEY = "release_date";
    private static final String JSON_RATING_KEY = "vote_average";


    public Movie(String originalTitle, String posterId, String plot, String releaseDate, double rating) {
        this.originalTitle = originalTitle;
        this.posterId = posterId;
        this.plot = plot;
        this.releaseDate = releaseDate;
        this.rating = rating;
    }

    public static Movie objectFromJson(JSONObject jsonMovie) {
        String title = jsonMovie.optString(JSON_ORIGINAL_TITLE_KEY);
        String posterId = jsonMovie.optString(JSON_POSTER_KEY);
        String plot = jsonMovie.optString(JSON_PLOT_KEY);
        String releaseDate = jsonMovie.optString(JSON_RELEASE_DATE_KEY);
        double rating = jsonMovie.optDouble(JSON_RATING_KEY);

        return new Movie(title, posterId, plot, releaseDate, rating);
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getPosterId() {
        return posterId;
    }

    public String getPlot() {
        return plot;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public double getRating() {
        return rating;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "originalTitle='" + originalTitle + '\'' +
                ", posterId='" + posterId + '\'' +
                ", plot='" + plot + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", rating=" + rating +
                '}';
    }
}
