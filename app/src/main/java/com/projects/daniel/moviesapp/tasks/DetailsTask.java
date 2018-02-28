package com.projects.daniel.moviesapp.tasks;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.projects.daniel.moviesapp.NetworkUtils;
import com.projects.daniel.moviesapp.adapters.TrailersAdapter;
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

public class DetailsTask extends AsyncTask<URL, Void, String> {

    private ArrayList<Trailer> trailers;
    private AfterLoading afterLoading;

    public interface AfterLoading {
        public void onFinish();
    }

    public DetailsTask(AfterLoading afterLoading) {
        this.afterLoading = afterLoading;
    }

    @Override
    protected String doInBackground(URL... urls) {
        URL url = urls[0];

        String response = null;
        try {
            response = NetworkUtils.getResponseFromHttpUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

    @Override
    protected void onPostExecute(String jsonString) {
        super.onPostExecute(jsonString);

        if(jsonString != null) {
            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                JSONArray trailersJsonArray = jsonObject.getJSONArray("youtube");
                ArrayList<Trailer> list = new ArrayList<>();
                for(int i = 0; i < trailersJsonArray.length(); i++) {
                    list.add(Trailer.buildFromJson(trailersJsonArray.getJSONObject(i)));
                }
                trailers = new ArrayList<>(list);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            afterLoading.onFinish();
        }
    }

    public ArrayList<Trailer> getTrailers() {
        return trailers;
    }
}
