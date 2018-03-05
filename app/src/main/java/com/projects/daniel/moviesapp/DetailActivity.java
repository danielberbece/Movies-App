package com.projects.daniel.moviesapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.projects.daniel.moviesapp.adapters.ReviewsAdapter;
import com.projects.daniel.moviesapp.adapters.TrailersAdapter;
import com.projects.daniel.moviesapp.data.MoviesContract.FavoriteMovieEntry;
import com.projects.daniel.moviesapp.models.Movie;
import com.projects.daniel.moviesapp.tasks.DetailsTask;
import com.squareup.picasso.Picasso;


public class DetailActivity extends AppCompatActivity implements DetailsTask.AfterLoading {

    private Movie movie;
    private TextView titleTextView;
    private TextView ratingTextView;
    private TextView plotTextView;
    private TextView releaseDate;
    private ImageView posterView;
    private DetailsTask getDetailsTask;
    private RecyclerView trailersRecyclerView;
    private TrailersAdapter trailersAdapter;
    private RecyclerView reviewsRecyclerView;
    private ReviewsAdapter reviewsAdapter;
    private Menu menu;


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

        movie = (Movie) getIntent().getSerializableExtra(MainActivity.DETAILS_KEY);

        titleTextView.setText(movie.getOriginalTitle());
        ratingTextView.setText(String.valueOf(movie.getRating()));
        plotTextView.setText(movie.getPlot());
        releaseDate.setText(movie.getReleaseDate());

        Uri uri = Uri.parse(NetworkUtils.POSTER_BASE_URL).buildUpon()
                .appendEncodedPath(NetworkUtils.POSTER_SIZE)
                .appendEncodedPath(movie.getPosterId())
                .build();

        Picasso.with(this)
                .load(uri)
                .placeholder(R.drawable.ic_photo_black_24dp)
                .error(R.drawable.ic_error_outline_24dp)
                .into(posterView);


        getDetailsTask = new DetailsTask(this);
        getDetailsTask.execute(NetworkUtils.getTrailersUrl(movie.getId()), NetworkUtils.getReviewsUrl(movie.getId()));

        Log.d("First Trailers received", String.valueOf(getDetailsTask.getTrailers()));
        trailersRecyclerView = findViewById(R.id.trailers_recyclerview);
        trailersAdapter = new TrailersAdapter(this, getDetailsTask.getTrailers());

        trailersRecyclerView.setAdapter(trailersAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        trailersRecyclerView.setLayoutManager(linearLayoutManager);

        reviewsRecyclerView = findViewById(R.id.reviews_recyclerview);
        reviewsAdapter = new ReviewsAdapter();
        reviewsRecyclerView.setAdapter(reviewsAdapter);
        reviewsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }


    @Override
    public void onFinish() {
        trailersAdapter.setTrailers(getDetailsTask.getTrailers());
        reviewsAdapter.setTrailers(getDetailsTask.getReviews());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.detail_menu, menu);
        this.menu = menu;

        if(isFavorite(movie)){
            menu.getItem(0).setIcon(getDrawable(R.drawable.ic_star_filled_24dp));
        }

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.mark_favorite:
                // Add movie to database, if it doesn't exist there:
                if(!isFavorite(movie)) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(FavoriteMovieEntry.COLUMN_MOVIE_ID, movie.getId());
                    contentValues.put(FavoriteMovieEntry.COLUMN_POSTER_ID, movie.getPosterId());
                    contentValues.put(FavoriteMovieEntry.COLUMN_PLOT, movie.getPlot());
                    contentValues.put(FavoriteMovieEntry.COLUMN_TITLE, movie.getOriginalTitle());
                    contentValues.put(FavoriteMovieEntry.COLUMN_RELEASE_DATE, movie.getReleaseDate());
                    contentValues.put(FavoriteMovieEntry.COLUMN_RATING, movie.getRating());

                    getContentResolver().insert(FavoriteMovieEntry.CONTENT_URI, contentValues);
                    menu.getItem(0).setIcon(getDrawable(R.drawable.ic_star_filled_24dp));
                    Toast.makeText(this, "Added to favorites", Toast.LENGTH_SHORT).show();
                } else {
                    deleteFromDatabase(movie);
                    menu.getItem(0).setIcon(getDrawable(R.drawable.ic_star_border_24dp));
                    Toast.makeText(this, "Removed from favorites", Toast.LENGTH_SHORT).show();
                }
                break;
            case android.R.id.home:
                finish();
                break;
        }

        return true;
    }

    private boolean isFavorite(Movie movie) {
        Cursor cursor = getContentResolver().query(FavoriteMovieEntry.CONTENT_URI, null,
                "movie_id=" +
                        String.valueOf(movie.getId()),
                null,
                null);
        if(cursor == null || cursor.getCount() == 0) return false;
        cursor.close();
        return true;
    }

    private void deleteFromDatabase(Movie movie) {
        String movieId = String.valueOf(movie.getId());
        Uri uri = FavoriteMovieEntry.CONTENT_URI;
        uri = uri.buildUpon().appendPath(movieId).build();
        getContentResolver().delete(uri, null, null);
    }
}
