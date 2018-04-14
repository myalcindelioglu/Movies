package com.myd.movies.mvp.view;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;

import com.myd.movies.R;
import com.myd.movies.mvp.presenter.MainPresenter;
import com.myd.movies.util.DateUtil;
import com.myd.movies.util.RxUtil;

import java.util.Calendar;

import io.reactivex.disposables.CompositeDisposable;

public class MainActivity extends AppCompatActivity implements MainContract.View {
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private MainPresenter presenter;
    private MenuItem filterMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(toolbar);
        presenter = new MainPresenter(this);
        presenter.subscribe();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        filterMenu = menu.findItem(R.id.action_filter);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
       presenter.handleBackPress();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_filter) {
            presenter.handleFilterClick();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxUtil.unSubscribe(compositeDisposable);
        presenter.unSubscribe();
    }

    @Override
    public void subscribeMovieOnClick() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment);
        if (fragment instanceof MovieListFragment) {
            MovieListFragment movieListFragment = (MovieListFragment) fragment;
            compositeDisposable.add(movieListFragment.getOnClicks().subscribe(
                    movieId -> presenter.handleOnMovieClick(movieId)
                    )
            );
        }
    }

    @Override
    public void showDetail(int movieId) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        if (filterMenu != null) filterMenu.setVisible(false);
        MovieDetailsFragment detailsFragment = MovieDetailsFragment.newInstance(movieId);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment, detailsFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void showDatePicker() {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.setPresenter(presenter);
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    @Override
    public void showFilteredMovies(String date) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment);
        if (fragment instanceof MovieListFragment) {
            MovieListFragment listFragment = (MovieListFragment) fragment;
            listFragment.filterMovies(date, 1);
        }
    }

    @Override
    public void showMovieList() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
        if (filterMenu != null) filterMenu.setVisible(true);
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        MainPresenter presenter;

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            presenter.filterMovies(DateUtil.intToString(year, month, day));
        }

        public void setPresenter(MainPresenter presenter) {
            this.presenter = presenter;
        }
    }
}
