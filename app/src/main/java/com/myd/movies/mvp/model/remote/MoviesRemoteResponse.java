package com.myd.movies.mvp.model.remote;

import com.myd.movies.mvp.model.Local.Movie;

import java.util.List;

/**
 * Created by MYD on 4/11/18.
 *
 */

public class MoviesRemoteResponse {
    private int page;
    private int total_results;
    private int total_pages;
    private List<Movie> results;

    public MoviesRemoteResponse() {
    }

    public MoviesRemoteResponse(int page, int total_results, int total_pages, List<Movie> results) {
        this.page = page;
        this.total_results = total_results;
        this.total_pages = total_pages;
        this.results = results;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "MoviesRemoteResponse{" +
                "page=" + page +
                ", total_results=" + total_results +
                ", total_pages=" + total_pages +
                ", results=" + results +
                '}';
    }
}
