package com.csv.proj.app.view;

/**
 * Created by Łukasz on 2014-12-30.
 */
public interface AppListener {

    public void getFile();
    public void getHeader(String filename);
    public void getData(String filename);
    public void getColumnId(int column);

}
