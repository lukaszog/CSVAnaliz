package com.csv.proj.app.model;

/**
 * Created by Łukasz on 2015-01-24.
 */
public class Header {

    private String name;
    private int id;

    public Header(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public int getId(){ return id; }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id){
        this.id = id;
    }
}
