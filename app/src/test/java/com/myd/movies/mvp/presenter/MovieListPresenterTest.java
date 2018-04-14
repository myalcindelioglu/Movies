package com.myd.movies.mvp.presenter;

import com.myd.movies.common.data.remote.response.MoviesRemoteResponse;
import com.myd.movies.mvp.MovieListContract;
import com.myd.movies.mvp.model.Local.Movies;
import com.myd.movies.mvp.model.remote.MoviesRemoteDataSource;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import io.reactivex.Maybe;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by MYD on 4/14/18.
 *
 */

public class MovieListPresenterTest {

    private MovieListPresenter presenter;

    @Mock
    private MoviesRemoteDataSource dataSource;

    @Mock
    private MovieListContract.View view;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        presenter = new MovieListPresenter(dataSource, view);
    }

    @Test
    public void testLoadMovies() throws Exception {
        Movies movies = new Movies(1, "Batman",
                "/eLQRLiu4jf4cLPzrp66M4lyDLQe.jpg",
                "2018-2-27");
        MoviesRemoteResponse response =
                new MoviesRemoteResponse(1, 1,
                        1, Arrays.asList(movies));

        when(dataSource.discoverMovies(1)).thenReturn(Maybe.just(response));
        presenter.loadMovies(1);
        verify(view, times(1)).showProgress(false);
        verify(view, times(1)).showData(Arrays.asList(movies));
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

        when(dataSource.filterMovies(date, 1)).thenReturn(Maybe.just(response));
        presenter.filterMovies(date, 1);
        verify(view, times(1)).showProgress(false);
        verify(view, times(1)).showData(Arrays.asList(movies));
    }
}
