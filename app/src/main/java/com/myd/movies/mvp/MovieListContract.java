package com.myd.movies.mvp;

import android.support.annotation.NonNull;

import com.myd.movies.common.base.BasePresenter;
import com.myd.movies.common.base.BaseView;
import com.myd.movies.mvp.model.Local.Movies;

import java.util.List;

/**
 * Created by MYD on 4/14/18.
 *
 */

public interface MovieListContract {
    interface View extends BaseView {
        void showProgress(boolean isLoadMore);
        void showData(List<Movies> movies, boolean isLoadMore);
        void showError(boolean isLoadMore);
    }

    interface Presenter extends BasePresenter {
        void discoverMovies(int nextPage, boolean isLoadMore);
        void filterMovies(@NonNull String filterDate, int nextPage, boolean isLoadMore);
    }
}
