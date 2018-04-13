package com.myd.movies.mvp.model.remote;

import com.myd.movies.common.data.remote.response.MoviesRemoteResponse;
import com.myd.movies.mvp.model.MovieDetails;
import com.myd.movies.mvp.model.MovieDetailsDataSource;
import com.myd.movies.mvp.model.MoviesDataSource;
import com.myd.movies.util.DateUtil;
import com.myd.movies.util.TmdbServiceHelper;

import io.reactivex.Maybe;
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
