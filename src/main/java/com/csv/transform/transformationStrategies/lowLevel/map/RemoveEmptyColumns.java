package com.csv.transform.transformationStrategies.lowLevel.map;

import com.csv.transform.models.csv.CsvTable;
import com.csv.transform.models.csv.Row;
import com.csv.transform.transformationStrategies.lowLevel.TransformationStrategy;
import org.springframework.stereotype.Component;

@Component
public class RemoveEmptyColumns implements TransformationStrategy<CsvTable, CsvTable> {
    @Override
    public CsvTable transform(CsvTable input) {
        var numRows = input.rowSize();
        var outTable = new CsvTable(numRows);
        var columnCounter = 0;
        for(int i = 0; i < input.rowSize(); i++){
            var inRow = input.get(i);
            var outRow = new Row();
            for(int j = 0; j < inRow.size();j++){
                var cellData = inRow.get(j).toString();
                var isNotEmptyData = !cellData.isEmpty() && !cellData.isBlank() && !cellData.equals("*"); //&& !cellData.equals(" WILLIAM J");
                if(isNotEmptyData){
                    outRow.addCell(cellData);
                    if(outRow.size() > columnCounter) columnCounter++;
                }
            }
            outTable.add(outRow);
        }
        outTable.setNumColumns(columnCounter);
        return outTable;
    }
}
