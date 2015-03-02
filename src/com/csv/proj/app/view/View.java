package com.csv.proj.app.view;

import com.csv.proj.app.model.*;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.event.ActionEvent;
import net.miginfocom.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.print.PrinterException;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileOutputStream;
import java.text.NumberFormat;
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
public class View extends JFrame implements PropertyChangeListener{


    private Model model;
    private View view;
    private AppListener appListener;
    private ViewListener viewListener;
    private List<String> header;
    private List<String> column;
    private JTable headerTable = new JTable();
    private DefaultTableModel tablemodel = new DefaultTableModel();
    private JScrollPane scrollHeader = new JScrollPane();
    private JScrollPane scrollOperation = new JScrollPane();
    private JScrollPane scrollTop = new JScrollPane();
    private JScrollPane scrollResult = new JScrollPane();
    private JPanel operationTop = new JPanel();
    private JPanel resultPanel = new JPanel();
    private JPanel operationContent = new JPanel();
    private JPanel resultPane = new JPanel();
    private JPanel operationBottom  = new JPanel();
    private JPanel contentPane = new JPanel();
    private DefaultTableModel headermodel = new DefaultTableModel();
    private JCheckBox warunekchk = new JCheckBox();
    private JTextField jTextField;
    private JLabel range;
    private Map<Integer,JTextField> textField = new HashMap<>();
    private int count=0;
    private String jTextFieldName;
    private int xgdid=5;
    private Vector fields = new Vector<>();
    private Map<String, JTextField> field = new HashMap<>();
    private int[] sum;
    private int sum_do;
    private String filename;
    private JFormattedTextField rangeF;
    private JFormattedTextField rangeT;
    private JFormattedTextField numPeriodsField;
    private NumberFormat amountFormat;
    private JMenuBar menuBar;
    private JMenu menu, submenu;
    private JMenuItem menuItem;
    private Map<Integer,Integer> sumList;
    private Map<Integer,Integer> resultMap;
    private Map<Integer,String> headerMap = new HashMap<>();
    private Map<Integer,String> dataMap = new HashMap<>();
    private JComboBox dataCombo = new JComboBox();
    private JComboBox dataComboTo = new JComboBox();
    private int dataFrom;
    private int dataTo;
    private JTextField filename_pdf = new JTextField(), dir = new JTextField();
    private String file;


    public View(){
        super("Calculate CSV File");

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

        menuBar = new JMenuBar();

        menu = new JMenu("File");
        menu.setMnemonic(KeyEvent.VK_A);
        menu.getAccessibleContext().setAccessibleDescription(
                "The only menu in this program that has menu items");

        menuItem = new JMenuItem("Open file",
                KeyEvent.VK_T);

        menuItem.getAccessibleContext().setAccessibleDescription(
                "This doesn't really do anything");
        menu.add(menuItem);

        menuItem.addActionListener(e -> {
            System.out.println("File Choose");
            openFile(1);
        });


            menuBar.add(menu);
            setJMenuBar(menuBar);
            //fireOpenEvent();
            openFile(0);
        }


