package com.myd.movies.mvp.model.remote;

import com.myd.movies.common.data.remote.TmdbService;
import com.myd.movies.common.data.remote.response.MoviesRemoteResponse;
import com.myd.movies.mvp.model.Local.Movie;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;

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
        Movie movie = new Movie(1, "Batman",
                "/eLQRLiu4jf4cLPzrp66M4lyDLQe.jpg",
                "2018-2-27");
        MoviesRemoteResponse response =
                new MoviesRemoteResponse(1, 1,
                        1, Collections.singletonList(movie));

        when(service.movieDiscover(1)).thenReturn(Maybe.just(response));
        TestObserver<MoviesRemoteResponse> testObserver = remoteDataSource.discoverMovies(1).test();
        testObserver.assertNoErrors();
        testObserver.assertValue(response);
    }

    @Test
    public void testFilterMovies() throws Exception {
        Movie movie = new Movie(1, "Batman",
                "/eLQRLiu4jf4cLPzrp66M4lyDLQe.jpg",
                "2018-2-27");
        MoviesRemoteResponse response =
                new MoviesRemoteResponse(1, 1,
                        1, Collections.singletonList(movie));

        String date = "2018-2-27";
        when(service.movieDiscoverFilterReleaseDateDesc(date, 1)).thenReturn(Maybe.just(response));
        TestObserver<MoviesRemoteResponse> testObserver = remoteDataSource.filterMovies(date, 1).test();
        testObserver.assertNoErrors();
        testObserver.assertValue(response);
    }
}
