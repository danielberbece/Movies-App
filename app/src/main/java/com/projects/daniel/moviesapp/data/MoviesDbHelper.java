package com.projects.daniel.moviesapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.projects.daniel.moviesapp.data.MoviesContract.FavoriteMovieEntry;

public class MoviesDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "favoritesDb.db";

    private static final int VERSION = 1;

    public MoviesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_TABLE = "CREATE TABLE "  + FavoriteMovieEntry.TABLE_NAME + " (" +
                FavoriteMovieEntry._ID                 + " INTEGER PRIMARY KEY, " +
                FavoriteMovieEntry.COLUMN_MOVIE_ID     + " INTEGER NOT NULL, " +
                FavoriteMovieEntry.COLUMN_TITLE        + " TEXT NOT NULL, " +
                FavoriteMovieEntry.COLUMN_PLOT         + " TEXT NOT NULL, " +
                FavoriteMovieEntry.COLUMN_POSTER_ID    + " TEXT NOT NULL, " +
                FavoriteMovieEntry.COLUMN_RATING       + " DOUBLE NOT NULL, " +
                FavoriteMovieEntry.COLUMN_RELEASE_DATE + " TEXT NOT NULL" +
                ");";

        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FavoriteMovieEntry.TABLE_NAME);
        onCreate(db);
    }
}
