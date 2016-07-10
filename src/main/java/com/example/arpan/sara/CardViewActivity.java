package com.example.arpan.sara;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.herokuapp.darkfire.sara.ComplaintDB;
import com.herokuapp.darkfire.sara.R;
import com.herokuapp.darkfire.sara.query.ComplaintQueryObj;

import java.util.ArrayList;


public class CardViewActivity extends Activity {

    ArrayList<DataObject> results = new ArrayList<DataObject>();
    String myIssue;
    private TextView myIssue1;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String LOG_TAG = "CardViewActivity";
    private ComplaintQueryObj queryObj;

    private Button registerButton;
    private Button noRegisterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        results = (ArrayList<DataObject>) getIntent().getSerializableExtra(CBR.CASES);
        myIssue = getIntent().getStringExtra(CBR.MY_ISSUE);
        queryObj = (ComplaintQueryObj) getIntent().getSerializableExtra(CBR.COMPLAINT_OBJ);
        System.out.println("Query object:  " + queryObj.getIssue());
        setContentView(R.layout.activity_card_view);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        myIssue1 = (TextView)findViewById(R.id.textView7);
        myIssue1.setText(myIssue);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyRecyclerViewAdapter(results);
        mRecyclerView.setAdapter(mAdapter);

        init();
        // Code to Add an item with default animation
        //((MyRecyclerViewAdapter) mAdapter).addItem(obj, index);

        // Code to remove an item with default animation
        //((MyRecyclerViewAdapter) mAdapter).deleteItem(index);
    }

    private void init(){
        registerButton = (Button) findViewById(R.id.register_button);
        noRegisterButton = (Button) findViewById(R.id.no_register_complaint);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //enter the complaint in the database
                ComplaintDB.getInstance().addComplaint(queryObj);
                System.out.println("Added to database");
                new TweetConfig.PostTwitter().execute(queryObj.getIssue()+"@@"+queryObj.getId());
                finish();
            }
        });
        noRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //do not enter the complaint in database
                finish();
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        ((MyRecyclerViewAdapter) mAdapter).setOnItemClickListener(new MyRecyclerViewAdapter
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Log.i(LOG_TAG, " Clicked on Item " + position);
            }
        });
    }


}
