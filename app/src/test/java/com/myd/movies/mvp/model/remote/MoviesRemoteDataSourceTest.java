package com.myd.movies.mvp.model.remote;

import com.myd.movies.common.data.remote.TmdbService;
import com.myd.movies.common.data.remote.response.MoviesRemoteResponse;
import com.myd.movies.mvp.model.Local.Movies;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;

import java.util.Arrays;

import io.reactivex.Maybe;
import io.reactivex.observers.TestObserver;

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
    public void testFilterMovies() throws Exception {
        Movies movies = new Movies(1, "Batman",
                "/eLQRLiu4jf4cLPzrp66M4lyDLQe.jpg",
                "2018-2-27");
        MoviesRemoteResponse response =
                new MoviesRemoteResponse(1, 1,
                        1, Arrays.asList(movies));

        String date = "2018-2-27";
        when(service.movieDiscoverFilterReleaseDateDesc(date, 1)).thenReturn(Maybe.just(response));
        TestObserver<MoviesRemoteResponse> testObserver = remoteDataSource.filterMovies(date, 1).test();
        testObserver.assertNoErrors();
        testObserver.assertValue(response);
    }
}
