package com.myd.movies.mvp.presenter;

import android.util.Log;

import com.myd.movies.mvp.MovieDetailContract;
import com.myd.movies.mvp.model.Local.MovieDetails;
import com.myd.movies.mvp.model.remote.MovieDetailsDataSource;
import com.myd.movies.util.RxUtil;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by MYD on 4/15/18.
 *
 */

public class MovieDetailPresenter implements MovieDetailContract.Presenter {

    private static final String TAG = "MovieDetailPresenter";

    private CompositeDisposable subscriptions = new CompositeDisposable();

    private MovieDetailsDataSource dataSource;
    private MovieDetailContract.View view;

    public MovieDetailPresenter(MovieDetailsDataSource dataSource, MovieDetailContract.View view) {
        this.dataSource = dataSource;
        this.view = view;
    }

    @Override
    public void getDetails(int movieId) {
        view.showProgress();
        Single<MovieDetails> movieDetailsSingle =
            dataSource.getDetails(movieId).compose(RxUtil.applySingleSchedulers());

        movieDetailsSingle.subscribe(movieDetails -> {
                    Log.e(TAG, "getDetails has an error for movieId= " + movieId);
                    view.loadViews(movieDetails);
                },
                e -> {
                    Log.e(TAG, "getDetails has an error for movieId= " + movieId, e);
                    view.showError();
                }
        );
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        RxUtil.unSubscribe(subscriptions);
    }
}
