package com.myd.movies.mvp.presenter;

import com.myd.movies.mvp.MainContract;

import javax.inject.Inject;

/**
 * Created by MYD on 4/14/18.
 *
 */

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View view;

    @Inject
    public MainPresenter() {

    }

    @Override
    public void filterMovies(String date) {
        view.showFilteredMovies(date);
    }

    @Override
    public void handleOnMovieClick(int movieId) {
        view.showDetail(movieId);
    }

    @Override
    public void handleFilterClick() {
        view.showDatePicker();
    }

    @Override
    public void handleBackPress() {
        view.showMovieList();
    }

    @Override
    public void subscribe(MainContract.View view) {
        this.view = view;
        this.view.subscribeMovieOnClick();
    }

    @Override
    public void unSubscribe() {
    }
}
