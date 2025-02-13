package com.csv.transform.transformationStrategies.highLevel;

import com.csv.transform.models.csv.CsvTable;
import com.csv.transform.models.excel.ExcelIndexTable;
import com.csv.transform.transformationStrategies.lowLevel.TransformationStrategy;
import com.csv.transform.transformationStrategies.lowLevel.map.NormalizeColumns;
import com.csv.transform.transformationStrategies.lowLevel.map.RemoveEmptyColumns;
import com.csv.transform.transformationStrategies.lowLevel.map.MapByPurchaseKeywordColumn;
import com.csv.transform.transformationStrategies.lowLevel.build.BuildExcelIndexTable;
import com.csv.transform.transformationStrategies.lowLevel.combine.CombineCsvTables;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PurchaseKeywordTransformation implements TransformationStrategy<CsvTable, Pair<CsvTable, ExcelIndexTable>> {
    @Autowired
    NormalizeColumns normalizeColumns;
    @Autowired
    RemoveEmptyColumns removeEmptyColumns;
    @Autowired
    MapByPurchaseKeywordColumn mapByPurchaseKeywordColumn;
    @Autowired
    BuildExcelIndexTable buildExcelIndexTable;
    @Autowired
    CombineCsvTables combineCsvTables;

    @Override
    public Pair<CsvTable, ExcelIndexTable> transform(CsvTable input) {
        var normalized = normalizeColumns.transform(input);
        var removed = removeEmptyColumns.transform(normalized);
        var tables = mapByPurchaseKeywordColumn.transform(removed);
        var excelIndexTable = buildExcelIndexTable.transform(tables);
        var combined = combineCsvTables.transform(Pair.of(tables, excelIndexTable));
        return Pair.of(combined,excelIndexTable);
    }
}
