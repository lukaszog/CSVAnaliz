package com.csv.proj.app.model;

import au.com.bytecode.opencsv.CSVReader;
import com.csv.proj.app.view.View;

import javax.swing.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by Łukasz on 2014-12-29.
 */
public class Model{

    private List<Row> toCSVFile = new LinkedList<>();
    private Map<Integer,String> cellMap = new HashMap<>();
    private List<String> headers = new LinkedList<>();
    private List<String> column = new LinkedList<>();
    private Map<Integer, Integer> data = new HashMap<>();



    public void setHead(List<String> headers){
        this.headers = headers;
    }

    public void setData(Map<Integer, Integer> data){
        this.data = data;
    }

    public Map<Integer,Integer> getData(){
        return data;
    }

    public List<String> getHead(){
        System.out.println("Jestem w getHead");
        return new ArrayList<>(headers);
    }

    public List<String> getColumn(){
        System.out.println("Jestem w getColumn");
        return new ArrayList<>(column);
    }

    public void loadHeader(View view,String filename){

        System.out.println("Jestem w Model.java -  loadHeader");
        csvDAO csvDAO = new csvDAO(filename);
        csvDAO.setItself(this);
        csvDAO.setJob("header");
        csvDAO.setView(view);
        csvDAO.execute();

    }
    public void filterColumn(View view, Map<Integer,JTextField> dataMap,String filename, List<Integer> sumList, int from, int to){

        System.out.println("Jestem w Model.java -  filterColumn");
        csvDAO csvDAO = new csvDAO(filename);
        csvDAO.setItself(this);
        csvDAO.setJob("filterColumn");
        csvDAO.setView(view);
        csvDAO.setMap(dataMap);
        csvDAO.setColumnToSum(sumList);
        csvDAO.setFromTo(from,to);
        csvDAO.execute();

    }
    public static List<String> getHeader(List<String[]> records){

        String[] firstRow = records.get(0);
        return  new ArrayList<>(Arrays.asList(firstRow));
    }



}
