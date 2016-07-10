package com.example.arpan.sara;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.herokuapp.darkfire.sara.GenericMachine2;
import com.herokuapp.darkfire.sara.interfaces.Machine;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Created by Arpan on 7/9/2016.
 */
public class TweetConfig{

    public static class PostTwitter extends AsyncTask<String, Void, Long> {
        Twitter twitter;

        @Override
        protected void onPreExecute() {


        }

        @Override
        protected Long doInBackground(String... params) {

            ConfigurationBuilder builder = new ConfigurationBuilder();
            builder.setOAuthConsumerKey("A0PJKFlUZyndsijEaFzhl4KZv");
            builder.setOAuthConsumerSecret("uC4DRsuiOPDc2xQtnM8XhIYkE0GXcMTcB6vveft6lfzJhSSaek");
            Configuration configuration = builder.build();
            TwitterFactory factory = new TwitterFactory(configuration);
            twitter = factory.getInstance();

            try {
                RequestToken requestToken = twitter.getOAuthRequestToken();
            } catch (TwitterException e) {
                e.printStackTrace();
            }
            AccessToken accessToken = new AccessToken("751814149340950528-wkFW1cIh4ZdEEYEz8ImeQsm6jTYqSEY", "9auRiT2uoMK6alJYbRneMUwFnorZJKoD8FoOhsKkwQG3w");
            //twitter.setOAuthAccessToken(accessToken);
            twitter.setOAuthAccessToken(accessToken);
            twitter4j.Status status = null;
            try {
                status = twitter.updateStatus(params[0].split("@@")[0] + "\nReply with #traiSaraCompID" + params[0].split("@@")[1] + " to help!!");
            } catch (TwitterException e) {
                e.printStackTrace();
            }
             System.out.println("Status : " + params[0]);
            Long ID = status.getId();
                System.out.println("ID is : " + ID);
                Log.i("is", "ds" + ID);
            return ID;
            }

        @Override
        protected void onPostExecute(Long aLong) {

        }
    }

    public static class CheckTwitter extends AsyncTask<String, Void, List<Status>> {
        Twitter twitter;
        Machine machine;

        public CheckTwitter(Machine machine){
            this.machine = machine;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected List<twitter4j.Status> doInBackground(String... params) {

            ConfigurationBuilder builder = new ConfigurationBuilder();
            builder.setOAuthConsumerKey("A0PJKFlUZyndsijEaFzhl4KZv");
            builder.setOAuthConsumerSecret("uC4DRsuiOPDc2xQtnM8XhIYkE0GXcMTcB6vveft6lfzJhSSaek");
            Configuration configuration = builder.build();
            TwitterFactory factory = new TwitterFactory(configuration);
            twitter = factory.getInstance();

            //twitter = TwitterFactory.getSingleton();
            //twitter.setOAuthConsumer("A0PJKFlUZyndsijEaFzhl4KZv", "uC4DRsuiOPDc2xQtnM8XhIYkE0GXcMTcB6vveft6lfzJhSSaek");
            try {
                RequestToken requestToken = twitter.getOAuthRequestToken();
            } catch (TwitterException e) {
                e.printStackTrace();
            }
            AccessToken accessToken = new AccessToken("751814149340950528-wkFW1cIh4ZdEEYEz8ImeQsm6jTYqSEY", "9auRiT2uoMK6alJYbRneMUwFnorZJKoD8FoOhsKkwQG3w");
            //twitter.setOAuthAccessToken(accessToken);
            twitter.setOAuthAccessToken(accessToken);
            Query query = new Query("#traiSaraCompID"+params[0]);
            QueryResult qr = null;
            try {
                qr = twitter.search(query);
            } catch (TwitterException e) {
                e.printStackTrace();
            }
          //  List<String> text = null;
            List<twitter4j.Status> qrTweets = qr.getTweets();
            Log.i("sdsd", "size is: " + qrTweets.size());

            return qrTweets;
        }

        @Override
        protected void onPostExecute(List<twitter4j.Status> tweets) {
            super.onPostExecute(tweets);
            //List<String> text = new ArrayList<>();
            for(twitter4j.Status st : tweets){
                if(!st.getUser().getName().equals("Sara")) {
                    machine.sendInput(st.getUser().getName() + " tweets " + st.getText());
                }
            }
        }
    }

    }



