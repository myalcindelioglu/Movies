package com.myd.movies.mvp;

import android.support.annotation.NonNull;

import com.myd.movies.common.base.BasePresenter;
import com.myd.movies.common.base.BaseView;
import com.myd.movies.mvp.model.Local.Movie;

import java.util.List;

/**
 * Created by MYD on 4/14/18.
 *
 */

public interface MovieListContract {
    interface View extends BaseView {
        void showProgress(boolean isLoadMore);
        void showData(List<Movie> movies, boolean isLoadMore);
        void showError(boolean isLoadMore);
    }

    interface Presenter extends BasePresenter<View> {
        void discoverMovies(int nextPage, boolean isLoadMore);
        void filterMovies(@NonNull String filterDate, int nextPage, boolean isLoadMore);
    }
}
