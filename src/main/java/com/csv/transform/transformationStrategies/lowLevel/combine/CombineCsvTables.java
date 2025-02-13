package com.csv.transform.transformationStrategies.lowLevel.combine;

import com.csv.transform.models.csv.CsvTables;
import com.csv.transform.models.csv.CsvTable;
import com.csv.transform.models.excel.ExcelIndexTable;
import com.csv.transform.transformationStrategies.lowLevel.TransformationStrategy;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

@Component
public class CombineCsvTables implements TransformationStrategy<Pair<CsvTables, ExcelIndexTable>, CsvTable> {

    @Override
    public CsvTable transform(Pair<CsvTables, ExcelIndexTable> input) {
        var indexTable = input.getRight();
        var tables = input.getLeft();
        var combinedTable = new CsvTable(indexTable.getNumRows(), indexTable.getNumColumns());
        for (CsvTable srcTable : tables) {
            for (int i = 0; i < srcTable.rowSize(); i++) {
                var srcRow = srcTable.get(i);
                var trgtRow = combinedTable.get(i);
                for (int j = 0; j < srcRow.size(); j++) {
                    trgtRow.addCell(srcRow.get(j).toString());
                }
            }
        }
        return combinedTable;
    }
}
