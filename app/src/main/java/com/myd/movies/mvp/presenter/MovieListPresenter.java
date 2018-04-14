package com.myd.movies.mvp.presenter;

import com.myd.movies.mvp.MainContract;
import com.myd.movies.mvp.MovieListContract;
import com.myd.movies.mvp.model.remote.MoviesRemoteDataSource;
import com.myd.movies.util.RxUtil;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by MYD on 4/15/18.
 *
 */

public class MovieListPresenter implements MovieListContract.Presenter {

    private CompositeDisposable subscriptions = new CompositeDisposable();

    private MoviesRemoteDataSource moviesRemoteDataSource;
    private MovieListContract.View view;

    MovieListPresenter(MoviesRemoteDataSource moviesRemoteDataSource,
                              MovieListContract.View view) {
        this.moviesRemoteDataSource = moviesRemoteDataSource;
        this.view = view;
    }

    @Override
    public void loadMovies(int nextPage) {

    }

    @Override
    public void filterMovies(String filterDate, int nextPage) {

    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        RxUtil.unSubscribe(subscriptions);
    }
}
