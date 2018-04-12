package com.myd.movies.mvp.view;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.myd.movies.R;
import com.myd.movies.common.data.remote.response.MoviesResponseBean;
import com.myd.movies.util.TmdbServiceHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.v7.widget.StaggeredGridLayoutManager.VERTICAL;

public class MovieListFragment extends Fragment {

    public MovieListFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.fragment_movie_list_rcv);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);

        Call<MoviesResponseBean> call =
                TmdbServiceHelper.getService().movieDiscoverByReleaseDateDesc();
        call.enqueue(new Callback<MoviesResponseBean>() {
            @Override
            public void onResponse(@NonNull Call<MoviesResponseBean> call, @NonNull Response<MoviesResponseBean> response) {

            }

            @Override
            public void onFailure(@NonNull Call<MoviesResponseBean> call,
                                  @NonNull Throwable t) {

            }
        });

        String[] strings = new String[]{
                "aaaaasadsadsafdfgfgfgwerqrqerqereqreqrqreqr",
                "aaaaasadsadsafdfgfgfgwerqrqerqereqreqrqreqr",
                "aaaaasadsadsafdfgfgfgwerqrqerqereqreqrqreqr",
                "aaaaasadsadsafdfgfgfgwerqrqerqereqreqrqreqr",
                "aaaaasadsadsafdfgfgfgwerqrqerqereqreqrqreqr",
                "aaaaasadsadsafdfgfgfgwerqrqerqereqreqrqreqr",
                "aaaaasadsadsafdfgfgfgwerqrqerqereqreqrqreqr",
                "aaaaasadsadsafdfgfgfgwerqrqerqereqreqrqreqr",
                "aaaaasadsadsafdfgfgfgwerqrqerqereqreqrqreqr",
                "aaaaasadsadsafdfgfgfgwerqrqerqereqreqrqreqr",
                "aaaaasadsadsafdfgfgfgwerqrqerqereqreqrqreqr",
                "aaaaasadsadsafdfgfgfgwerqrqerqereqreqrqreqr",
                "aaaaasadsadsafdfgfgfgwerqrqerqereqreqrqreqr",
                "aaaaasadsadsafdfgfgfgwerqrqerqereqreqrqreqr",
                "aaaaasadsadsafdfgfgfgwerqrqerqereqreqrqreqr",
        };
        MoviesAdapter moviesAdapter = new MoviesAdapter(strings);
        recyclerView.setAdapter(moviesAdapter);

        return view;
    }

    private class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

        String[] movies;

        MoviesAdapter(@NonNull String[] movies) {
            this.movies = movies;
        }

        @NonNull
        @Override
        public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_movies_list_item, parent, false);
            TextView textView = view.findViewById(R.id.fragment_movie_list_item_txt);
            return new MoviesViewHolder(view, textView);
        }

        @Override
        public void onBindViewHolder(@NonNull MoviesViewHolder holder, int position) {
            holder.textView.setText(movies[position]);
        }

        @Override
        public int getItemCount() {
            return movies.length;
        }

        class MoviesViewHolder extends RecyclerView.ViewHolder {

            private TextView textView;

            MoviesViewHolder(View itemView, TextView textView) {
                super(itemView);
                this.textView = textView;
            }
        }
    }
}
