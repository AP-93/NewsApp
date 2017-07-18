package com.example.ante.newsapp;

/**
 * Created by Ante on 18/07/2017.
 */

public class News {

    String mTitle;
    String mUrl;
    String mDate;
    String mCategory;

    public News(String title, String url, String date, String category) {
        mTitle = title;

        mUrl = url;
        mDate = date;
        mCategory = category;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmUrl() {
        return mUrl;
    }

    public void setmUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    public String getmCategory() {
        return mCategory;
    }

    public void setmCategory(String mCategory) {
        this.mCategory = mCategory;
    }
}
