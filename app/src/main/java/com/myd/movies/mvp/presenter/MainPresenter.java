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

    }

    @Override
    public void handleOnMovieClick(int movieId) {

    }

    @Override
    public void handleFilterClick() {

    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }
}
