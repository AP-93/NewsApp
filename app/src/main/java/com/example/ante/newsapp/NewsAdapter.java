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
        // to reference the child views for later actions
        ViewHolder holder;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_list_row, parent, false);

            // Get the object located at this position in the list
            News currentPlace = getItem(position);

            holder = new ViewHolder();

            // Find the TextView in the news_list_row.xml layout with the ID title, date, category
            holder.titleTextView = (TextView) listItemView.findViewById(R.id.title);
            holder.dateTextView = (TextView) listItemView.findViewById(R.id.date);
            holder.categoryTextView = (TextView) listItemView.findViewById(R.id.category);

            // Get the title, date, category from the current News object and
            // set this text on the TextViews
            holder.titleTextView.setText(currentPlace.getmTitle());
            holder.dateTextView.setText(currentPlace.getmDate());
            holder.categoryTextView.setText(currentPlace.getmCategory());

            // associate the holder with the view for later lookup
            listItemView.setTag(holder);
        } else {
            // view already exists, get the holder instance from the view
            holder = (ViewHolder) listItemView.getTag();
        }
        // Return the whole list item layout
        // so that it can be shown in the ListView
        return listItemView;
    }

    static class ViewHolder {
        TextView titleTextView;
        TextView dateTextView;
        TextView categoryTextView;
    }
}

