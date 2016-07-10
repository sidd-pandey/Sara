package com.herokuapp.darkfire.sara.utilities;

import java.util.List;

/**
 * Created by Siddharth on 7/9/2016.
 */
public class MessagesDB {
    public List<String> msgList;

    private static MessagesDB INSTANCE;

    private MessagesDB(){

    }

    public static MessagesDB getInstance(){
        if(INSTANCE == null){
            INSTANCE = new MessagesDB();
        }
        return INSTANCE;
    }
}
