package com.myd.movies.mvp.model.remote;

import android.support.annotation.VisibleForTesting;

import com.myd.movies.common.data.remote.response.MoviesRemoteResponse;
import com.myd.movies.mvp.model.Local.Movie;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Maybe;

/**
 * Created by MYD on 4/16/18.
 *
 */

public class FakeMovieDataSource implements MoviesDataSource {

    public static final MoviesRemoteResponse SERVICE_DATA =
            new MoviesRemoteResponse(1, 0, 1, new ArrayList<>());

    @Inject
    public FakeMovieDataSource() {
    }

    @Override
    public Maybe<MoviesRemoteResponse> discoverMovies(int page) {
        return Maybe.just(SERVICE_DATA);
    }

    @Override
    public Maybe<MoviesRemoteResponse> filterMovies(String date, int page) {

        List<Movie> movies = new ArrayList<>();
        for (Movie movie :
                SERVICE_DATA.getResults()) {
            movies.add(new Movie(movie.getId(), movie.getTitle(), movie.getPoster_path(), date));
        }
        return Maybe.just(
                new MoviesRemoteResponse(
                        SERVICE_DATA.getPage(),
                        SERVICE_DATA.getTotal_results(),
                        SERVICE_DATA.getTotal_pages(),
                        movies));
    }

    @VisibleForTesting
    public void setServiceData(MoviesRemoteResponse remoteResponse) {
        SERVICE_DATA.setPage(remoteResponse.getPage());
        SERVICE_DATA.setTotal_results(remoteResponse.getTotal_results());
        SERVICE_DATA.setTotal_pages(remoteResponse.getTotal_pages());
        SERVICE_DATA.setResults(remoteResponse.getResults());
    }
}
