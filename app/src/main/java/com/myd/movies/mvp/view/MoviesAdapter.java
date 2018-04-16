package com.myd.movies.mvp.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.myd.movies.BuildConfig;
import com.myd.movies.R;
import com.myd.movies.mvp.model.Local.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.subjects.PublishSubject;

/**
 * Created by MYD on 4/16/18.
 *
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

    private List<Movie> movies;
    private PublishSubject<Integer> movieIdOnClickPublisher;

    @Inject
    public MoviesAdapter(@NonNull List<Movie> movies, PublishSubject<Integer> movieIdOnClickPublisher) {
        this.movies = movies;
        this.movieIdOnClickPublisher = movieIdOnClickPublisher;
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
        Picasso.with(holder.poster.getContext())
                .load(BuildConfig.TMDB_SECURE_IMAGE_URL + "w500" + posterPath)
                .into(holder.poster);
        holder.title.setText(movies.get(position).getTitle());
        holder.releaseDate.setText(movies.get(position).getRelease_date());

        final Integer movieId = movies.get(position).getId();
        holder.itemView.setOnClickListener(v -> movieIdOnClickPublisher.onNext(movieId));

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public List<Movie> getMovies() {
        return movies;
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
