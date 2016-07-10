package com.example.arpan.sara;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.util.Log;

import com.herokuapp.darkfire.sara.objects.Complaint;
import com.herokuapp.darkfire.sara.query.ComplaintQueryObj;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;


import au.com.bytecode.opencsv.CSVReader;


public class CBR {

    private Context context;
    private ArrayList<DataObject> list;
    private String myIssue;

    public static final String MY_ISSUE = "my_issue";
    public static final String CASES = "cases";
    public static final String COMPLAINT_OBJ = "Complaint_Obj";

    public CBR(Context context) {
        this.context = context;
        this.list = new ArrayList<>();
    }

    public void loadCBR(Complaint.Procedure procedure) {
        try {
            getSolution(procedure);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        Intent i = new Intent(context, CardViewActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra(MY_ISSUE, myIssue);
        i.putExtra(CASES, list);
        ComplaintQueryObj obj = makeComplaintObject(procedure);
        i.putExtra(COMPLAINT_OBJ,obj);
        context.startActivity(i);
    }

    public void getSolution(Complaint.Procedure procedure) throws IOException {
        
        String domain = procedure.domain.toLowerCase();

        String subDomain = procedure.subDomain.toLowerCase();
        String issue = procedure.issue;
        System.out.println("domain: " + domain + " subDomain: " + subDomain + " Issue:" + issue);
        myIssue = issue;
        try {
            CSVReader reader = new CSVReader(new InputStreamReader(context.getAssets().open("Book.csv")));
            //in open();
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                // nextLine[] is an array of values from the line
                // System.out.print("1");
                System.out.println("CSV Heading are: " + nextLine[0].toLowerCase()+ " " + nextLine[1].toLowerCase());
                if (domain.equals(nextLine[0].toLowerCase()) && subDomain.equals(nextLine[1].toLowerCase())) {
                    System.out.println("" + nextLine[3].toLowerCase() + " " + issue.toLowerCase());
                    if (GetSolution.getRatio(nextLine[3].toLowerCase(), issue.toLowerCase(), false) >= 60) {
                        list.add(new DataObject(nextLine[3], nextLine[4]));
                        //CardViewActivity.setDataSet(nextLine[3], nextLine[4]);
                        //CardViewActivity.setMyIssue(issue);
                    } else
                        Log.i("result", "false");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ComplaintQueryObj makeComplaintObject(Complaint.Procedure procedure) {
        ComplaintQueryObj queryObj = new ComplaintQueryObj();
        queryObj.setDomain(procedure.domain);
        queryObj.setSub_domain(procedure.subDomain);
        queryObj.setIssue(procedure.issue);
        queryObj.setOpen(true);
        queryObj.setId(""+new Date().getTime());
        return queryObj;
    }

}
