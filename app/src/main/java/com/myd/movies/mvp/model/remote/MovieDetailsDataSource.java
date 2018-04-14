package com.myd.movies.mvp.model.remote;

import com.myd.movies.mvp.model.Local.MovieDetails;

import io.reactivex.Single;

/**
 * Created by MYD on 4/12/18.
 *
 */

public interface MovieDetailsDataSource {
    Single<MovieDetails> getDetails(int movieId);
}
