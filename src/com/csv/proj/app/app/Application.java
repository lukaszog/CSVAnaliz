package com.csv.proj.app.app;

import com.csv.proj.app.Controller.Controller;
import com.csv.proj.app.model.Model;
import com.csv.proj.app.view.View;

import javax.swing.*;

/**
 * Created by Łukasz on 2014-12-29.
 */
public class Application {

    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try{
                    runApp();
                    System.out.println("Aplikacja uruchomiona");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }


    public static void runApp(){

        Model model = new Model();
        View view = new View();
        Controller controller = new Controller(view,model);
        view.setAppListener(controller);


    }
}
