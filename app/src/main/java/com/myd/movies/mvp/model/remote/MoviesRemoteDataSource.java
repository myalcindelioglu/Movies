package com.myd.movies.mvp.model.remote;

import com.myd.movies.common.data.remote.response.MoviesRemoteResponse;
import com.myd.movies.mvp.model.MoviesDataSource;
import com.myd.movies.util.DateUtil;
import com.myd.movies.util.TmdbServiceHelper;

import io.reactivex.Maybe;

/**
 * Created by MYD on 4/12/18.
 *
 */

public class MoviesRemoteDataSource implements MoviesDataSource {
    @Override
    public Maybe<MoviesRemoteResponse> discoverMovies(int page) {
        return TmdbServiceHelper.getService().movieDiscoverByReleaseDateDesc(
                DateUtil.epochToString(System.currentTimeMillis()), page);
    }
}
