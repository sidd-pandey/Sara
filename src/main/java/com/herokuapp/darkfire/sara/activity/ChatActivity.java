package com.herokuapp.darkfire.sara.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.herokuapp.darkfire.sara.ComplaintDB;
import com.herokuapp.darkfire.sara.GenericMachine2;
import com.herokuapp.darkfire.sara.R;
import com.herokuapp.darkfire.sara.adapters.ChatAdapter;
import com.herokuapp.darkfire.sara.query.ComplaintQueryObj;
import com.herokuapp.darkfire.sara.utilities.MessagesDB;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChatActivity extends Activity {

    ListView chatView;
    Button sendMsg;
    EditText msgEditText;
    GenericMachine2 machine;
    ChatAdapter chatAdapter;

    private static final String COMPLAINT = "complaint";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        init();
        readFromDb();
    }

    private void init(){
        chatView = (ListView) findViewById(R.id.chat_list_view);
        sendMsg = (Button) findViewById(R.id.send_button);
        msgEditText = (EditText) findViewById(R.id.msg_edit_text);
        chatAdapter = new ChatAdapter(getBaseContext());
        machine = new GenericMachine2(chatAdapter, chatView, getBaseContext(), this);
        chatView.setAdapter(chatAdapter);

        sendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = msgEditText.getText().toString();
                msgEditText.setText("");
                if (msg == null || msg.length() == 0)
                    return;
                machine.processInput(msg);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chat, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MessagesDB.getInstance().msgList = chatAdapter.getData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(MessagesDB.getInstance().msgList != null){
            chatAdapter.setData(MessagesDB.getInstance().msgList);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveDbState();
    }

    private void saveDbState(){
        try {
            ObjectOutputStream os = new ObjectOutputStream(openFileOutput("complaint", Context.MODE_PRIVATE));
            os.writeObject(ComplaintDB.getInstance().getDb());
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readFromDb(){
        try {
            ObjectInputStream ois = new ObjectInputStream(openFileInput("complaint"));
            Object object = ois.readObject();
            if(object != null){
                HashMap<String, ComplaintQueryObj> map = (HashMap<String, ComplaintQueryObj>)object;
                ComplaintDB.getInstance().setDb(map);
            }
            ois.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
