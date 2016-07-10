package com.herokuapp.darkfire.sara.query;

import java.io.Serializable;

/**
 * Created by Siddharth on 7/9/2016.
 */
public class ComplaintQueryObj implements Serializable{
    private String domain;
    private String sub_domain;
    private String issue;
    private String id;
    private boolean open;

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getSub_domain() {
        return sub_domain;
    }

    public void setSub_domain(String sub_domain) {
        this.sub_domain = sub_domain;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }
}
