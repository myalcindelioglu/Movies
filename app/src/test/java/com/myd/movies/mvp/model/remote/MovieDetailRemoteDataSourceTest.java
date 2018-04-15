package com.myd.movies.mvp.model.remote;

import com.myd.movies.common.data.remote.TmdbService;
import com.myd.movies.mvp.model.Local.MovieDetails;
import com.myd.movies.util.TestUtil;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import io.reactivex.Single;
import io.reactivex.observers.TestObserver;

/**
 * Created by MYD on 4/15/18.
 *
 */

public class MovieDetailRemoteDataSourceTest {
    private MovieDetailsDataSource dataSource;

    @Mock
    private TmdbService tmdbService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        dataSource = new MovieDetailsRemoteDataSource(tmdbService);
    }

    @Test
    public void testGetDetails() throws Exception {

        int movieId = 1;
        MovieDetails movieDetails = TestUtil.createMovieDetails(movieId);
        Mockito.when(tmdbService.getMovieDetails(movieId)).thenReturn(Single.just(movieDetails));
        TestObserver<MovieDetails> test = dataSource.getDetails(movieId).test();
        test.assertNoErrors();
        test.assertValue(movieDetails);
    }
}
