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

import com.myd.movies.R;
import com.myd.movies.common.data.remote.response.MoviesRemoteResponse;
import com.myd.movies.mvp.model.Movies;
import com.myd.movies.mvp.model.remote.MoviesRemoteDataSource;
import com.myd.movies.util.RxUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Maybe;

public class MovieListFragment extends Fragment {

    private static final String TAG = "MovieListFragment";

    private MoviesRemoteDataSource remoteDataSource;
    private MoviesAdapter moviesAdapter;

    public MovieListFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        remoteDataSource = new MoviesRemoteDataSource();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.fragment_movie_list_rcv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemViewCacheSize(3);

        moviesAdapter = new MoviesAdapter(new ArrayList<>());
        recyclerView.setAdapter(moviesAdapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Maybe<MoviesRemoteResponse> responseMaybe = remoteDataSource.discoverMovies(1).compose(RxUtil.applyMaybeSchedulers());
        responseMaybe.subscribe(resp -> {
            moviesAdapter.movies = resp.getResults();
            moviesAdapter.notifyDataSetChanged();
        }, e -> {
            Log.e(TAG, "discoverMovies has an error", e);
                }
        );

    }

    private class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

        List<Movies> movies;

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
                        .load("https://image.tmdb.org/t/p/w500" + posterPath)
                        .into(holder.poster);
            }
            holder.title.setText(movies.get(position).getTitle());
            holder.releaseDate.setText(movies.get(position).getRelease_date());
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
