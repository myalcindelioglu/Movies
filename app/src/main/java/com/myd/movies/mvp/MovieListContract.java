package com.myd.movies.mvp;

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
        void showData(List<Movies> movies);
    }

    interface Presenter extends BasePresenter {
        void loadMovies(int nextPage);
        void filterMovies(String filterDate, int nextPage);
    }
}
