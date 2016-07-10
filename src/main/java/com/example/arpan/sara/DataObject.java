package com.example.arpan.sara;

import java.io.Serializable;

public class DataObject implements Serializable{
    private String mText1;
    private String mText2;

    DataObject (String text1, String text2){
        mText1 = text1;
        mText2 = text2;
    }

    public String getIssue() {
        return mText1;
    }

    public void setIssue(String mText1) {
        this.mText1 = mText1;
    }

    public String getSolution() {
        return mText2;
    }

    public void setSolution(String mText2) {
        this.mText2 = mText2;
    }
}