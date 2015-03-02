package com.csv.proj.app.model;
import com.csv.proj.app.view.View;
import javax.swing.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import au.com.bytecode.opencsv.CSVReader;



/**
 * Created by Łukasz on 2015-02-10.
 */
public class csvDAO extends SwingWorker<Void, Void> {

    private Model model;
    private View view;
    private List<String> headers;
    private Map<Integer,String> column;
    private List<Row> toCSVFile = new LinkedList<>();
    private List<Row> DatatoCSVFile = new LinkedList<>();
    private Map<Integer,String> cellMap = new HashMap<>();
    private CSVFile csvfile;
    private String job;
    private String filename;
    private Map<Integer,JTextField> map = new HashMap<>();
    private Map<Integer, Integer> results;
    private Map<Integer,Integer> sumList;
    private Map<Integer,String> dataRow;
    private int from;
    private int RowCount=0;
    private int to;

    public void setItself(Model model) {
        this.model = model;
    }
    public void setJob(String job){
        this.job = job;
    }
    public void setView(View view) {
        this.view = view;
    }

    public void setMap(Map<Integer,JTextField> dataMap){
        this.map = dataMap;
    }
    public csvDAO(String filename){
        this.filename = filename;
    }

    public void setColumnToSum(Map<Integer,Integer> sumList){
        this.sumList = sumList;
    }
    public void setFromTo(int from, int to){
        this.from = from;
        this.to = to;
    }


    protected Void doInBackground() throws Exception {

        if(job.equals("header")) {

            headers = getHeaders();
            System.out.println("Swing worker pracuje");

        }
        if(job.equals("showColumn")){

            System.out.println("Jestem w showColumn");
          //  csvfile = getColumn();
        }
        if(job.equals("filterColumn")){
            System.out.println("Jestem w filterColumn");
            csvfile = getColumn();
        }
        if(job.equals("getData")){
            getData();
        }

        return null;
    }

    protected void done() {

        System.out.println("Jestem w done");


        if(job.equals("showColumn")){

            model.setData(results);
            System.out.println("jestm w done - showColumn");
        }
        if(job.equals("filterColumn")){
            System.out.println("jestem w done - filterColumn");
            model.setData(results);
            view.showResult();
        }
        if(job.equals("header")) {

            model.setHead(headers);
            view.loadHead();
            System.out.println("jestm w done - header");
        }
        if(job.equals("getData")){
            System.out.println("Jestem w getData");
            model.setDataColumn(dataRow);
            view.loadDataColumn();

        }

    }

    protected Map<Integer, String> getData() throws  IOException{

        CSVReader ile = new CSVReader(new FileReader(filename),';');
        System.out.println("Jestem w getColumn");

        int columnCount=0;
        String[] header = ile.readNext(); // assuming first read

        if (header != null) {                     // and there is a (header) line
            columnCount = header.length;       // get the column count
        }
        System.out.println("Liczba kolumn: " + columnCount);
        CSVReader reader1 = new CSVReader(new FileReader(filename), ';');

        List<String[]> records = reader1.readAll();
        Iterator<String[]> iterator = records.iterator();

        headers = getHeader(records); //nazwy kolumn
        iterator.next();

        toCSVFile.clear();
        while(iterator.hasNext()){

            List<String> toRow = new ArrayList<>();
            String[] record = iterator.next();
            int ids=0;

                for (int i = 0; i < 2; i += 2) {

                    toRow.add(record[i] + " - " + record[i + 1]);
                    ids++;
                    //System.out.println(record[i] + " leci data " + record[i + 1] + " koniec wiersza, iteracja nr: "+i);

                }
                Row row = new Row(toRow);
                DatatoCSVFile.add(row);
        }
            csvfile = new CSVFile(DatatoCSVFile,null);

        dataRow = csvfile.getData();

        //dataRow.put(2323,"costam csasdasdjoasijdoasj");


        return dataRow;

    }
    protected CSVFile getColumn() throws  IOException{

        CSVReader ile = new CSVReader(new FileReader(filename),';');
        System.out.println("Jestem w getColumn");

        Map<Integer,String> dataRow = new HashMap<>();

        int columnCount=0;
        String[] header = ile.readNext(); // assuming first read

        if (header != null) {                     // and there is a (header) line
            columnCount = header.length;       // get the column count
        }
        System.out.println("Liczba kolumn: " + columnCount);
        CSVReader reader1 = new CSVReader(new FileReader(filename), ';');

        List<String[]> records = reader1.readAll();
        Iterator<String[]> iterator = records.iterator();

        headers = getHeader(records); //nazwy kolumn
        iterator.next();

        while(iterator.hasNext()){

            List<String> toRow = new ArrayList<>();
            String[] record = iterator.next();

            for(int i=0; i <columnCount-1; i++) {

                toRow.add(record[i]);
                RowCount++;

            }

            Row row = new Row(toRow);
            toCSVFile.add(row);
        }
        csvfile = new CSVFile(toCSVFile,headers);

        int i=0;
        cellMap.clear();
        for (Map.Entry<Integer, JTextField> entry : map.entrySet())
        {
             cellMap.put(entry.getKey(),entry.getValue().getText()); //numer kolumny, wartosc
        }
        System.out.println("Filename: " + filename + RowCount);

        results = csvfile.calculate(from,to,cellMap,sumList);

        return csvfile;

    }

    protected List<String> getHeaders() throws IOException{

        List<String> head = new ArrayList<>();

        CSVReader ile = new CSVReader(new FileReader(filename),';');

        int columnCount=0;
        String[] header = ile.readNext(); // assuming first read

        if (header != null) {                     // and there is a (header) line
            columnCount = header.length;       // get the column count
        }

        System.out.println("Liczba kolumn: " + columnCount);

        CSVReader reader1 = new CSVReader(new FileReader(filename), ';');
        List<String[]> records = reader1.readAll();
        Iterator<String[]> iterator = records.iterator();

        head = getHeader(records); //nazwy kolumn

        return head;
    }

    public static List<String> getHeader(List<String[]> records){

        String[] firstRow = records.get(0);
        return  new ArrayList<>(Arrays.asList(firstRow));
    }

}
