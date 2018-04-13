package com.myd.movies.mvp.model;

import com.myd.movies.common.data.remote.response.MoviesRemoteResponse;

import io.reactivex.Maybe;
import io.reactivex.Single;

/**
 * Created by MYD on 4/12/18.
 *
 */

public interface MovieDetailsDataSource {
    Single<MovieDetails> getDetails(int movieId);
}