    public void openFile(int flag){

        if(flag==1) {

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
            int result = fileChooser.showOpenDialog(this);

            remove(contentPane);
            remove(operationTop);
            remove(operationContent);
            remove(resultPane);
            remove(operationBottom);
            remove(headerTable);
            remove(scrollHeader);
            remove(scrollOperation);
            remove(scrollTop);


            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                filename = selectedFile.getAbsolutePath();
                System.out.println("Selected file: " + selectedFile.getAbsolutePath());


                remove(contentPane);
                remove(operationTop);
                remove(operationContent);
                remove(resultPane);
                remove(operationBottom);
                remove(headerTable);
                remove(scrollHeader);
                remove(scrollOperation);
                remove(scrollTop);

                revalidate();
                repaint();


                fireOpenEvent(filename);

                revalidate();
                repaint();
            }
        }
        if(flag==0) {

            contentPane = new JPanel(new MigLayout("center",                 // Layout Constraints
                    "center",             // Column constraints
                    ""));
            JButton file = new JButton("Open file");
            JPanel buttonPanel = new JPanel(new MigLayout("", "[center, grow]"));
            buttonPanel.add(file, "");
            contentPane.add(buttonPanel, "dock south");
            add(contentPane);

            file.addActionListener(e -> {

                System.out.println("File Choose");

                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                int result = fileChooser.showOpenDialog(this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    filename = selectedFile.getAbsolutePath();
                    System.out.println("Selected file: " + selectedFile.getAbsolutePath());

                    fireOpenEvent(filename);
                    remove(contentPane);
                    revalidate();
                    repaint();
                }
            });
        }
    }


    public void loadHead(){

        header = model.getHead();

        int ids=0;
        int id=1;

        for(String head: header) {
            headermodel.addRow(new Object[]{id,head});
            headerMap.put(ids,head);
            id++;
            ids++;
            count++;
         }
    }

    public void loadDataColumn(){

        dataMap = model.getDataColumn();

        for(Map.Entry<Integer,String> entry : dataMap.entrySet()){

            dataCombo.addItem(new Item(entry.getKey(),entry.getValue()));
            dataComboTo.addItem(new Item(entry.getKey(),entry.getValue()));
        }
    }

    public void fireOpenEvent(String filename){

        GridLayout gridLayout = new GridLayout(1,1);

        if(appListener!=null){
            //appListener.getFile();
            appListener.getHeader(filename);
            appListener.getData(filename);


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


            scrollHeader.setBorder(new TitledBorder("Column names"));
            operationContent.setBorder(new TitledBorder("Constraint"));
            resultPane.setBorder(new TitledBorder("Final"));

            operationTop.setLayout(new MigLayout("left left,wrap , gapy 2"));
            operationContent.setLayout(new MigLayout("left left, , gapy 2"));
            operationBottom.setLayout(new MigLayout("left left, wrap, gapy 112"));

            range = new JLabel("Range: ");
            JLabel from = new JLabel("From:");
            JLabel fi = new JLabel("to:");


            rangeT = new JFormattedTextField(amountFormat);
            rangeF = new JFormattedTextField(amountFormat);

            rangeT.setColumns(5);
            rangeF.setColumns(5);


            numPeriodsField = new JFormattedTextField();
            numPeriodsField.setValue(new Integer(3));
            numPeriodsField.setColumns(10);
            numPeriodsField.addPropertyChangeListener("value", this);



            rangeT.addPropertyChangeListener("value", this);
            rangeF.addPropertyChangeListener("value", this);

            operationTop.add(range);
            operationTop.add(from);
            operationTop.add(dataCombo);
            operationTop.add(fi);
            operationTop.add(dataComboTo);
            resultPane.add(operationTop);

            System.out.println("Liczba kolumn"+ count);

            dataCombo.addActionListener(e->{

                Item selected_item_from = (Item) dataCombo.getSelectedItem();
                dataFrom = selected_item_from.getId();
            });

            dataComboTo.addActionListener(e->{
                Item selected_item_to = (Item) dataComboTo.getSelectedItem();
                dataTo = selected_item_to.getId();
            });




            sum = new int[1];
            sum_do = 0;
            sumList = new HashMap<>();
            headerTable.getModel().addTableModelListener(new TableModelListener() {

                @Override
                public void tableChanged(TableModelEvent e) {

                    if(e.getColumn() == 3) {
                        boolean colValue_s = (boolean) headerTable.getValueAt(e.getFirstRow(), 3);
                        int id_s = (int) headerTable.getValueAt(e.getFirstRow(), 0);
                        if (colValue_s) {
                            System.out.println("Wybieram kolumny do sumowania: " + id_s + count + sum_do);
                            //  sum[sum_do] = id-1;
                            // sum_do++;
                            sumList.put(id_s - 1, id_s - 1);
                        } else {
                            sumList.remove(id_s - 1);
                        }
                    }

                    if(e.getColumn() == 2  && e.getFirstRow()>-1){
                        int id =  e.getFirstRow();
                        int lp =  (int)headerTable.getValueAt(e.getFirstRow(), 0);
                        String colName = (String)headerTable.getValueAt(e.getFirstRow(), 1);
                        boolean colValue = (boolean)headerTable.getValueAt(e.getFirstRow(), 2);

                        System.out.println("Row : " + e.getFirstRow() +
                                   " value :" + headerTable.getValueAt(e.getFirstRow(), 2));
                        appListener.getColumnId(id);

                       // sum[sum_do] = id; //identyfikatory pól do sumowania

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

            JButton button = new JButton("Calculate");
            button.addActionListener(e -> {
                fireDataMapEvent(textField,sumList,dataFrom,dataTo);
            });

            resultPane.add(button);

            setLayout(gridLayout);
            operationContent.add(resultPane);
            add(scrollHeader);
            add(scrollOperation);
            add(resultPane);
        }
    }

    public void showResult(){

        resultMap = model.getData();

        if(resultMap.isEmpty()){

            JOptionPane.showMessageDialog(View.this,
                    "No data!", "Error",
                    JOptionPane.WARNING_MESSAGE);

        }else {
        final JFrame newpopup = new JFrame("Results");
        newpopup.setSize(500, 280);
        newpopup.setLocationRelativeTo(null);
        newpopup.setResizable(false);
        newpopup.setVisible(true);

        resultPane = new JPanel(new MigLayout("left",                 // Layout Constraints
                "left",             // Column constraints
                ""));
            for (Map.Entry<Integer, String> entry : headerMap.entrySet()) {

                for (Map.Entry<Integer, Integer> res : resultMap.entrySet()) {
                    if (entry.getKey() == res.getKey()) {

                        JLabel fi = new JLabel(entry.getValue() + ": " + res.getValue());
                        resultPane.add(fi, "wrap");

                    }

                }

            }

            JButton pdfButton = new JButton("Generate PDF");
            JButton okButton = new JButton("OK");
            JPanel buttonPanel = new JPanel(new MigLayout(
                    "center",                 // Layout Constraints
                    "[][]",             // Column constraints
                    "[][][]:push[]"));

            buttonPanel.add(pdfButton, "center");
            buttonPanel.add(okButton, "center");
            resultPane.add(buttonPanel, "center, grow, pushy, wrap push");

            pdfButton.addActionListener(e -> {


                PDF pdf = new PDF();
                pdf.setView(view);
                pdf.setHeaderMap(headerMap);
                pdf.setResultMap(resultMap);


                JFileChooser c = new JFileChooser();
                // Demonstrate "Save" dialog:
                int rVal = c.showSaveDialog(View.this);
                if (rVal == JFileChooser.APPROVE_OPTION) {
                    filename_pdf.setText(c.getSelectedFile().getName());
                    System.out.println(c.getSelectedFile().getName());
                    System.out.println(c.getCurrentDirectory().toString());
                    dir.setText(c.getCurrentDirectory().toString());
                    // StringBuilder sb = new StringBuilder();
                    //  sb.append(c.getCurrentDirectory().toString());
                    //  sb.append("/");

                    file = c.getCurrentDirectory().toString() + "\\" + c.getSelectedFile().getName() + ".pdf";
                    System.out.println(file);
                }

                if (rVal == JFileChooser.CANCEL_OPTION) {
                    filename_pdf.setText("You pressed cancel");
                    dir.setText("");
                }

                try {
                    Document document = new Document();
                    PdfWriter.getInstance(document, new FileOutputStream(file));
                    document.open();
                    pdf.addMetaData(document);
                    pdf.addTitlePage(document);
                    pdf.addContent(document);
                    document.close();
                    JOptionPane.showMessageDialog(View.this,
                            "The file has been saved",
                            "Information", JOptionPane.INFORMATION_MESSAGE);


                } catch (Exception error) {
                    JOptionPane.showMessageDialog(View.this,
                            "PDF create error, choose other path",
                            "Error", JOptionPane.WARNING_MESSAGE);
                }
            });

            okButton.addActionListener(e -> {
                newpopup.dispatchEvent(new WindowEvent(newpopup, WindowEvent.WINDOW_CLOSING));
            });


            scrollResult = new JScrollPane(resultPane);
            newpopup.add(scrollResult);
        }


    }

    public void fireDataMapEvent(Map<Integer,JTextField> textFieldEntry, Map<Integer,Integer> sumList,int from,int to)
    {
        viewListener.dataMap(textFieldEntry,filename,sumList,from,to);
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

    public void setViewListener(ViewListener viewListener){
        this.viewListener = viewListener;
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}

