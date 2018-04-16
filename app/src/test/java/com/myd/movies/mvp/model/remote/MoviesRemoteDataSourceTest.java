package com.myd.movies.mvp.model.remote;

import com.myd.movies.common.data.remote.TmdbService;
import com.myd.movies.mvp.model.Local.Movie;
import com.myd.movies.util.TestUtil;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Maybe;
import io.reactivex.observers.TestObserver;

import static org.mockito.Mockito.when;

/**
 * Created by MYD on 4/15/18.
 *
 */

public class MoviesRemoteDataSourceTest {

    private MoviesRemoteDataSource remoteDataSource;

    @Mock
    private TmdbService service;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        remoteDataSource = new MoviesRemoteDataSource(service);
    }

    @Test
    public void testDiscoverMovies() throws Exception {
        String date = "2018-2-27";
        Movie movie = TestUtil.createMovie(1, date);
        MoviesRemoteResponse response = TestUtil.createMoviesRemoteResponse(movie);

        when(service.movieDiscover(1)).thenReturn(Maybe.just(response));
        TestObserver<MoviesRemoteResponse> testObserver = remoteDataSource.discoverMovies(1).test();
        testObserver.assertNoErrors();
        testObserver.assertValue(response);
    }

    @Test
    public void testFilterMovies() throws Exception {
        String date = "2018-2-27";
        Movie movie = TestUtil.createMovie(1, date);
        MoviesRemoteResponse response = TestUtil.createMoviesRemoteResponse(movie);

        when(service.movieDiscoverFilterReleaseDateDesc(date, 1)).thenReturn(Maybe.just(response));
        TestObserver<MoviesRemoteResponse> testObserver = remoteDataSource.filterMovies(date, 1).test();
        testObserver.assertNoErrors();
        testObserver.assertValue(response);
    }
}
