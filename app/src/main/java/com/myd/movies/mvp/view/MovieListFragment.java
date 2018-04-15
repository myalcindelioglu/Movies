package com.myd.movies.mvp.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.myd.movies.BuildConfig;
import com.myd.movies.R;
import com.myd.movies.mvp.MovieListContract;
import com.myd.movies.mvp.model.Local.Movie;
import com.myd.movies.mvp.model.remote.MoviesRemoteDataSource;
import com.myd.movies.mvp.presenter.MovieListPresenter;
import com.myd.movies.util.TmdbServiceHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.subjects.PublishSubject;

public class MovieListFragment extends Fragment implements MovieListContract.View {

    private static final String TAG = "MovieListFragment";

    private MovieListPresenter presenter;

    private MoviesAdapter moviesAdapter;

    private final PublishSubject<Integer> movieIdPublisher = PublishSubject.create();
    private View loadMoreProgress;
    private View loadProgress;
    private RecyclerView recyclerView;
    private InfiniteScrollListenerImpl infiniteScrollListener;

    public MovieListFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MoviesRemoteDataSource moviesRemoteDataSource = new MoviesRemoteDataSource(TmdbServiceHelper.getService());
        presenter = new MovieListPresenter(moviesRemoteDataSource, this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);

        loadMoreProgress = view.findViewById(R.id.fragment_movie_list_load_more_pb);
        loadProgress = view.findViewById(R.id.fragment_movie_list_load_pb);
        recyclerView = view.findViewById(R.id.fragment_movie_list_rcv);
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        infiniteScrollListener = new InfiniteScrollListenerImpl(linearLayoutManager);
        recyclerView.addOnScrollListener(infiniteScrollListener);

        moviesAdapter = new MoviesAdapter(new ArrayList<>());
        recyclerView.setAdapter(moviesAdapter);

        presenter.discoverMovies(1, false);

        return view;
    }

    @Override
    public void showProgress(boolean isLoadMore) {
        if (isLoadMore) {
            loadMoreProgress.setVisibility(View.VISIBLE);
        } else {
            loadProgress.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showData(List<Movie> movies, boolean isLoadMore) {
        hideProgress(isLoadMore);
        if (!isLoadMore) {
            moviesAdapter.movies.clear();
        }
        moviesAdapter.movies.addAll(movies);
        moviesAdapter.notifyDataSetChanged();

        recyclerView.scrollToPosition(0);

    }

    @Override
    public void showError(boolean isLoadMore) {
        infiniteScrollListener.onLoadError();
        hideProgress(isLoadMore);
        Snackbar.make(recyclerView, R.string.network_error_text, BaseTransientBottomBar.LENGTH_LONG).show();
    }

    private void hideProgress(boolean isLoadMore) {
        if (isLoadMore) {
            loadMoreProgress.setVisibility(View.GONE);
        } else {
            loadProgress.setVisibility(View.GONE);
        }
    }

    public void filterMovies(String date, int page) {
        infiniteScrollListener.reset();
        presenter.filterMovies(date, page, false);
    }

    public PublishSubject<Integer> getOnClicks() {
        return movieIdPublisher;
    }

    public class InfiniteScrollListenerImpl extends InfiniteScrollListener {

        InfiniteScrollListenerImpl(RecyclerView.LayoutManager layoutManager) {
            super(layoutManager);
        }

        @Override
        public void onLoadMore(int nextPage) {
            presenter.discoverMovies(nextPage, true);
        }
    }

    private class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

        private List<Movie> movies;

        MoviesAdapter(@NonNull List<Movie> movies) {
            this.movies = movies;
        }

        @NonNull
        @Override
        public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_movies_list_item, parent, false);
            ImageView poster = view.findViewById(R.id.fragment_movie_list_item_movies_poster_iv);
            TextView title = view.findViewById(R.id.fragment_movie_list_item_movies_title_txv);
            TextView releaseDate = view.findViewById(R.id.fragment_movie_list_item_movies_release_date_txv);
            return new MoviesViewHolder(view, poster, title, releaseDate);
        }

        @Override
        public void onBindViewHolder(@NonNull MoviesViewHolder holder, int position) {

            String posterPath = movies.get(position).getPoster_path();
            Picasso.with(getContext())
                    .load(BuildConfig.TMDB_SECURE_IMAGE_URL + "w500" + posterPath)
                    .into(holder.poster);
            holder.title.setText(movies.get(position).getTitle());
            holder.releaseDate.setText(movies.get(position).getRelease_date());

            final Integer movieId = movies.get(position).getId();
            holder.itemView.setOnClickListener(v -> movieIdPublisher.onNext(movieId));

        }

        @Override
        public int getItemCount() {
            return movies.size();
        }

        class MoviesViewHolder extends RecyclerView.ViewHolder {

            private ImageView poster;
            private TextView title;
            private TextView releaseDate;

            MoviesViewHolder(View itemView,
                             ImageView poster,
                             TextView title,
                             TextView releaseDate) {
                super(itemView);
                this.poster = poster;
                this.title = title;
                this.releaseDate = releaseDate;
            }
        }
    }
}
