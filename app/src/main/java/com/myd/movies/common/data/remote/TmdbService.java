package com.myd.movies.common.data.remote;

import com.myd.movies.common.data.remote.response.MoviesRemoteResponse;
import com.myd.movies.mvp.model.Local.MovieDetails;

import io.reactivex.Maybe;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by MYD on 4/11/18.
 *
 */

public interface TmdbService {
    @GET("discover/movie?sort_by=release_date.desc")
    Maybe<MoviesRemoteResponse> movieDiscover(@Query("page") int page);

    @GET("discover/movie?sort_by=release_date.desc")
    Maybe<MoviesRemoteResponse> movieDiscoverFilterReleaseDateDesc(
            @Query("release_date.lte") String date, @Query("page") int page);

    @GET("movie/{movieId}")
    Single<MovieDetails> getMovieDetails(@Path("movieId") int movieId);

}
