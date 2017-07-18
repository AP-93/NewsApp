package com.example.ante.newsapp;

/**
 * Created by Ante on 18/07/2017.
 */

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Loads a list of news by using an AsyncTask to perform the
 * network request to the given URL.
 */
public class NewsLoader extends AsyncTaskLoader<ArrayList<News>> {

    /**
     * Tag for log messages
     */
    private static final String LOG_TAG = NewsLoader.class.getName();

    /**
     * Query URL
     */
    private String mUrl;

    /**
     * Constructs a new {@link NewsLoader}.
     *
     * @param context of the activity
     * @param url     to load data from
     */
    public NewsLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public ArrayList<News> loadInBackground() {

        String jsonResponse = "";

        if (mUrl == null) {
            return null;
        }
        //creates URL object from string
        URL url = createUrl(mUrl);

        //call makeHttpRequest method from Utils.class and return json as string
        try {
            jsonResponse = Utils.makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        if (jsonResponse == null) {
            return null;
        }
        //call getNews method from Utils.class to parse string jsonResponse
        ArrayList<News> news = Utils.getNews(jsonResponse);
        return news;

    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }
}