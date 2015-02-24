package com.csv.proj.app.model;

/**
 * Created by Łukasz on 2015-01-24.
 */
public class CSV {

    private static CSV instance = new CSV();


    private void OpenFile(){
        try{
            this.open();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void open(){


    }

}
