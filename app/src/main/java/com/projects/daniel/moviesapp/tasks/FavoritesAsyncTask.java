package com.projects.daniel.moviesapp.tasks;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import com.projects.daniel.moviesapp.data.MoviesContract;
import com.projects.daniel.moviesapp.models.Movie;

/**
 * Created by Daniel on 3/5/2018.
 */

public class FavoritesAsyncTask extends AsyncTask<Void, Void, Cursor> {

    private Context context;
    private DetailsTask.AfterLoading afterLoading;
    private Cursor cursorData;

    public FavoritesAsyncTask(Context context, DetailsTask.AfterLoading afterLoading) {
        this.context = context;
        this.afterLoading = afterLoading;
    }

    @Override
    protected Cursor doInBackground(Void... voids) {

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
    protected void onPostExecute(Cursor cursor) {
        super.onPostExecute(cursor);
        cursorData = cursor;
        afterLoading.onFinish();
    }

    public Cursor getCursorData() {
        return cursorData;
    }
}
