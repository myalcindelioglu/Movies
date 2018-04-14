package com.myd.movies.mvp.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.myd.movies.BuildConfig;
import com.myd.movies.R;
import com.myd.movies.common.data.remote.response.MoviesRemoteResponse;
import com.myd.movies.mvp.MovieListContract;
import com.myd.movies.mvp.model.Local.Movies;
import com.myd.movies.mvp.model.remote.MoviesRemoteDataSource;
import com.myd.movies.util.RxUtil;
import com.myd.movies.util.TmdbServiceHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.subjects.PublishSubject;

public class MovieListFragment extends Fragment implements MovieListContract.View {

    private static final String TAG = "MovieListFragment";

    private MoviesRemoteDataSource moviesRemoteDataSource;

    private MoviesAdapter moviesAdapter;

    private int totalPages = 0;
    private String filterDate = "";

    private final PublishSubject<Integer> movieIdPublisher = PublishSubject.create();

    public MovieListFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        moviesRemoteDataSource = new MoviesRemoteDataSource(TmdbServiceHelper.getService());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.fragment_movie_list_rcv);
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addOnScrollListener(new InfiniteScrollListener(linearLayoutManager) {

            @Override
            public void onLoadMore(int nextPage) {
                if (nextPage <= totalPages) {
                    if (filterDate.isEmpty()) loadMovies(nextPage);
                    else filterMovies(filterDate, nextPage);
                }
            }
        });

        moviesAdapter = new MoviesAdapter(new ArrayList<>());
        recyclerView.setAdapter(moviesAdapter);

        loadMovies(1);

        return view;
    }

    @Override
    public void showProgress(boolean isLoadMore) {

    }

    @Override
    public void showData(List<Movies> movies) {

    }

    private void loadMovies(int page) {
        Maybe<MoviesRemoteResponse> responseMaybe =
                moviesRemoteDataSource.discoverMovies(page).compose(RxUtil.applyMaybeSchedulers());
        responseMaybe.subscribe(resp -> {
                    moviesAdapter.movies.addAll(resp.getResults());
                    totalPages = resp.getTotal_pages();
                    moviesAdapter.notifyDataSetChanged();
                }, e -> Log.e(TAG, "discoverMovies has an error", e)
        );
    }

    public void filterMovies(String date, int page) {
        filterDate = date;
        Maybe<MoviesRemoteResponse> responseMaybe = moviesRemoteDataSource.filterMovies(date, page).compose(RxUtil.applyMaybeSchedulers());
        responseMaybe.subscribe(resp -> {
                    if (page == 1) {
                        moviesAdapter.movies = resp.getResults();
                    } else {
                        moviesAdapter.movies.addAll(resp.getResults());
                    }
                    totalPages = resp.getTotal_pages();
                    moviesAdapter.notifyDataSetChanged();
                }, e -> Log.e(TAG, "discoverMovies has an error", e)
        );
    }

    public PublishSubject<Integer> getOnClicks() {
        return movieIdPublisher;
    }

    private class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

        private List<Movies> movies;

        MoviesAdapter(@NonNull List<Movies> movies) {
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
            if (posterPath != null && !posterPath.isEmpty()) {
                Picasso.with(getContext())
                        .load(BuildConfig.TMDB_SECURE_IMAGE_URL + "w500" + posterPath)
                        .into(holder.poster);
            }
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
