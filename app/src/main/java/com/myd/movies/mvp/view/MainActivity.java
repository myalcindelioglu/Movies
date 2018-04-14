package com.myd.movies.mvp.view;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.myd.movies.mvp.model.Movies;
import com.myd.movies.util.DateUtil;

import java.util.Calendar;

import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {

    private MenuItem filterMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(toolbar);
        subscribeMovieListClicks();
    }

    private void subscribeMovieListClicks() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment);
        if (fragment instanceof MovieListFragment) {
            MovieListFragment movieListFragment = (MovieListFragment) fragment;
            Disposable disposable = movieListFragment.getOnClicks().subscribe(
                    movieId -> {
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
            );
        }
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
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
        if (filterMenu != null) filterMenu.setVisible(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_filter) {
            DialogFragment newFragment = new DatePickerFragment();
            newFragment.show(getSupportFragmentManager(), "datePicker");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

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
            if (getActivity() != null) {
                Fragment fragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment);
                if (fragment instanceof MovieListFragment) {
                    MovieListFragment listFragment = (MovieListFragment)fragment;
                    listFragment.filterMovies(DateUtil.intToString(year, month, day), 1);
                }
            }
        }
    }
}
