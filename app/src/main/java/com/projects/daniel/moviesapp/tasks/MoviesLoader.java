package com.projects.daniel.moviesapp.tasks;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.database.Cursor;

import com.projects.daniel.moviesapp.data.MoviesContract;

public class MoviesLoader extends AsyncTaskLoader<Cursor> {
    private Cursor data;
    private Context context;

    public MoviesLoader(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onStartLoading() {
        if(data != null) {
            deliverResult(data);
        } else {
            forceLoad();
        }
    }

    @Override
    public Cursor loadInBackground() {
        try {
            return context.getContentResolver().query(MoviesContract.FavoriteMovieEntry.CONTENT_URI,
                    null,
                    null,
                    null,
                    MoviesContract.FavoriteMovieEntry.COLUMN_TITLE);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void deliverResult(Cursor data) {
        super.deliverResult(data);
        this.data = data;
    }
}
