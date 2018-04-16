package com.myd.movies.mvp.model.remote;

import android.support.annotation.VisibleForTesting;

import com.myd.movies.mvp.model.Local.MovieDetails;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by MYD on 4/12/18.
 *
 */

public class FakeDetailsDataSource implements MovieDetailsDataSource {
    private static final MovieDetails SERVICE_DATA = new MovieDetails();

    @Inject
    public FakeDetailsDataSource() {
    }

    @Override
    public Single<MovieDetails> getDetails(int movieId) {
        return Single.just(SERVICE_DATA);
    }

    @VisibleForTesting
    public void setServiceData(MovieDetails details) {
        SERVICE_DATA.setId(details.getId());
        SERVICE_DATA.setTitle(details.getTitle());
        SERVICE_DATA.setPoster_path(details.getPoster_path());
        SERVICE_DATA.setBackdrop_path(details.getBackdrop_path());
        SERVICE_DATA.setRelease_date(details.getRelease_date());
        SERVICE_DATA.setOriginal_language(details.getOriginal_language());
        SERVICE_DATA.setVote_average(details.getVote_average());
        SERVICE_DATA.setVote_count(details.getVote_count());
        SERVICE_DATA.setOverview(details.getOverview());
        SERVICE_DATA.setGenres(details.getGenres());
    }

}
