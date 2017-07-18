package com.example.ante.newsapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by Ante on 18/07/2017.
 */

public class NewsAdapter extends ArrayAdapter<News> {

    public NewsAdapter(Activity context, int resource) {
        super(context, resource);
    }

    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position    The position in the list of data that should be displayed in the list item view
     * @param convertView The recycled view to populate.
     * @param parent      The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_list_row, parent, false);
        }

        // Get the object located at this position in the list
        News currentPlace = getItem(position);

        // Find the TextView in the news_list_row.xml layout with the ID title, date, category
        TextView titleTextView = (TextView) listItemView.findViewById(R.id.title);
        TextView dateTextView = (TextView) listItemView.findViewById(R.id.date);
        TextView categoryTextView = (TextView) listItemView.findViewById(R.id.category);

        // Get the title, date, category from the current News object and
        // set this text on the TextViews
        titleTextView.setText(currentPlace.getmTitle());
        dateTextView.setText(currentPlace.getmDate());
        categoryTextView.setText(currentPlace.getmCategory());

        // Return the whole list item layout
        // so that it can be shown in the ListView
        return listItemView;
    }
}

