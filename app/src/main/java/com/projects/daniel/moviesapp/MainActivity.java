package com.projects.daniel.moviesapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.projects.daniel.moviesapp.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ListAdapter.ListItemClickListener {

    private TextView errorTextView;
    private static final int SPAN_COUNT_PORT = 2;
    private static final int SPAN_COUNT_LAND = 3;
    public static final String DETAILS_KEY = "details_key";
    private ListAdapter mAdapter;
    private RecyclerView mMoviesList;
    private ArrayList<Movie> mMoviesData;
    private String mCurrentMovies;
    private ProgressBar loadingProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        errorTextView = findViewById(R.id.error_tv);
        loadingProgressBar = findViewById(R.id.progress_bar);

        mMoviesData = new ArrayList<>();

        mMoviesList = findViewById(R.id.movies_rv);
        mMoviesList.setHasFixedSize(true);

        mAdapter = new ListAdapter(this, mMoviesData, this);
        mMoviesList.setAdapter(mAdapter);


        int spanCount = SPAN_COUNT_PORT;
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            spanCount = SPAN_COUNT_LAND;
        }

        GridLayoutManager gridLayoutManager =
                new GridLayoutManager(this, spanCount);
        gridLayoutManager.setItemPrefetchEnabled(true);
        mMoviesList.setLayoutManager(gridLayoutManager);

        updateCurrentMovies(NetworkUtils.POPULAR_MOVIES);

        URL popularMoviesUrl = NetworkUtils.getMoviesQueryUrl(NetworkUtils.POPULAR_MOVIES);
        new MoviesQueryTask().execute(popularMoviesUrl);
    }

    private void updateCurrentMovies(String popularMovies) {
        mCurrentMovies = popularMovies;
        if(mCurrentMovies.equals(NetworkUtils.POPULAR_MOVIES)) {
            getSupportActionBar().setTitle("Movies: Most popular");
        } else if(mCurrentMovies.equals(NetworkUtils.TOP_RATED_MOVIES)) {
            getSupportActionBar().setTitle("Movies: Top rated");
        }
    }

    public class MoviesQueryTask extends AsyncTask<URL, Void, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadingProgressBar.setVisibility(View.VISIBLE);
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
        protected void onPostExecute(String s) {
            loadingProgressBar.setVisibility(View.INVISIBLE);
            if( s != null ) {
                errorTextView.setVisibility(View.INVISIBLE);
                parseJsonToMovies(s);
                mAdapter.setList(mMoviesData);
            } else {
                errorTextView.setVisibility(View.VISIBLE);
            }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.search_by_rating_menu_item:
                if(!mCurrentMovies.equals(NetworkUtils.TOP_RATED_MOVIES)) {
                    updateCurrentMovies(NetworkUtils.TOP_RATED_MOVIES);
                    URL topRatedMoviesUrl = NetworkUtils.getMoviesQueryUrl(NetworkUtils.TOP_RATED_MOVIES);
                    new MoviesQueryTask().execute(topRatedMoviesUrl);
                }
                break;
            case R.id.search_by_popularity_menu_item:
                if(!mCurrentMovies.equals(NetworkUtils.POPULAR_MOVIES)) {
                    updateCurrentMovies(NetworkUtils.POPULAR_MOVIES);
                    URL popularMoviesUrl = NetworkUtils.getMoviesQueryUrl(NetworkUtils.POPULAR_MOVIES);
                    new MoviesQueryTask().execute(popularMoviesUrl);
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
