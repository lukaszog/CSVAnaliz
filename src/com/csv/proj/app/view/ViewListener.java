package com.csv.proj.app.view;

import javax.swing.*;
import java.util.List;
import java.util.Map;

/**
 * Created by Łukasz on 2014-12-30.
 */
public interface ViewListener {

    public void dataMap(Map<Integer,JTextField> textFieldEntry, String filename, Map<Integer,Integer> sumList, int from, int to);

}
