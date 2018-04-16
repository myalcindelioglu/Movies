package com.myd.movies.common.dagger.module;


import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.myd.movies.BuildConfig;
import com.myd.movies.common.data.remote.TmdbService;
import com.myd.movies.mvp.model.remote.FakeDetailsDataSource;
import com.myd.movies.mvp.model.remote.FakeMovieDataSource;
import com.myd.movies.mvp.model.remote.MovieDetailsDataSource;
import com.myd.movies.mvp.model.remote.MoviesDataSource;
import com.myd.movies.util.TmdbApiInterceptor;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class TmdbSourceModule {

    @Singleton
    @Provides
    MoviesDataSource provideMoviesDataSource() {
        return new FakeMovieDataSource();
    }

    @Singleton
    @Provides
    MovieDetailsDataSource provideMoviesDetailDataSource() {
        return new FakeDetailsDataSource();
    }

    @Provides
    @Singleton
    TmdbService provideServerInterface(Gson gson, OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.TMDB_SECURE_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();

        return retrofit.create(TmdbService.class);
    }


    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        }

        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(10, TimeUnit.SECONDS);

        // build okhttp client
        return builder
                .addInterceptor(new TmdbApiInterceptor())
                .build();
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                .create();
    }

    @Provides
    @Singleton
    TmdbApiInterceptor provideApiKeyRequestInterceptor() {
        return new TmdbApiInterceptor();
    }

}
