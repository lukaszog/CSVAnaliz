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


    public void setHead(List<String> headers){
        this.headers = headers;
    }

    public void setColumn(List<String> column){
        this.column = column;
    }


    public List<String> getHead(){
        System.out.println("Jestem w getHead");
        return new ArrayList<>(headers);
    }

    public List<String> getColumn(){
        System.out.println("Jestem w getColumn");
        return new ArrayList<>(column);
    }

    public void loadHeader(View view){

        System.out.println("Jestem w Model.java -  loadHeader");
        csvDAO csvDAO = new csvDAO();
        csvDAO.setItself(this);
        csvDAO.setJob("header");
        csvDAO.setView(view);
        csvDAO.execute();

    }

    public void loadColumnId(View view, int column){

        System.out.println("Jestem w Model.java -  loadColumnId");
        csvDAO csvDAO = new csvDAO();
        csvDAO.setItself(this);
        csvDAO.setJob("showColumn");
        csvDAO.setView(view);
        csvDAO.execute();
    }

    public void  openFile(View view) throws IOException {

        /*
        CSVReader ile = new CSVReader(new FileReader("plik.csv"),';');



        int columnCount=0;
        String[] header = ile.readNext(); // assuming first read

        if (header != null) {                     // and there is a (header) line
            columnCount = header.length;       // get the column count
        }

        System.out.println("Liczba kolumn: " + columnCount);

        CSVReader reader1 = new CSVReader(new FileReader("plik.csv"), ';');

        List<String[]> records = reader1.readAll();
        Iterator<String[]> iterator = records.iterator();

        headers = getHeader(records); //nazwy kolumn


        iterator.next();


        while(iterator.hasNext()){

            List<String> toRow = new ArrayList<>();
            String[] record = iterator.next();

            for(int i=0; i <columnCount-1; i++) {

                   toRow.add(record[i]);

            }
            Row row = new Row(toRow);
            toCSVFile.add(row);
        }
        CSVFile csvfile = new CSVFile(toCSVFile,headers);

        Iterator<String> headerIterator = headers.iterator();

        while(headerIterator.hasNext())
        {
            System.out.println(headerIterator.next());
        }

        cellMap.put(5,"14"); //numer kolumny, wartosc
        csvfile.filter(1,4,cellMap); //zakres, filrt
        */
     }

    public static List<String> getHeader(List<String[]> records){

        String[] firstRow = records.get(0);
        return  new ArrayList<>(Arrays.asList(firstRow));
    }



}
