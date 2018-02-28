package com.projects.daniel.moviesapp;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.projects.daniel.moviesapp.adapters.TrailersAdapter;
import com.projects.daniel.moviesapp.models.Movie;
import com.projects.daniel.moviesapp.models.Trailer;
import com.projects.daniel.moviesapp.tasks.DetailsTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity implements DetailsTask.AfterLoading {

    private TextView titleTextView;
    private TextView ratingTextView;
    private TextView plotTextView;
    private TextView releaseDate;
    private ImageView posterView;
    private DetailsTask getDetailsTask;
    private RecyclerView trailersRecyclerView;
    private TrailersAdapter trailersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        titleTextView = findViewById(R.id.title_detail_view);
        ratingTextView = findViewById(R.id.rating_detail_view);
        plotTextView = findViewById(R.id.plot_detail_view);
        releaseDate = findViewById(R.id.release_date_detail_view);
        posterView = findViewById(R.id.poster_detail_view);

        Movie extra = (Movie) getIntent().getSerializableExtra(MainActivity.DETAILS_KEY);

        titleTextView.setText(extra.getOriginalTitle());
        ratingTextView.setText(String.valueOf(extra.getRating()));
        plotTextView.setText(extra.getPlot());
        releaseDate.setText(extra.getReleaseDate());

        Uri uri = Uri.parse(NetworkUtils.POSTER_BASE_URL).buildUpon()
                .appendEncodedPath(NetworkUtils.POSTER_SIZE)
                .appendEncodedPath(extra.getPosterId())
                .build();

        Picasso.with(this)
                .load(uri)
                .placeholder(R.drawable.ic_photo_black_24dp)
                .error(R.drawable.ic_error_outline_24dp)
                .into(posterView);


        getDetailsTask = new DetailsTask(this);
        getDetailsTask.execute(NetworkUtils.getTrailersUrl(extra.getId()));

        Log.d("First Trailers received", String.valueOf(getDetailsTask.getTrailers()));
        trailersRecyclerView = findViewById(R.id.trailers_recyclerview);
        trailersAdapter = new TrailersAdapter(this, getDetailsTask.getTrailers());

        trailersRecyclerView.setAdapter(trailersAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setAutoMeasureEnabled(true);
        trailersRecyclerView.setLayoutManager(linearLayoutManager);
        trailersRecyclerView.setNestedScrollingEnabled(false);
    }


    @Override
    public void onFinish() {
        trailersAdapter.setTrailers(getDetailsTask.getTrailers());
    }
}
