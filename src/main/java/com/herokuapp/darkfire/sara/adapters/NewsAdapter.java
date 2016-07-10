package com.herokuapp.darkfire.sara.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.herokuapp.darkfire.sara.R;
import com.herokuapp.darkfire.sara.utilities.NewsItemObject;

import java.util.List;

/**
 * Created by Siddharth on 7/10/2016.
 */
public class NewsAdapter extends BaseAdapter{

    Context context;
    List<NewsItemObject> newsItems;
    LayoutInflater inflater;

    public NewsAdapter(Context context, List<NewsItemObject> newsItems){
        this.context = context;
        this.newsItems = newsItems;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return newsItems.size();
    }

    @Override
    public Object getItem(int position) {
        return newsItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.news_item, null);
        ((TextView)(view.findViewById(R.id.news_heading))).setText(newsItems.get(position).headline);
        return view;
    }
}
