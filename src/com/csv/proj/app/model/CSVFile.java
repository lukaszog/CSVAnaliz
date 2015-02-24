package com.csv.proj.app.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Łukasz on 2014-12-17.
 */
public class CSVFile {

    private List<String> header = new LinkedList<>();
    private List<Row>    row = new LinkedList<>();

    public CSVFile(List<Row> row, List<String> header)
    {
        this.row = row;
        this.header = header;
        //TODO sprawdzic poprawnosc pliku
    }

    public List<Row> filter(int startRow, int endRow, Map<Integer, String> constraint)
    {

        System.out.println(row.size());
        // List<Row> fromRange = row;
        List<Row> fromRange = row.subList(startRow-1,endRow);
        List<Row> filtered = new LinkedList<>();

        for(Row r: fromRange) {

            if (isRowCorrect(r,constraint)) {
                filtered.add(r);
            }
        }

        for(Row r : filtered) {
            List<String> rowData = r.getList();

            for(String s : rowData){
                System.out.print(s + ", ");
            }
            System.out.print("koniec wiersza\n");
        }

        return filtered;
    }

    public Map<Integer, Integer> calculate(int startRow, int endRow, Map<Integer, String> cellMap, int[] columnsIds) {

        Map<Integer,Integer> result = new HashMap<>();
        List<Row> filteredRows = filter(startRow,endRow,cellMap);
        //tutaj liczymy

        for(int columnId: columnsIds)
        {
            result.put(columnId,0);
        }

        for(Row row: filteredRows)
        {
            for(int columnId: columnsIds)
            {
                int sumforColumn=result.get(columnId);
                sumforColumn+=row.getCellInt(columnId);
                result.put(columnId,sumforColumn);

            }
        }

        return result;
    }

    private boolean isRowCorrect(Row row, Map<Integer, String> constraint) {
        for (int column : constraint.keySet()) {
            String value = constraint.get(column);

            if(!value.equals(row.getCell(column))) return false;
        }
        return true;
    }
}
