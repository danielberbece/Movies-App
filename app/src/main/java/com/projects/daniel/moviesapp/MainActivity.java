package com.projects.daniel.moviesapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ListAdapter.ListItemClickListener {

    private static final int NUM_LIST_ITEMS = 20;
    private static final int SPAN_COUNT = 2;
    public static final String EXTRA_DETAIL_KEY = "details_key";
    private ListAdapter mAdapter;
    private RecyclerView mMoviesList;
    private ArrayList<String> mMoviesData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMoviesData = new ArrayList<>();

        mMoviesList = findViewById(R.id.movies_rv);
        mMoviesList.setHasFixedSize(true);

        mAdapter = new ListAdapter(this, NUM_LIST_ITEMS, this);
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
            parseJsonToMovie(s);
        }
    }

    private void parseJsonToMovie(String s) {
        mMoviesData.add(s);
    }

    @Override
    public void onItemClick(int itemIndex) {
        Intent detailActivityIntent = new Intent(this, DetailActivity.class);
        detailActivityIntent.putExtra(EXTRA_DETAIL_KEY, mMoviesData.get(0));
        startActivity(detailActivityIntent);
    }
}
