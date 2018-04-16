package com.myd.movies.mvp.model.remote;

import com.myd.movies.common.data.remote.TmdbService;
import com.myd.movies.mvp.model.Local.Movie;
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

        Movie movie = TestUtil.createMovie(1, "2018-04-12");
        MovieDetails movieDetails = TestUtil.createMovieDetails(movie);
        Mockito.when(tmdbService.getMovieDetails(movie.getId())).thenReturn(Single.just(movieDetails));
        TestObserver<MovieDetails> test = dataSource.getDetails(movie.getId()).test();
        test.assertNoErrors();
        test.assertValue(movieDetails);
    }
}
