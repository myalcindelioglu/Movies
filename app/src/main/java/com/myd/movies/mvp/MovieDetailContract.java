package com.myd.movies.mvp;

import com.myd.movies.common.base.BasePresenter;
import com.myd.movies.common.base.BaseView;
import com.myd.movies.mvp.model.Local.MovieDetails;

/**
 * Created by MYD on 4/15/18.
 *
 */

public interface MovieDetailContract {
    interface View extends BaseView {
        void showProgress();
        void loadViews(MovieDetails movieDetails);
        void showError();
    }

    interface Presenter extends BasePresenter<View> {
        void getDetails(int movieId);
    }
}
