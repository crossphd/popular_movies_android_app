package com.crossphd.popularmovies;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity
        extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<Movie>>,
        SharedPreferences.OnSharedPreferenceChangeListener {

    public static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static String BUILT_URL ="";
    String API_KEY = BuildConfig.MY_MOVIE_DB_API_KEY;
    private String POPULAR_URL = String.format("https://api.themoviedb.org/3/movie/popular?api_key=%s&language=en-US&region=US&include_adult=false&page=1", API_KEY);
    private String RATING_URL = String.format("https://api.themoviedb.org/3/movie/top_rated?api_key=%s&language=en-US&region=US&include_adult=false&page=1", API_KEY);
    private MovieAdapter adapter;
    private TextView mEmptyStateTextView;
    private static final int ARTICLE_LOADER_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUpSharedPreferences();

        setContentView(R.layout.activity_main);

        final GridView movieGridView = findViewById(R.id.movies_grid);
        mEmptyStateTextView = findViewById(R.id.empty_view);
        movieGridView.setEmptyView(mEmptyStateTextView);
        adapter = new MovieAdapter(MainActivity.this, new ArrayList<Movie>());
        movieGridView.setAdapter(adapter);

        movieGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Movie m = adapter.getItem(position);
                launchDetailActivity(m);
            }
        });


        //        check network connectivity before calling loaderManager
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connMgr.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(ARTICLE_LOADER_ID, null, this);
        } else {
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    private void setUpSharedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String sort = sharedPreferences.getString("sort_order", "1");
//        Log.v(LOG_TAG, "******************  shared preferences sort order = " + sort + " ********************");
//        Log.v(LOG_TAG, "******************  APIKEY = " + API_KEY + " ********************");
        if (sort.equals("1")) {
            BUILT_URL = POPULAR_URL;
        }
        else if (sort.equals("2")) {
            BUILT_URL = RATING_URL;
        }
//        else {
//            BUILT_URL = POPULAR_URL;
//        }
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

    }

    private void launchDetailActivity(Movie m) {
        Intent intent = new Intent(MainActivity.this, MovieDetail.class);
        intent.putExtra("movie", m);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent startSettingsActivity = new Intent(this, SettingsActivity.class);
            startActivity(startSettingsActivity);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void update() {
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.restartLoader(ARTICLE_LOADER_ID, null, this);
    }

    @Override
    public Loader<List<Movie>> onCreateLoader(int id, Bundle args) {
        Log.v(LOG_TAG, "onCreateLoader called");
        return new MovieLoader(this, BUILT_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> movies) {
        adapter.clear();
        Log.v(LOG_TAG, "OnLoadFinished called");
        if (movies != null && !movies.isEmpty()) {
            adapter.addAll(movies);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Movie>> loader) {
        adapter.clear();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.pref_sort_order_key))) {
            String sort = sharedPreferences.getString(key, null);
//            Log.v(LOG_TAG, "******************  shared preferences sort order = " + sort + " ********************");
            if (sort.equals("1")) {
                BUILT_URL = POPULAR_URL;
            }
            else if (sort.equals("2")) {
                BUILT_URL = RATING_URL;
            }
        }
        update();
    }
}
