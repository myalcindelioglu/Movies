package com.myd.movies.common.data.remote;

import com.myd.movies.common.data.remote.response.MoviesResponseBean;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by MYD on 4/11/18.
 *
 */

public interface TmdbService {
    @GET("discover/movie?sort_by=release_date.desc")
    Call<MoviesResponseBean> movieDiscoverByReleaseDateDesc();
}
