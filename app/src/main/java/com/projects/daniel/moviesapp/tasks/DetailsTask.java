package com.projects.daniel.moviesapp.tasks;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.projects.daniel.moviesapp.NetworkUtils;
import com.projects.daniel.moviesapp.adapters.TrailersAdapter;
import com.projects.daniel.moviesapp.models.Review;
import com.projects.daniel.moviesapp.models.Trailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Daniel on 3/1/2018.
 */

public class DetailsTask extends AsyncTask<URL, Void, String[]> {

    private ArrayList<Trailer> trailers;
    private ArrayList<Review> reviews;
    private AfterLoading afterLoading;

    public interface AfterLoading {
        public void onFinish();
    }

    public DetailsTask(AfterLoading afterLoading) {
        this.afterLoading = afterLoading;
    }

    @Override
    protected String[] doInBackground(URL... urls) {
        String[] response = new String[2];

        try {
            response[0] = NetworkUtils.getResponseFromHttpUrl(urls[0]);
            response[1] = NetworkUtils.getResponseFromHttpUrl(urls[1]);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

    @Override
    protected void onPostExecute(String[] jsonStrings) {
        super.onPostExecute(jsonStrings);

        if(jsonStrings != null) {
            try {
                Gson gson = new GsonBuilder().create();
                if(jsonStrings[0] != null) {
                    JSONObject jsonObject = new JSONObject(jsonStrings[0]);
                    JSONArray trailersJsonArray = jsonObject.getJSONArray("youtube");
                    ArrayList<Trailer> list = new ArrayList<>();
                    for (int i = 0; i < trailersJsonArray.length(); i++) {
                        list.add(Trailer.buildFromJson(trailersJsonArray.getJSONObject(i)));
                    }
                    trailers = new ArrayList<>(list);
                } else {
                    trailers = new ArrayList<>();
                }

                if(jsonStrings[1] != null) {
                    JSONObject jsonReviews = new JSONObject(jsonStrings[1]);
                    JSONArray reviewsJsonArray = jsonReviews.getJSONArray("results");
                    ArrayList<Review> reviewsList = new ArrayList<>();
                    for (int i = 0; i < reviewsJsonArray.length(); i++) {
                        reviewsList.add(gson.fromJson(reviewsJsonArray.getJSONObject(i).toString(), Review.class));
                    }
                    reviews = new ArrayList<>(reviewsList);
                } else {
                    reviews = new ArrayList<>();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            afterLoading.onFinish();
        }
    }

    public ArrayList<Trailer> getTrailers() {
        return trailers;
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }
}
