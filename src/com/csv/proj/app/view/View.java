package com.csv.proj.app.view;

import com.csv.proj.app.model.*;
import javafx.event.ActionEvent;
import net.miginfocom.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 * Created by Łukasz on 2014-12-29.
 */
public class View extends JFrame implements ViewListener {


    private Model model;
    private AppListener appListener;
    private ViewListener viewListener;
    private List<String> header;
    private List<String> column;
    private JTable headerTable = new JTable();
    private DefaultTableModel tablemodel = new DefaultTableModel();
    private JScrollPane scrollHeader = new JScrollPane();
    private JScrollPane scrollOperation = new JScrollPane();
    private JScrollPane scrollTop = new JScrollPane();
    private JPanel operationTop = new JPanel();
    private JPanel operationContent = new JPanel();
    private JPanel resultPane = new JPanel();
    private JPanel operationBottom  = new JPanel();
    private DefaultTableModel headermodel = new DefaultTableModel();
    private JCheckBox warunekchk = new JCheckBox();
    private JTextField jTextField;
    private JTextField rangeF;
    private JTextField rangeT;
    private JLabel range;
    private Map<Integer,JTextField> textField = new HashMap<>();
    private int count;
    private String jTextFieldName;
    private int xgdid=5;
    private Vector fields = new Vector<>();
    private Map<String, JTextField> field = new HashMap<>();




    public View(){

        super("Magazyn");

    }

    public void setModel(Model model) {

        this.model = model;

    }

    public void initializeView() {


        pack();
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        fireOpenEvent();

    }

    @Override
    public void showColums() {

        System.out.println("Jestem w showColums");



    }

    public void loadHead(){

        header = model.getHead();

        int id=1;
        for(String head: header) {
            headermodel.addRow(new Object[]{id,head});
            id++;
        }
        System.out.println("jestem w loadhead");
    }

    public void loadColumn(){

        column = model.getColumn();

        for(String col:column){
            System.out.println(new Object[]{col});
        }
        System.out.println("jestem w loadColumn");

    }



    public void fireOpenEvent(){

        GridLayout gridLayout = new GridLayout(1,1);

        if(appListener!=null){
            //appListener.getFile();
            appListener.getHeader();


            headerTable = new JTable(headermodel);
            operationTop = new JPanel();
            operationContent = new JPanel();
            operationBottom = new JPanel();

            //appListener.getColumnId(2);
            operationContent.setLayout(new BoxLayout(operationContent, BoxLayout.X_AXIS));


            scrollHeader = new JScrollPane(headerTable);
            scrollOperation = new JScrollPane(operationContent);
            scrollTop = new JScrollPane(operationTop);

            operationContent.setLayout(new BoxLayout(operationContent, BoxLayout.X_AXIS));


            headermodel.addColumn("Lp.");
            headermodel.addColumn("Nazwa kolumny");
            headermodel.addColumn("Kolumna warunkowa");
            headermodel.addColumn("Sumowanie");

            JCheckBox checkBox = new JCheckBox();

            TableColumnModel tcm = headerTable.getColumnModel();
            // tcm.getColumn(0).setMaxWidth(30);
            // tcm.getColumn(1).setMaxWidth(120);
            // tcm.getColumn(2).setMaxWidth(120);
            // tcm.getColumn(3).setMaxWidth(120);
            tcm.getColumn(2).setCellEditor(new DefaultCellEditor(new JCheckBox()));
            tcm.getColumn(3).setCellEditor(new DefaultCellEditor(new JCheckBox()));


            scrollHeader.setBorder(new TitledBorder("Nazwy kolumn"));
            operationContent.setBorder(new TitledBorder("Operacje"));
            resultPane.setBorder(new TitledBorder("Finalizuj"));

            operationTop.setLayout(new MigLayout("left left, , gapy 2"));
            operationContent.setLayout(new MigLayout("left left, , gapy 2"));
            operationBottom.setLayout(new MigLayout("left left, wrap, gapy 112"));

            range = new JLabel("Zakres wierszy: ");
            JLabel fi = new JLabel("do");

            rangeF = new JTextField(5);
            rangeT = new JTextField(5);

            operationTop.add(range);
            operationTop.add(rangeF);
            operationTop.add(fi);
            operationTop.add(rangeT,"wrap");

            boolean selected = checkBox.isSelected();



            headerTable.getModel().addTableModelListener(new TableModelListener() {

                @Override
                public void tableChanged(TableModelEvent e) {
                    if(e.getColumn() >= 0  && e.getFirstRow()>-1){
                        int id =  e.getFirstRow();
                        int lp =  (int)headerTable.getValueAt(e.getFirstRow(), 0);
                        String colName = (String)headerTable.getValueAt(e.getFirstRow(), 1);
                        boolean colValue = (boolean)headerTable.getValueAt(e.getFirstRow(), 2);

                        System.out.println("Row : " + e.getFirstRow() +
                                   " value :" + headerTable.getValueAt(e.getFirstRow(), 2));
                        appListener.getColumnId(id);

                        //create texfield

                        if(colValue){

                            jTextField = new JTextField(20);
                            textField.put(id,jTextField);
                            field.put(colName,jTextField);
                            refreshPanel();

                            if (textField != null && !textField.isEmpty()) {
                                textField.get(textField.size()-1);
                                System.out.println("Add");
                            }

                        } else {
                            System.out.println("Delete" + lp);
                            field.remove(colName);
                            textField.remove(id);
                            refreshPanel();
                        }
                        revalidate();
                        repaint();
                    }
                }
            });

            JButton button = new JButton("Click Me!");
            button.addActionListener(e -> {

                System.out.println("Click Detected by Lambda Listner");

                for (Map.Entry<Integer, JTextField> entry : textField.entrySet())
                {
                    System.out.println(entry.getKey() + "/" + entry.getValue().getText());
                }

            });
            resultPane.add(button);

            setLayout(gridLayout);
            operationContent.add(resultPane);
            add(scrollHeader);
            add(scrollOperation);
            add(resultPane);
        }
    }

    public void refreshPanel()
    {
        operationContent.removeAll();
        for (Map.Entry<String, JTextField> entry : field.entrySet())
        {
            JLabel labelName = new JLabel(entry.getKey()+": ");
            operationContent.add(labelName,"");
            operationContent.add(entry.getValue(), "growy, wrap");

        }
        revalidate();
        repaint();
    }

    public void setAppListener(AppListener appListener) {

        this.appListener = appListener;
    }


}

