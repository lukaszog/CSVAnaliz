package com.csv.proj.app.Controller;

import com.csv.proj.app.model.Model;
import com.csv.proj.app.view.AppListener;
import com.csv.proj.app.view.View;
import com.csv.proj.app.view.ViewListener;

import javax.swing.*;
import java.util.List;
import java.util.Map;


public class Controller implements AppListener,ViewListener {

    private View view;
    private Model model;
    private String filename;


    public Controller(View view, Model model){

        this.view = view;
        this.model = model;

        view.setModel(model);
        view.setAppListener(this);
        view.initializeView();

    }
    public void setFilename(String filename){
        this.filename = filename;
    }
    public void setModel(Model model) {
        this.model = model;
    }

    /*
    Otwarcie pliku
     */
    public void getFile(){
        try{
           // model.openFile(view);
            System.out.println("Jestem w getFile");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void dataMap(Map<Integer,JTextField> jTextFieldMap, String filename, List<Integer> sumList, int from, int to){
        try{
            System.out.println("Jestem w dataMap");
            model.filterColumn(view,jTextFieldMap, filename, sumList, from, to);
        }catch(Exception e){
            e.printStackTrace();
        }

    }
    public void getHeader(String filename){
        try{
            model.loadHeader(view,filename);
            System.out.println("Kontroler: getHeader");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void getColumnId(int column){
        try{
          //  model.loadColumnId(view,column);
            System.out.println("Kontroler: getColumnId"+ column);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
