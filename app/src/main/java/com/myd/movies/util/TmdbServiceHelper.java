package com.myd.movies.util;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.myd.movies.BuildConfig;
import com.myd.movies.common.data.remote.TmdbService;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by MYD on 4/11/18.
 *
 */

public class TmdbServiceHelper {
    private static TmdbService service;

    private TmdbServiceHelper() {
    }

    public static TmdbService getService() {
        if (service == null) {
            service = buildService();
        }

        return service;
    }


    private static TmdbService buildService() {

        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                .create();

        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        }

        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(10, TimeUnit.SECONDS);

        // build okhttp client
        OkHttpClient okHttpClient = builder
                .addInterceptor(new TmdbApiInterceptor())
                .build();

        // build retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.TMDB_SECURE_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();

        return retrofit.create(TmdbService.class);

    }




}
