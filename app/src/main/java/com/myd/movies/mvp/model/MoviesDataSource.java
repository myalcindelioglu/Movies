package com.myd.movies.mvp.model;

import com.myd.movies.common.data.remote.response.MoviesRemoteResponse;

import io.reactivex.Maybe;

/**
 * Created by MYD on 4/12/18.
 *
 */

public interface MoviesDataSource {
    Maybe<MoviesRemoteResponse> discoverMovies(int page);
}