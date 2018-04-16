package com.myd.movies.mvp.presenter;

import com.myd.movies.mvp.MainContract;
import com.myd.movies.util.DateUtil;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

/**
 * Created by MYD on 4/14/18.
 *
 */

public class MainPresenterTest {

    private MainPresenter presenter;

    @Mock
    private MainContract.View view;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        presenter = new MainPresenter();
        presenter.subscribe(view);
    }

    @Test
    public void testHandleOnMovieClick() throws Exception{
        presenter.handleOnMovieClick(1);
        verify(view, times(1)).showDetail(1);
    }

    @Test
    public void testFilterMovies() throws Exception {
        String date = DateUtil.intToString(2017, 4, 13);
        presenter.filterMovies(date);
        verify(view, times(1)).showFilteredMovies(date);
    }

    @Test
    public void testHandleFilterClick() throws Exception {
        presenter.handleFilterClick();
        verify(view, times(1)).showDatePicker();
    }

    @Test
    public void testHandleBackPress() throws Exception {
        presenter.handleBackPress();
        verify(view, times(1)).showMovieList();
    }

    @Test
    public void testSubscribe() throws Exception {
        presenter.subscribe(view);
        verify(view, times(1)).subscribeMovieOnClick();
    }
}
