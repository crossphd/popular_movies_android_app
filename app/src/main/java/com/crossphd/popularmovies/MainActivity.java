package com.crossphd.popularmovies;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
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

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Movie>> {

//    video json response sample url: https://api.themoviedb.org/3/movie/499772/videos?api_key=3636e8f94c49efca06551bbd383c9484

    public static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static String BUILT_URL ="https://api.themoviedb.org/3/movie/popular?api_key=3636e8f94c49efca06551bbd383c9484";
    private static String POPULAR_URL = "https://api.themoviedb.org/3/discover/movie?api_key=3636e8f94c49efca06551bbd383c9484&language=en-US&region=US&sort_by=popularity.desc&include_adult=false&page=1";
    private static String RATING_URL = "https://api.themoviedb.org/3/discover/movie?api_key=3636e8f94c49efca06551bbd383c9484&language=en-US&region=US&sort_by=vote_average.desc&include_adult=false&page=1";
    private MovieAdapter adapter;
    private TextView mEmptyStateTextView;
    private static final int ARTICLE_LOADER_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    private void launchDetailActivity(Movie m) {
        Intent intent = new Intent(MainActivity.this, MovieDetail.class);
        intent.putExtra("movie", m);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void update() {
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.restartLoader(ARTICLE_LOADER_ID, null, this);
//        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public Loader<List<Movie>> onCreateLoader(int id, Bundle args) {
        Log.v(LOG_TAG, "onCreateLoader called");
        return new MovieLoader(this, POPULAR_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> movies) {
        adapter.clear();
        Log.v(LOG_TAG, "OnLoadFinished called");
        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (movies != null && !movies.isEmpty()) {
            adapter.addAll(movies);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Movie>> loader) {
        adapter.clear();
    }
}
