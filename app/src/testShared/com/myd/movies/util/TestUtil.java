package com.myd.movies.util;

import com.myd.movies.mvp.model.remote.MoviesRemoteResponse;
import com.myd.movies.mvp.model.Local.Movie;
import com.myd.movies.mvp.model.Local.MovieDetails;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by MYD on 4/15/18.
 *
 */

public class TestUtil {
    public static MovieDetails createMovieDetails(Movie movie) {
        MovieDetails.Genre genre = new MovieDetails.Genre(1, "drama");
        return new MovieDetails(movie.getId(),
                movie.getTitle(),
                movie.getPoster_path(),
                "/ducVIDxVa6BjlMl976XLmogBRkI.jpg",
                movie.getRelease_date(),
                "en",
                8.9,
                1000,
                "overview text",
                Collections.singletonList(genre));

    }

    public static Movie createMovie (int id, String releaseDate) {

        return new Movie(id, "Batman",
                "/kns75wPtBCISvnWddHFEo0DMuU0.jpg",
                releaseDate);
    }

    public static List<Movie> createMovies () {
        List<Movie> movies = new ArrayList<>();

        String date1 = "2018-2-27";
        String date2 = "2018-2-26";

        for (int i = 1; i < 11; i++) {
            movies.add(createMovie(i, date1));
        }

        for (int i = 11; i < 21; i++) {
            movies.add(createMovie(i, date2));
        }

        return movies;
    }

    public static MoviesRemoteResponse createMoviesRemoteResponse (int page, int totalResults, int totalPages, List<Movie> movies) {
        return new MoviesRemoteResponse(page, totalResults,
                totalPages, movies);
    }

    public static MoviesRemoteResponse createMoviesRemoteResponse (Movie movie) {
        return createMoviesRemoteResponse(1, 1,
                        1, Collections.singletonList(movie));
    }
}
