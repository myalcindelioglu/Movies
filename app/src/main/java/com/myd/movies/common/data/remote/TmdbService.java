package com.myd.movies.common.data.remote;

import com.myd.movies.common.data.remote.response.MoviesRemoteResponse;

import io.reactivex.Maybe;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by MYD on 4/11/18.
 *
 */

public interface TmdbService {
    @GET("discover/movie?sort_by=release_date.desc")
    Maybe<MoviesRemoteResponse> movieDiscoverFilterReleaseDateDesc(
            @Query("release_date.lte") String date, @Query("page") int page);

}
