package com.myd.movies.common.base;

/**
 * Created by MYD on 4/12/18.
 *
 */

public interface BasePresenter<T> {
    void subscribe(T view);
    void unSubscribe();
}
