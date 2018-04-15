package com.myd.movies.mvp.presenter;

import com.myd.movies.mvp.MovieDetailContract;
import com.myd.movies.mvp.model.Local.MovieDetails;
import com.myd.movies.mvp.model.remote.MovieDetailsDataSource;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import io.reactivex.Single;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by MYD on 4/15/18.
 *
 */

public class MovieDetailPresenterTest {
    private MovieDetailPresenter presenter;

    @Mock
    private MovieDetailsDataSource dataSource;

    @Mock
    private MovieDetailContract.View view;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> Schedulers.trampoline());
        presenter = new MovieDetailPresenter(dataSource, view);
    }

    @Test
    public void testGetDetails() throws Exception {
        int movieId = 1;
        MovieDetails movieDetails = createMovieDetails(1);
        Mockito.when(dataSource.getDetails(movieId)).thenReturn(Single.just(movieDetails));
        presenter.getDetails(movieId);
        Mockito.verify(view, Mockito.times(1)).showProgress();
        Mockito.verify(view, Mockito.times(1)).loadViews(movieDetails);
    }


    private MovieDetails createMovieDetails(int movieId) {
        MovieDetails.Genre genre = new MovieDetails.Genre(1, "drama");
        return new MovieDetails(movieId,
                "Batman Returns",
                "/poster.jpg",
                "backdrop.jpg",
                "2018-2-13",
                "en",
                8.9,
                1000,
                "overview text",
                Collections.singletonList(genre));

    }
}
