package com.myd.movies.mvp.presenter;

import com.myd.movies.common.data.remote.response.MoviesRemoteResponse;
import com.myd.movies.mvp.MovieListContract;
import com.myd.movies.mvp.model.Local.Movie;
import com.myd.movies.mvp.model.remote.MoviesRemoteDataSource;
import com.myd.movies.util.TestUtil;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import io.reactivex.Maybe;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.Mockito.timeout;
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
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> Schedulers.trampoline());
        presenter = new MovieListPresenter(dataSource, view);
    }

    @Test
    public void testDiscoverMovies() throws Exception {
        String date = "2018-2-27";
        Movie movie = TestUtil.createMovie(date);
        MoviesRemoteResponse response = TestUtil.createMoviesRemoteResponse(movie);

        when(dataSource.discoverMovies(1)).thenReturn(Maybe.just(response));
        when(dataSource.filterMovies(date, 1)).thenReturn(Maybe.just(response));
        presenter.discoverMovies(1, false);
        verify(view, times(1)).showProgress(false);
        verify(view, timeout(400).times(1)).showData(Collections.singletonList(movie), false);
    }

    @Test
    public void testFilterMovies() throws Exception {
        String date = "2018-2-27";
        Movie movie = TestUtil.createMovie(date);
        MoviesRemoteResponse response = TestUtil.createMoviesRemoteResponse(movie);

        when(dataSource.filterMovies(date, 1)).thenReturn(Maybe.just(response));
        presenter.filterMovies(date, 1, false);
        verify(view, times(1)).showProgress(false);
        verify(view, timeout(400).times(1)).showData(Collections.singletonList(movie), false);
    }
}
