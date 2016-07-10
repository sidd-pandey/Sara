package com.herokuapp.darkfire.sara.tasks;

import android.os.AsyncTask;

import com.example.arpan.sara.TraiNewsCrawler;
import com.herokuapp.darkfire.sara.interfaces.Machine;

import java.util.List;

/**
 * Created by Siddharth on 7/10/2016.
 */
public class LoadNewsTask extends AsyncTask<Void, Void, List<String>> {

    Machine machine;

    public LoadNewsTask(Machine machine){
        this.machine = machine;
    }

    @Override
    protected void onPreExecute() {
        //
        machine.sendInput("I will download the news in background");
        machine.sendInput("Meanwhile you can continue");
    }

    @Override
    protected List<String> doInBackground(Void... params) {
        List<String> news = TraiNewsCrawler.getNews();
        return news;
    }

    @Override
    protected void onPostExecute(List<String> news) {
        //convert list to a single string
        StringBuilder builder = new StringBuilder("NEWS####");
        for(int i = 0; i < news.size(); i++){
            if(i == 4) {
                builder.append(news.get(i));
                break;
            }
            builder.append(news.get(i)+"####");
        }
        machine.sendInput(builder.toString());
    }
}
