package com.myd.movies.mvp.model.Local;

import java.util.List;

/**
 * Created by MYD on 4/11/18.
 *
 */

public class Movies {
    private int id;
    private String title;
    private String poster_path;
    private String release_date;

    public Movies() {
    }

    public Movies(int id, String title, String poster_path, String release_date) {
        this.id = id;
        this.title = title;
        this.poster_path = poster_path;
        this.release_date = release_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    @Override
    public String toString() {
        return "Movies{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", poster_path='" + poster_path + '\'' +
                ", release_date='" + release_date + '\'' +
                '}';
    }
}
