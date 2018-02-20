package com.projects.daniel.moviesapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    private TextView detailTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        detailTextView = findViewById(R.id.detail_tv);

        int extra = getIntent().getIntExtra(MainActivity.EXTRA_DETAIL_KEY, 0);
        detailTextView.setText(String.valueOf(extra));
    }
}
