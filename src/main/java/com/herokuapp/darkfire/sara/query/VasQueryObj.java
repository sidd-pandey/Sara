package com.herokuapp.darkfire.sara.query;

import java.io.Serializable;

/**
 * Created by Siddharth on 7/10/2016.
 */
public class VasQueryObj implements Serializable {

    public String id;
    public String name;
    public long activationTime;
    public String description;
    public boolean active = true;
}
