package com.myd.movies.common.dagger.module;


import com.myd.movies.common.dagger.annotations.ActivityScoped;
import com.myd.movies.common.dagger.annotations.FragmentScoped;
import com.myd.movies.mvp.MainContract;
import com.myd.movies.mvp.MovieDetailContract;
import com.myd.movies.mvp.MovieListContract;
import com.myd.movies.mvp.presenter.MainPresenter;
import com.myd.movies.mvp.presenter.MovieDetailPresenter;
import com.myd.movies.mvp.presenter.MovieListPresenter;
import com.myd.movies.mvp.view.MovieDetailsFragment;
import com.myd.movies.mvp.view.MovieListFragment;
import com.myd.movies.mvp.view.MoviesAdapter;

import java.util.ArrayList;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;
import io.reactivex.subjects.PublishSubject;

@Module
abstract class MainActivityModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract MovieListFragment movieListFragment();

    @FragmentScoped
    @ContributesAndroidInjector
    abstract MovieDetailsFragment movieDetailsFragment();

    @ActivityScoped
    @Binds
    abstract MainContract.Presenter mainPresenter (MainPresenter presenter);

    @Provides
    @ActivityScoped
    static MoviesAdapter provideMoviesAdapter(PublishSubject<Integer> movieIdOnClickPublisher) {
        return new MoviesAdapter(new ArrayList<>(), movieIdOnClickPublisher);
    }

    @Provides
    @ActivityScoped
    static PublishSubject<Integer> provideMovieIdOnClickPublisher() {
        return PublishSubject.create();
    }

    @FragmentScoped
    @Binds
    abstract MovieListContract.Presenter movieListPresenter (MovieListPresenter presenter);

    @FragmentScoped
    @Binds
    abstract MovieDetailContract.Presenter movieDetailPresenter (MovieDetailPresenter presenter);
}
