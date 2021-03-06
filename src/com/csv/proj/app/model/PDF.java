package com.csv.proj.app.model;

/**
 * Created by Łukasz on 2015-03-01.
 */
import com.csv.proj.app.view.*;

import java.io.FileOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.*;


public class PDF {
    private static String FILE = "e:/FirstPdf.pdf";
    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);
    private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL, BaseColor.RED);
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
            Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.BOLD);
    private static Map<Integer,Integer> resultMap = new HashMap<>();
    private static Map<Integer,String> headerMap = new HashMap<>();
    private View view;

    public void PDF(){

    }

    public void setResultMap(Map<Integer,Integer> resultMap){
        this.resultMap = resultMap;
    }
    public void setHeaderMap(Map<Integer,String> headerMap){
        this.headerMap = headerMap;
    }
    public void setView(View view){
        this.view = view;
    }

    public void execute(){

    }



    // iText allows to add metadata to the PDF which can be viewed in your Adobe
    // Reader
    // under File -> Properties
    public static void addMetaData(Document document) {
        document.addTitle("My first PDF");
        document.addSubject("Using iText");
        document.addKeywords("Java, PDF, iText");
        document.addAuthor("Lars Vogel");
        document.addCreator("Lars Vogel");
    }

    public static void addTitlePage(Document document)
            throws DocumentException {
        Paragraph preface = new Paragraph();
        // We add one empty line
        addEmptyLine(preface, 1);
        // Lets write a big header
        preface.add(new Paragraph("Report", catFont));

        addEmptyLine(preface, 1);
        // Will create: Report generated by: _name, _date
        preface.add(new Paragraph("Report generated by: " + System.getProperty("user.name") + ", " + new Date(), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                smallBold));
        addEmptyLine(preface, 3);


        document.add(preface);

    }

    public static void addContent(Document document) throws DocumentException {
        Anchor anchor = new Anchor("", catFont);
        anchor.setName("");

        // Second parameter is the number of the chapter
        Chapter catPart = new Chapter(new Paragraph(anchor), 1);

        Paragraph subPara = new Paragraph("", subFont);
        Section subCatPart = catPart.addSection(subPara);

        // add a table
        createTable(subCatPart);

        PdfPTable table = new PdfPTable(2);


        StringBuilder sb = new StringBuilder();


        for (Map.Entry<Integer, String> entry : headerMap.entrySet()) {

            for (Map.Entry<Integer, Integer> res : resultMap.entrySet()) {
                if (entry.getKey() == res.getKey()) {
                    String strI = Integer.toString(res.getValue());
                    table.addCell(entry.getValue());
                    table.addCell(strI);

                }

            }

        }
        document.add(table);

        // now add all this to the document
       // document.add(catPart);
    }

    private static void createTable(Section subCatPart)
            throws BadElementException {
        PdfPTable table = new PdfPTable(2);


        StringBuilder sb = new StringBuilder();


        for (Map.Entry<Integer, String> entry : headerMap.entrySet()) {

            for (Map.Entry<Integer, Integer> res : resultMap.entrySet()) {
                if (entry.getKey() == res.getKey()) {
                    String strI = Integer.toString(res.getValue());
                    table.addCell(entry.getValue());
                    table.addCell(strI);

                }

            }

        }




        subCatPart.add(table);

    }

    private static void createList(Section subCatPart) {
        List list = new List(true, false, 10);
        list.add(new ListItem("First point"));
        list.add(new ListItem("Second point"));
        list.add(new ListItem("Third point"));
        subCatPart.add(list);
    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
}
