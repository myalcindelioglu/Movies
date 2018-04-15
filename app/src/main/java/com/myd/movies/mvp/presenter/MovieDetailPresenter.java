package com.myd.movies.mvp.presenter;

import com.myd.movies.mvp.MovieDetailContract;
import com.myd.movies.mvp.model.remote.MovieDetailsDataSource;
import com.myd.movies.util.RxUtil;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by MYD on 4/15/18.
 *
 */

public class MovieDetailPresenter implements MovieDetailContract.Presenter {

    private CompositeDisposable subscriptions = new CompositeDisposable();

    private MovieDetailsDataSource dataSource;
    private MovieDetailContract.View view;

    public MovieDetailPresenter(MovieDetailsDataSource dataSource, MovieDetailContract.View view) {
        this.dataSource = dataSource;
        this.view = view;
    }

    @Override
    public void getDetails(int movieId) {

    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        RxUtil.unSubscribe(subscriptions);
    }
}
