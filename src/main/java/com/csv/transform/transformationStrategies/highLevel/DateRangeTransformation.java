package com.csv.transform.transformationStrategies.highLevel;

import com.csv.transform.models.data.CsvTable;
import com.csv.transform.models.data.ExcelIndexTable;
import com.csv.transform.transformationStrategies.lowLevel.TransformationStrategy;
import com.csv.transform.transformationStrategies.lowLevel.map.NormalizeColumns;
import com.csv.transform.transformationStrategies.lowLevel.map.RemoveEmptyColumns;
import com.csv.transform.transformationStrategies.lowLevel.map.MapByDateColumn;
import com.csv.transform.transformationStrategies.lowLevel.build.BuildExcelIndexTable;
import com.csv.transform.transformationStrategies.lowLevel.combine.CombineCsvTables;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DateRangeTransformation implements TransformationStrategy<CsvTable, Pair<CsvTable, ExcelIndexTable>> {
    @Autowired
    NormalizeColumns normalizeColumns;
    @Autowired
    RemoveEmptyColumns removeEmptyColumns;
    @Autowired
    MapByDateColumn mapByDateColumn;
    @Autowired
    BuildExcelIndexTable buildExcelIndexTable;
    @Autowired
    CombineCsvTables combineCsvTables;
    @Override
    public Pair<CsvTable, ExcelIndexTable> transform(CsvTable input) {
        var normalized = normalizeColumns.transform(input);
        var removed = removeEmptyColumns.transform(normalized);
        var tables = mapByDateColumn.transform(removed);
        var excelIndexTable = buildExcelIndexTable.transform(tables);
        var combined = combineCsvTables.transform(Pair.of(tables, excelIndexTable));
        return Pair.of(combined,excelIndexTable);
    }
}
