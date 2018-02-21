package com.projects.daniel.moviesapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.projects.daniel.moviesapp.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ListAdapter.ListItemClickListener {

    private static final int NUM_LIST_ITEMS = 20;
    private static final int SPAN_COUNT = 2;
    public static final String DETAILS_KEY = "details_key";
    private ListAdapter mAdapter;
    private RecyclerView mMoviesList;
    private ArrayList<Movie> mMoviesData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMoviesData = new ArrayList<>();

        mMoviesList = findViewById(R.id.movies_rv);
        mMoviesList.setHasFixedSize(true);

        mAdapter = new ListAdapter(this, mMoviesData, this);
        mMoviesList.setAdapter(mAdapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, SPAN_COUNT);
        mMoviesList.setLayoutManager(gridLayoutManager);

        URL popularMoviesUrl = NetworkUtils.getMoviesQueryUrl(NetworkUtils.POPULAR_MOVIES);
        new MoviesQueryTask().execute(popularMoviesUrl);
    }

    public class MoviesQueryTask extends AsyncTask<URL, Void, String>{

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
        protected void onPostExecute(String s) {
            parseJsonToMovies(s);
            mAdapter.setList(mMoviesData);
        }
    }

    private void parseJsonToMovies(String s) {
        mMoviesData.clear();
        try {
            JSONObject json = new JSONObject(s);
            JSONArray jsonMovies = json.getJSONArray("results");
            for(int i = 0; i < jsonMovies.length(); i++) {
                JSONObject jsonMovie = jsonMovies.getJSONObject(i);
                Movie movie = Movie.objectFromJson(jsonMovie);
                mMoviesData.add(movie);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(int itemIndex) {
        Intent detailActivityIntent = new Intent(this, DetailActivity.class);
        detailActivityIntent.putExtra(DETAILS_KEY, mMoviesData.get(itemIndex));
        startActivity(detailActivityIntent);
    }
}
