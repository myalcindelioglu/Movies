package com.myd.movies.mvp.view;

import com.myd.movies.common.base.BasePresenter;
import com.myd.movies.common.base.BaseView;

/**
 * Created by MYD on 4/14/18.
 *
 */

public interface MainContract {
    interface View extends BaseView {
        void showDetail(int movieId);
        void showFilteredMovies(String date);
        void showDatePicker();
    }

    interface Presenter extends BasePresenter {
        void filterMovies(String date);
        void handleOnMovieClick(int movieId);
        void handleFilterClick();
    }
}
