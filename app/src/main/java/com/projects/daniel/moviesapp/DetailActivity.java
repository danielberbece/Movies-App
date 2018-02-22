package com.projects.daniel.moviesapp;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.projects.daniel.moviesapp.model.Movie;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private TextView titleTextView;
    private TextView ratingTextView;
    private TextView plotTextView;
    private TextView releaseDate;
    private ImageView posterView;

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
                .resize(500, 750)
                .into(posterView);
    }
}
