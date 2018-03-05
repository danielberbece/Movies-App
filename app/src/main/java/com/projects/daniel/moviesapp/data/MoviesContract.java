package com.projects.daniel.moviesapp.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Daniel on 3/5/2018.
 */

public class MoviesContract {

    public static final String AUTHORITY = "com.projects.daniel.moviesapp";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_FAVORITES = "favorites";

    public static final class FavoriteMovieEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendEncodedPath(PATH_FAVORITES).build();
        public static final String TABLE_NAME = "favorites";
        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_PLOT = "plot";
        public static final String COLUMN_RATING = "rating";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_POSTER_ID = "poster_id";
    }
}
