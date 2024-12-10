package com.csv.transform.models.data;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

public class ExcelIndexTable {
    @Getter
    private final int numColumns;
    @Getter
    private final int numRows;
    private ArrayList<ArrayList<String>> indices;
    public ExcelIndexTable(int numRows, int numColumns) {
        this.numColumns = numColumns;
        this.numRows = numRows;
        this.setIndices(numRows, numColumns);
    }
    public void setIndices(int numRows, int numColumns){
        this.indices = new ArrayList<>();
        for (int i = 0; i < numRows; i++) {
            var row = new ArrayList<String>();
            for (int j = 0; j < numColumns; j++) {
                row.add(asciiIndex(i, j));
            }
            assert this.indices != null;
            this.indices.add(row);
        }
    }

    private String asciiIndex(int i, int j){
//        System.out.print(i);
//        System.out.println(","+j);
        int a = 65;
        int z = 90;
        if(j <= (z - a)) {
            return String.valueOf((char) (a + j)) + (i + 1);
        }else {
            var k = (j-1)%(z - a);

            return ((char) a) + String.valueOf((char) (a + k)) + (i + 1);
        }
    }

    public String get(int i, int j){
        return this.indices.get(i).get(j);
    }
}
