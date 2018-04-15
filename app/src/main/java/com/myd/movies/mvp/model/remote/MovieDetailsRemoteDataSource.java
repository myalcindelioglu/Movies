package com.myd.movies.mvp.model.remote;

import com.myd.movies.common.data.remote.TmdbService;
import com.myd.movies.mvp.model.Local.MovieDetails;

import io.reactivex.Single;

/**
 * Created by MYD on 4/12/18.
 *
 */

public class MovieDetailsRemoteDataSource implements MovieDetailsDataSource {
    private  TmdbService tmdbService;

    public MovieDetailsRemoteDataSource(TmdbService tmdbService) {
        this.tmdbService = tmdbService;
    }

    @Override
    public Single<MovieDetails> getDetails(int movieId) {
        return tmdbService.getMovieDetails(movieId);
    }
}
