package com.example.ante.newsapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Ante on 18/07/2017.
 */

public class Utils {

    private static final String LOG_TAG = "";

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    public static String makeHttpRequest(URL url) throws IOException {

        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            //setting up HTTP request
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            //establish connection
            urlConnection.connect();

            // If the request was successful (response code 200),
            // read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    //parse string json and return ArrayList<Book>
    public static ArrayList<News> getNews(String json) {

        ArrayList<News> newsLst = new ArrayList<>();

        try {
            JSONObject jsonResponse = new JSONObject(json);

            // "response" is first object containing all other objects and arrays
            JSONObject responseObject = jsonResponse.getJSONObject("response");

            //"results" is array which contains other objects
            JSONArray jsonArray = responseObject.getJSONArray("results");

            //run trough each "results" object and get title,url,date and category
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject newsJsonObject = jsonArray.getJSONObject(i);

                String newsTitle = newsJsonObject.getString("webTitle");
                String newsUrl = newsJsonObject.getString("webUrl");
                String newsDate = newsJsonObject.getString("webPublicationDate");
                String newsCategory = newsJsonObject.getString("sectionName");

                //convert date from json date to more readable date
                newsDate = convertDate(newsDate);

                //add strings title,url,date and category to created News object
                News newsObj = new News(newsTitle, newsUrl, newsDate, newsCategory);
                //add news object to ArrayList<News> newsLst
                newsLst.add(newsObj);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return newsLst;
    }

    private static String convertDate(String newsDate) {

        //SimpleDateFormat is a concrete class for formatting and parsing dates.It allows for formatting (date -> text), parsing (text -> date), and normalization.
        SimpleDateFormat jsonFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

        try {
            //parse string from json to date
            Date parsedJsonDate = jsonFormatter.parse(newsDate);
            //create new SimpleDateFormat and format Date into day month, year, hour:minutes string
            SimpleDateFormat finalDateFormatter = new SimpleDateFormat("d MMM, yyy, HH:mm");
            return finalDateFormatter.format(parsedJsonDate);

        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }
}