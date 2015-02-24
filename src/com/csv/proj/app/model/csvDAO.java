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
    private Map<Integer,String> cellMap = new HashMap<>();
    private CSVFile csvfile;
    private String job;


    public void setItself(Model model) {
        this.model = model;
    }
    public void setJob(String job){
        this.job = job;
    }
    public void setView(View view) {
        this.view = view;
    }



    protected Void doInBackground() throws Exception {

        if(job.equals("header")) {

            headers = getHeaders();
            System.out.println("Swing worker pracuje");

        }
        if(job.equals("showColumn")){

            System.out.println("Jestem w showColumn");
            csvfile = getColumn();
        }

        return null;
    }

    protected void done() {

        System.out.println("Jestem w done");


        if(job.equals("showColumn")){


            System.out.println("jestm w done - showColumn");
        }

        if(job.equals("header")) {

            model.setHead(headers);
            view.loadHead();
            System.out.println("jestm w done - header");


        }

    }

    protected CSVFile getColumn() throws  IOException{

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
        csvfile = new CSVFile(toCSVFile,headers);

        Iterator<String> headerIterator = headers.iterator();



        cellMap.put(5,"14"); //numer kolumny, wartosc
        csvfile.filter(1,4,cellMap); //zakres, filrt

        return csvfile;
    }

    protected List<String> getHeaders() throws IOException{

        List<String> head = new ArrayList<>();

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

        head = getHeader(records); //nazwy kolumn

        return head;
    }

    public static List<String> getHeader(List<String[]> records){

        String[] firstRow = records.get(0);
        return  new ArrayList<>(Arrays.asList(firstRow));
    }

}
