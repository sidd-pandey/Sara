package com.herokuapp.darkfire.sara.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.arpan.sara.TraiNewsCrawler;
import com.herokuapp.darkfire.sara.R;
import com.herokuapp.darkfire.sara.utilities.NewsItemObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Siddharth on 7/8/2016.
 */
public class ChatAdapter extends BaseAdapter {

    Context context;
    List<String> msgList;
    LayoutInflater inflater;

    public ChatAdapter(Context context){
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.msgList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return msgList.size();
    }

    @Override
    public Object getItem(int position) {
        return msgList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View view = null;

        if(msgList.get(position).split("###")[0].equals("sara")){
            view = inflater.inflate(R.layout.list_item_message_left, null);
            if(msgList.get(position).split("####")[0].contains("NEWS")){
                return newsLayout(position);
            }
            if(msgList.get(position).split("###")[1].contains("#traiSaraCompID")){
                view = inflater.inflate(R.layout.list_item_message_left_twitter, null);
            }
            ((TextView)(view.findViewById(R.id.txtMsg))).setText(msgList.get(position).split("###")[1]);
        } else{
            view = inflater.inflate(R.layout.list_item_message_right, null);
            ((TextView)(view.findViewById(R.id.txtMsg))).setText(msgList.get(position).split("###")[1]);
        }
        return view;
    }

    public void addMsg(String msg){
        msgList.add(msg);
        notifyDataSetChanged();
    }

    public List<String> getData(){
        return msgList;
    }
    public void setData(List<String> list){
        msgList = list;
        notifyDataSetChanged();
    }

    private View newsLayout(int position){
        String[] texts = msgList.get(position).split("####");
        List<NewsItemObject> newsItemObjectList = new ArrayList<>();
        for(int i = 1; i < texts.length; i++){
            NewsItemObject obj = new NewsItemObject();
            obj.headline = msgList.get(position).split("####")[i].split("@@@@")[0];
            obj.link = msgList.get(position).split("####")[i].split("@@@@")[1];
            newsItemObjectList.add(obj);
        }
        final NewsAdapter newsAdapter = new NewsAdapter(context, newsItemObjectList);
        View view = inflater.inflate(R.layout.news_layout, null);
        ListView listView = (ListView) view.findViewById(R.id.news_list_view);
        listView.setAdapter(newsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String link = ((NewsItemObject)newsAdapter.getItem(position)).link;
                Intent i = new Intent(Intent.ACTION_VIEW);
                System.out.println(TraiNewsCrawler.BASE_URL+link);
                i.setData(Uri.parse(TraiNewsCrawler.BASE_URL + link));
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
        return view;
    }
}
