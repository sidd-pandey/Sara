package com.herokuapp.darkfire.sara;

import com.herokuapp.darkfire.sara.objects.Complaint;
import com.herokuapp.darkfire.sara.query.ComplaintQueryObj;
import com.herokuapp.darkfire.sara.query.VasQueryObj;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Siddharth on 7/9/2016.
 */
public class ComplaintDB {

    private static ComplaintDB INSTANCE;
    private HashMap<String, ComplaintQueryObj> db = new HashMap<>();
    private HashMap<String, VasQueryObj> vasDb = new HashMap<>();
    private boolean added;

    public String getLastId() {
        return lastId;
    }

    private String lastId;

    private ComplaintDB(){
        createSomeVAS();
    }

    public static ComplaintDB getInstance(){
        if(INSTANCE == null){
            INSTANCE = new ComplaintDB();
        }
        return INSTANCE;
    }

    public void addComplaint(ComplaintQueryObj obj){
        db.put(obj.getId(), obj);
        System.out.println("Enterd data to database");
        lastId = obj.getId();
        added = true;
    }
    public ComplaintQueryObj getComplaint(String id, ComplaintQueryObj obj){
        return db.get(id);
    }

    public boolean isAdded(){
        boolean result = added;
        added = false;
        return result;
    }
    public int getNumber(){
        System.out.println("Size of complaint db is:" + db.size());
        return db.size();
    }

    public HashMap<String, ComplaintQueryObj> getDb(){
        return db;
    }
    public void setDb(HashMap<String, ComplaintQueryObj> map){
        this.db  = map;
    }
    public HashMap<String, VasQueryObj> getVasDb(){
        return vasDb;
    }

    private void createSomeVAS(){
        VasQueryObj vasQueryObj = new VasQueryObj();
        vasQueryObj.id = "12CLT";
        vasQueryObj.name = "Custom Caller Tune";
        vasQueryObj.description = "Play a custom caller tune";
        vasQueryObj.activationTime = new Date().getTime();
        vasDb.put(vasQueryObj.id, vasQueryObj);

        vasQueryObj = new VasQueryObj();
        vasQueryObj.id = "41STK";
        vasQueryObj.name = "Market Updates";
        vasQueryObj.description = "Get Stock market updates.";
        vasQueryObj.activationTime = new Date().getTime();
        vasDb.put(vasQueryObj.id, vasQueryObj);

        vasQueryObj = new VasQueryObj();
        vasQueryObj.id = "56MUS";
        vasQueryObj.name = "Free music at music.in";
        vasQueryObj.description = "Free streaming of your favorite music.";
        vasDb.put(vasQueryObj.id, vasQueryObj);
    }

}
