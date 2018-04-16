package com.myd.movies.mvp.model.remote;

import android.support.annotation.NonNull;

import com.myd.movies.common.data.remote.TmdbService;

import javax.inject.Inject;

import io.reactivex.Maybe;

/**
 * Created by MYD on 4/12/18.
 *
 */

public class MoviesRemoteDataSource implements MoviesDataSource {

    private TmdbService tmdbService;

    @Inject
    public MoviesRemoteDataSource(@NonNull TmdbService tmdbService) {
        this.tmdbService = tmdbService;
    }

    @Override
    public Maybe<MoviesRemoteResponse> discoverMovies(int page) {
        return tmdbService.movieDiscover(page);
    }

    @Override
    public Maybe<MoviesRemoteResponse> filterMovies(String date, int page) {
        return tmdbService.movieDiscoverFilterReleaseDateDesc(
                date, page);
    }
}
