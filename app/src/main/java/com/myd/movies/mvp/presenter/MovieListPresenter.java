package com.myd.movies.mvp.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.myd.movies.mvp.model.remote.MoviesRemoteResponse;
import com.myd.movies.mvp.MovieListContract;
import com.myd.movies.mvp.model.remote.MoviesDataSource;
import com.myd.movies.util.RxUtil;

import javax.inject.Inject;

import io.reactivex.Maybe;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by MYD on 4/15/18.
 *
 */

public class MovieListPresenter implements MovieListContract.Presenter {

    private static final String TAG = "MovieListPresenter";

    private CompositeDisposable subscriptions = new CompositeDisposable();

    private MoviesDataSource moviesDataSource;
    private MovieListContract.View view;

    private int totalPages = 0;
    private String filterDate = "";

    @Inject
    MovieListPresenter(MoviesDataSource moviesDataSource) {
        this.moviesDataSource = moviesDataSource;
    }

    @Override
    public void discoverMovies(int nextPage, boolean isLoadMore) {

        if (isLoadMore && totalPages <= nextPage) {
            return;
        }

        view.showProgress(isLoadMore);

        Maybe<MoviesRemoteResponse> responseMaybe;
        if (this.filterDate.isEmpty()) {
            responseMaybe = moviesDataSource.discoverMovies(nextPage).compose(RxUtil.applyMaybeSchedulers());
        } else {
            responseMaybe = moviesDataSource.filterMovies(filterDate, nextPage).compose(RxUtil.applyMaybeSchedulers());

        }

        subscribeResponse(responseMaybe, isLoadMore);

    }

    @Override
    public void filterMovies(@NonNull String filterDate, int nextPage, boolean isLoadMore) {

        if (isLoadMore && totalPages <= nextPage) {
            return;
        }

        this.filterDate = filterDate;
        view.showProgress(isLoadMore);

        Maybe<MoviesRemoteResponse> responseMaybe =
                moviesDataSource.filterMovies(filterDate, nextPage)
                        .compose(RxUtil.applyMaybeSchedulers());

        subscribeResponse(responseMaybe, isLoadMore);

    }

    void subscribeResponse(Maybe<MoviesRemoteResponse> responseMaybe, boolean isLoadMore) {
        Disposable disposable = responseMaybe.subscribe(resp -> {
                    Log.d(TAG, resp.getResults().toString() + " totalPages = " + totalPages);
                    view.showData(resp.getResults(), isLoadMore);
                    totalPages = resp.getTotal_pages();
                }, e -> {
                    Log.e(TAG, "filterDate isEmpty = " + filterDate.isEmpty() + " discoverMovies has an error", e);
                    view.showError(isLoadMore);
                }
        );

        subscriptions.add(disposable);
    }

    @Override
    public void subscribe(MovieListContract.View view) {
        this.view = view;
    }

    @Override
    public void unSubscribe() {
        RxUtil.unSubscribe(subscriptions);
    }
}
