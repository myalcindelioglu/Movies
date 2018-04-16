package com.myd.movies.common.dagger.component;

import android.app.Application;

import com.myd.movies.App;
import com.myd.movies.common.dagger.module.ActivityBindingModule;
import com.myd.movies.common.dagger.module.AppModule;
import com.myd.movies.common.dagger.module.TmdbSourceModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;


@Singleton
@Component(modules = {
        TmdbSourceModule.class,
        AppModule.class,
        ActivityBindingModule.class,
        AndroidSupportInjectionModule.class})
public interface AppComponent extends AndroidInjector<App> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        AppComponent.Builder application(Application application);

        AppComponent build();
    }
}
