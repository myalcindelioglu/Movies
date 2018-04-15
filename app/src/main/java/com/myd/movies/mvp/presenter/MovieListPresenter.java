package com.myd.movies.mvp.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.myd.movies.common.data.remote.response.MoviesRemoteResponse;
import com.myd.movies.mvp.MovieListContract;
import com.myd.movies.mvp.model.remote.MoviesRemoteDataSource;
import com.myd.movies.util.RxUtil;

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

    private MoviesRemoteDataSource moviesRemoteDataSource;
    private MovieListContract.View view;

    private int totalPages = 0;
    private String filterDate = "";

    public MovieListPresenter(MoviesRemoteDataSource moviesRemoteDataSource,
                              MovieListContract.View view) {
        this.moviesRemoteDataSource = moviesRemoteDataSource;
        this.view = view;
    }


    @Override
    public void discoverMovies(int nextPage, boolean isLoadMore) {

        if (isLoadMore && totalPages <= nextPage) {
            return;
        }

        view.showProgress(isLoadMore);

        Maybe<MoviesRemoteResponse> responseMaybe;
        if (this.filterDate .isEmpty()) {
            responseMaybe = moviesRemoteDataSource.discoverMovies(nextPage).compose(RxUtil.applyMaybeSchedulers());
        } else {
            responseMaybe = moviesRemoteDataSource.filterMovies(filterDate, nextPage).compose(RxUtil.applyMaybeSchedulers());

        }

        Disposable disposable = responseMaybe.subscribe(resp -> {
                    view.showData(resp.getResults(), isLoadMore);
                    totalPages = resp.getTotal_pages();
                }, e -> Log.e(TAG, "discoverMovies has an error", e)
        );

        subscriptions.add(disposable);

    }

    @Override
    public void filterMovies(@NonNull String filterDate, int nextPage, boolean isLoadMore) {

        if (isLoadMore && totalPages <= nextPage) {
            return;
        }

        this.filterDate = filterDate;
        view.showProgress(isLoadMore);

        Maybe<MoviesRemoteResponse> responseMaybe =
                moviesRemoteDataSource.filterMovies(filterDate, nextPage)
                        .compose(RxUtil.applyMaybeSchedulers());

        Disposable disposable = responseMaybe.subscribe(resp -> {
                    view.showData(resp.getResults(), isLoadMore);
                    totalPages = resp.getTotal_pages();
                }, e -> Log.e(TAG, "discoverMovies has an error", e)
        );

        subscriptions.add(disposable);

    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        RxUtil.unSubscribe(subscriptions);
    }
}
