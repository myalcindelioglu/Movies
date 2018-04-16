package com.myd.movies.mvp.presenter;

import android.util.Log;

import com.myd.movies.mvp.MovieDetailContract;
import com.myd.movies.mvp.model.Local.MovieDetails;
import com.myd.movies.mvp.model.remote.MovieDetailsDataSource;
import com.myd.movies.util.RxUtil;

import javax.inject.Inject;

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

    @Inject
    public MovieDetailPresenter(MovieDetailsDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void getDetails(int movieId) {
        view.showProgress();
        Single<MovieDetails> movieDetailsSingle =
            dataSource.getDetails(movieId).compose(RxUtil.applySingleSchedulers());

        movieDetailsSingle.subscribe(movieDetails -> {
                    Log.d(TAG, "getDetails = " + movieDetails);
                    view.loadViews(movieDetails);
                },
                e -> {
                    Log.e(TAG, "getDetails has an error for movieId= " + movieId, e);
                    view.showError();
                }
        );
    }

    public void subscribe(MovieDetailContract.View view) {
        this.view = view;

    }

    @Override
    public void unSubscribe() {
        RxUtil.unSubscribe(subscriptions);
    }
}
