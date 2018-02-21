package com.projects.daniel.moviesapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.projects.daniel.moviesapp.model.Movie;

public class DetailActivity extends AppCompatActivity {

    private TextView detailTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        detailTextView = findViewById(R.id.detail_tv);

        Movie extra = (Movie) getIntent().getSerializableExtra(MainActivity.DETAILS_KEY);
        detailTextView.setText(extra.getOriginalTitle());
    }
}
