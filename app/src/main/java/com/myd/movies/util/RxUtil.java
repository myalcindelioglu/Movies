package com.myd.movies.util;

import io.reactivex.MaybeTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by MYD on 4/12/18.
 *
 */

public class RxUtil {
    public static <T> MaybeTransformer<T, T> applyMaybeSchedulers() {
        return observable -> observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
