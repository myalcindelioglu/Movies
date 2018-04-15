package com.myd.movies.mvp.view;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * Created by MYD on 4/12/18.
 *
 */

public abstract class InfiniteScrollListener extends RecyclerView.OnScrollListener {

    private int threshold;
    private int currentPage;
    private int lastTotalItemCount = 0;
    private int lastPage;
    private int initialPage = 1;

    private boolean loading = true;

    private RecyclerView.LayoutManager layoutManager;

    InfiniteScrollListener(RecyclerView.LayoutManager layoutManager) {
        this(layoutManager, 5, 1);

    }

    private InfiniteScrollListener(RecyclerView.LayoutManager layoutManager,
                                   int threshold,
                                   int currentPage) {
        this.layoutManager = layoutManager;
        this.threshold = threshold;
        this.lastPage = currentPage;
        this.currentPage = currentPage;
        this.initialPage = currentPage;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        int lastVisibleItemPosition = 0;
        int totalItemCount = layoutManager.getItemCount();

        if (layoutManager instanceof StaggeredGridLayoutManager) {
            int[] lastVisibleItemPositions = ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(null);
            lastVisibleItemPosition = getLastVisibleItem(lastVisibleItemPositions);
        } else if (layoutManager instanceof GridLayoutManager) {
            lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
        } else if (layoutManager instanceof LinearLayoutManager) {
            lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
        }

        if (totalItemCount < lastTotalItemCount) {
            this.currentPage = this.initialPage;
            this.lastTotalItemCount = totalItemCount;
            if (totalItemCount == 0) {
                this.loading = true;
            }
        }

        if (loading && (totalItemCount > lastTotalItemCount)) {
            loading = false;
            lastTotalItemCount = totalItemCount;
        }

        if (!loading && (lastVisibleItemPosition + threshold) > totalItemCount) {
            lastPage = currentPage++;
            onLoadMore(currentPage);
            loading = true;
        }

    }

    private int getLastVisibleItem(int[] lastVisibleItemPositions) {
        int maxSize = 0;
        for (int i = 0; i < lastVisibleItemPositions.length; i++) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i];
            }
            else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i];
            }
        }
        return maxSize;
    }

    void onLoadError() {
        currentPage = lastPage;
        loading = false;
    }

    public abstract void onLoadMore(int nextPage);
}
