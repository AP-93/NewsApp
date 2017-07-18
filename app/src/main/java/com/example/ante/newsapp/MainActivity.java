package com.example.ante.newsapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<News>> {

    NewsAdapter newsAdapter;
    ListView newsListView;
    Button refreshBtn;
    ProgressBar progressBar;

    private static final int NEWS_LOADER_ID = 1;

    // querys newest 50 articles
    public static final String NEWS_API_BASE_QUERY = "http://content.guardianapis.com/search?&type=article&order-by=newest&page-size=50&api-key=test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newsListView = (ListView) findViewById(R.id.listView);
        refreshBtn = (Button) findViewById(R.id.refreshBtn);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        //news adapter initialization
        newsAdapter = new NewsAdapter(this, -1);
        newsListView.setAdapter(newsAdapter);

        // Get a reference to the LoaderManager, in order to interact with loaders.
        LoaderManager loaderManager = getLoaderManager();

        // Initialize the loader. Pass in the int ID constant defined above and pass in null for
        // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
        // because this activity implements the LoaderCallbacks interface).
        loaderManager.initLoader(NEWS_LOADER_ID, null, this);

        //display toast message if there is no Internet connection
        if (!isNetworkConnected())
            Toast.makeText(MainActivity.this, "Check internet connection and refresh", Toast.LENGTH_LONG).show();

        // Set new AdapterView.OnItemClickListener on list
        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            //Callback method to be invoked when an item in this AdapterView has been clicked
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                News newsObj = newsAdapter.getItem(position);
                String url = newsObj.getmUrl();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });

        //onclick listener for refresh button
        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //check for network connection and display progress bar during load
                if (isNetworkConnected()) {
                    getLoaderManager().restartLoader(NEWS_LOADER_ID, null, MainActivity.this);
                    progressBar.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(MainActivity.this, "Check internet connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //Check to make sure it is connected to a network:
    private boolean isNetworkConnected() {

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    @Override
    public Loader<ArrayList<News>> onCreateLoader(int id, Bundle args) {
        // Create a new loader for the given URL
        return new NewsLoader(this, NEWS_API_BASE_QUERY);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<News>> loader, ArrayList<News> data) {

        // Clear the adapter of previous news data
        newsAdapter.clear();

        // If there is a valid list of {@link News}, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (data != null && !data.isEmpty()) {

            newsAdapter.addAll(data);
        }
        //set progress bar invisible after loading data is done
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<News>> loader) {
        // Loader reset, so we can clear out our existing data.
        newsAdapter.clear();
    }
}
