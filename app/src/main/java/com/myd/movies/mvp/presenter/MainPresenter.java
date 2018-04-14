package com.myd.movies.mvp.presenter;

import com.myd.movies.mvp.view.MainContract;

/**
 * Created by MYD on 4/14/18.
 *
 */

public class MainPresenter implements MainContract.Presenter{

    private MainContract.View view;
    public MainPresenter(MainContract.View view) {
        this.view = view;
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
    public void subscribe() {
        view.subscribeMovieOnClick();
    }

    @Override
    public void unSubscribe() {

    }
}
