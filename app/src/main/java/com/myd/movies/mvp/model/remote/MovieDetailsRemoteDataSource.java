package com.myd.movies.mvp.model.remote;

import com.myd.movies.mvp.model.Local.MovieDetails;
import com.myd.movies.util.TmdbServiceHelper;

import io.reactivex.Single;

/**
 * Created by MYD on 4/12/18.
 *
 */

public class MovieDetailsRemoteDataSource implements MovieDetailsDataSource {
    @Override
    public Single<MovieDetails> getDetails(int movieId) {
        return TmdbServiceHelper.getService().getMovieDetails(movieId);
    }
}
