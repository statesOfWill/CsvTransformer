package com.csv.transform.transformationStrategies.lowLevel.build;

import com.csv.transform.models.data.CsvTables;
import com.csv.transform.models.data.CsvTable;
import com.csv.transform.models.data.ExcelIndexTable;
import com.csv.transform.transformationStrategies.lowLevel.TransformationStrategy;
import org.springframework.stereotype.Component;

@Component
public class BuildExcelIndexTable implements TransformationStrategy<CsvTables, ExcelIndexTable> {
    @Override
    public ExcelIndexTable transform(CsvTables input) {
        int numRows = input.getMaxNumRows();
        int numColumns = input.stream().map(CsvTable::columnSize).reduce(Integer::sum).orElseThrow();
        return new ExcelIndexTable(numRows, numColumns);
    }
}
