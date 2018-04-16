package com.myd.movies.mvp.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.myd.movies.R;
import com.myd.movies.mvp.MovieListContract;
import com.myd.movies.mvp.model.Local.Movie;
import com.myd.movies.mvp.presenter.MovieListPresenter;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import io.reactivex.subjects.PublishSubject;

public class MovieListFragment extends DaggerFragment implements MovieListContract.View {

    @Inject
    public MovieListPresenter presenter;

    @Inject
    public PublishSubject<Integer> movieIdPublisher;

    @Inject
    public MoviesAdapter moviesAdapter;

    private View loadMoreProgress;
    private View loadProgress;
    private RecyclerView recyclerView;

    private InfiniteScrollListener infiniteScrollListener;

    public MovieListFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.subscribe(this);
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

        recyclerView.setAdapter(moviesAdapter);

        presenter.discoverMovies(1, false);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.unSubscribe();
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
            moviesAdapter.getMovies().clear();
        }
        moviesAdapter.getMovies().addAll(movies);
        moviesAdapter.notifyDataSetChanged();

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
        recyclerView.scrollToPosition(0);
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
}
