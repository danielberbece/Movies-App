package com.projects.daniel.moviesapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements ListAdapter.ListItemClickListener {

    private static final int NUM_LIST_ITEMS = 20;
    private static final int SPAN_COUNT = 2;
    public static final String EXTRA_DETAIL_KEY = "details_key";
    private ListAdapter mAdapter;
    private RecyclerView mMoviesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMoviesList = findViewById(R.id.movies_rv);
        mMoviesList.setHasFixedSize(true);

        mAdapter = new ListAdapter(this, NUM_LIST_ITEMS, this);
        mMoviesList.setAdapter(mAdapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, SPAN_COUNT);
        mMoviesList.setLayoutManager(gridLayoutManager);
    }

    @Override
    public void onItemClick(int itemIndex) {
        Intent detailActivityIntent = new Intent(this, DetailActivity.class);
        detailActivityIntent.putExtra(EXTRA_DETAIL_KEY, itemIndex);
        startActivity(detailActivityIntent);
    }
}
