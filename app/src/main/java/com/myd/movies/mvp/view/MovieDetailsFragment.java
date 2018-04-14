package com.myd.movies.mvp.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.myd.movies.BuildConfig;
import com.myd.movies.R;
import com.myd.movies.mvp.model.MovieDetails;
import com.myd.movies.mvp.model.MovieDetailsDataSource;
import com.myd.movies.mvp.model.remote.MovieDetailsRemoteDataSource;
import com.myd.movies.util.RxUtil;
import com.squareup.picasso.Picasso;

import java.util.Locale;

import io.reactivex.Single;

/**
 * Created by MYD on 4/14/18.
 *
 */

public class MovieDetailsFragment extends Fragment {

    private static final String TAG = "MovieDetailsFragment";
    public static final String MOVIE_ID_KEY = "MOVIE_ID";

    private int movieId;

    private MovieDetailsDataSource movieDetailsDataSource;
    private ImageView posterImage;
    private ImageView backdropImage;
    private TextView titleText;
    private TextView languageText;
    private TextView dateText;
    private TextView genresText;
    private TextView rateText;
    private TextView rateCountText;
    private TextView overviewText;

    public MovieDetailsFragment() {
    }

    public static MovieDetailsFragment newInstance(int movieId) {
        MovieDetailsFragment movieDetailsFragment = new MovieDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(MOVIE_ID_KEY, movieId);
        movieDetailsFragment.setArguments(args);
        return movieDetailsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            movieId = args.getInt(MOVIE_ID_KEY, -1);
        }
        movieDetailsDataSource = new MovieDetailsRemoteDataSource();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_movie_details, container, false);
        posterImage = view.findViewById(R.id.fragment_movie_details_poster_iv);
        backdropImage = view.findViewById(R.id.fragment_movie_details_backdrop_iv);
        titleText = view.findViewById(R.id.fragment_movie_details_title_txv);
        languageText = view.findViewById(R.id.fragment_movie_details_lang_txv);
        dateText = view.findViewById(R.id.fragment_movie_details_date_txv);
        genresText = view.findViewById(R.id.fragment_movie_details_genres_txv);
        rateText = view.findViewById(R.id.fragment_movie_details_rate_txv);
        rateCountText = view.findViewById(R.id.fragment_movie_details_count_txv);
        overviewText = view.findViewById(R.id.fragment_movie_details_overview_txv);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Single<MovieDetails> movieDetailsSingle =
                movieDetailsDataSource.getDetails(movieId).compose(RxUtil.applySingleSchedulers());
        movieDetailsSingle.subscribe(this::loadViews,
                e -> Log.e(TAG, "discoverMovies has an error", e)
        );
    }

    private void loadViews(MovieDetails movieDetails) {
        String posterPath = movieDetails.getPoster_path();
        if (posterPath != null && !posterPath.isEmpty()) {
            Picasso.with(getContext())
                    .load(BuildConfig.TMDB_SECURE_IMAGE_URL + "w780" + posterPath)
                    .into(posterImage
                    );

        }

        String backdropPath = movieDetails.getPoster_path();
        if (backdropPath != null && !backdropPath.isEmpty()) {
            Picasso.with(getContext())
                    .load(BuildConfig.TMDB_SECURE_IMAGE_URL + "w780" + backdropPath)
                    .into(backdropImage
                    );

        }

        titleText.setText(movieDetails.getTitle());
        languageText.setText(new Locale(movieDetails.getOriginal_language()).getDisplayName());
        dateText.setText(movieDetails.getRelease_date());
        genresText.setText(TextUtils.join(",",  movieDetails.getGenres()));
        rateText.setText(getString(R.string.movie_detail_rate, movieDetails.getVote_average()));
        rateCountText.setText(String.valueOf(movieDetails.getVote_count()));
        overviewText.setText(movieDetails.getOverview());
    }
}
