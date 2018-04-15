package com.myd.movies.util;

import com.myd.movies.common.data.remote.response.MoviesRemoteResponse;
import com.myd.movies.mvp.model.Local.Movie;
import com.myd.movies.mvp.model.Local.MovieDetails;

import java.util.Collections;

/**
 * Created by MYD on 4/15/18.
 *
 */

public class TestUtil {
    public static MovieDetails createMovieDetails(int movieId) {
        MovieDetails.Genre genre = new MovieDetails.Genre(1, "drama");
        return new MovieDetails(movieId,
                "Batman Returns",
                "/poster.jpg",
                "backdrop.jpg",
                "2018-2-13",
                "en",
                8.9,
                1000,
                "overview text",
                Collections.singletonList(genre));

    }

    public static Movie createMovie (String releaseDate) {

        return new Movie(1, "Batman",
                "/eLQRLiu4jf4cLPzrp66M4lyDLQe.jpg",
                releaseDate);
    }

    public static MoviesRemoteResponse createMoviesRemoteResponse (Movie movie) {
        return new MoviesRemoteResponse(1, 1,
                        1, Collections.singletonList(movie));
    }
}
