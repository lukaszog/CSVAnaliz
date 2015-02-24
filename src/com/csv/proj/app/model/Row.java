package com.csv.proj.app.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Łukasz on 2014-12-17.
 */
public class Row {

    private List<String> list = new LinkedList<String>();


    public Row(List<String> list) {

        this.list = list;
    }

    public List<String> getList() {
        return list;
    }

    public String getCell(int column){

        return list.get(column);
    }

    public int getCellInt(int column){

        //TODO zrobic try catch
        String number=getCell(column);
        return Integer.parseInt(number);
    }
}
