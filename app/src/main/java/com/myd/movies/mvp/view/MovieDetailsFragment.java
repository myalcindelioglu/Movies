package com.myd.movies.mvp.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.myd.movies.BuildConfig;
import com.myd.movies.R;
import com.myd.movies.mvp.MovieDetailContract;
import com.myd.movies.mvp.model.Local.MovieDetails;
import com.myd.movies.mvp.model.remote.MovieDetailsDataSource;
import com.myd.movies.mvp.model.remote.MovieDetailsRemoteDataSource;
import com.myd.movies.mvp.presenter.MovieDetailPresenter;
import com.myd.movies.util.TmdbServiceHelper;
import com.squareup.picasso.Picasso;

import java.util.Locale;

/**
 * Created by MYD on 4/14/18.
 *
 */

public class MovieDetailsFragment extends Fragment implements MovieDetailContract.View {

    public static final String MOVIE_ID_KEY = "MOVIE_ID";

    private MovieDetailPresenter presenter;

    private ImageView posterImage;
    private ImageView backdropImage;
    private TextView titleText;
    private TextView languageText;
    private TextView dateText;
    private TextView genresText;
    private TextView rateText;
    private TextView rateCountText;
    private TextView overviewText;
    private ProgressBar progressBar;

    private View rootView;

    private int movieId;

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
        MovieDetailsDataSource dataSource = new MovieDetailsRemoteDataSource(TmdbServiceHelper.getService());
        presenter = new MovieDetailPresenter(dataSource, this);
        presenter.subscribe();
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
        progressBar = view.findViewById(R.id.fragment_movie_details_load_pb);
        rootView = view.findViewById(R.id.fragment_movie_details_cl);

        presenter.getDetails(movieId);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.unSubscribe();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError() {
        progressBar.setVisibility(View.GONE);
        Snackbar.make(rootView, R.string.network_error_text, BaseTransientBottomBar.LENGTH_LONG).show();
    }

    @Override
    public void loadViews(MovieDetails movieDetails) {
        progressBar.setVisibility(View.GONE);
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
