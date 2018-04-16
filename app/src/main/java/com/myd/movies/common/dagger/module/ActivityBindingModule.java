package com.myd.movies.common.dagger.module;

import com.myd.movies.common.dagger.annotations.ActivityScoped;
import com.myd.movies.mvp.view.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {
    @ActivityScoped
    @ContributesAndroidInjector(modules = MainActivityModule.class)
    abstract MainActivity mainActivity();
}
