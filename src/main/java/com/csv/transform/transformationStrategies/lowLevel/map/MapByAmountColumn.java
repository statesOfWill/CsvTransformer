package com.csv.transform.transformationStrategies.lowLevel.map;

import com.csv.transform.mapperwrapper.ObjectMapperWrapper;
import com.csv.transform.models.data.CsvTable;
import com.csv.transform.models.data.CsvTables;
import com.csv.transform.models.data.Range;
import com.csv.transform.transformationStrategies.lowLevel.TransformationStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class MapByAmountColumn implements TransformationStrategy<CsvTable, CsvTables> {
    @Autowired
    ObjectMapperWrapper mapper;
    @Override
    public CsvTables transform(CsvTable source) {
        var target = new ArrayList<CsvTable>();
        var amountRanges = source.getAmountRanges();
        var mappingColIndex = source.getMappingColumnIndex();
        for(Range<Integer> range : amountRanges) {
            var lb = range.getLowerBound();
            var ub = range.getUpperBound();
            var rangeCsvTable = new CsvTable(source.getNumColumns(),source.getOutputColTitles());
            var rowCounter = 0;
            for (int i = 0; i < source.rowSize(); i++) {
                var row = source.get(i);
                var cell = mapper.objToDoubleCell(row.get(mappingColIndex));
                if (cellIsInAmountRange(cell.getData(), lb, ub)) {
                    rangeCsvTable.add(row);
                    rowCounter++;
                }
            }
            rangeCsvTable.setNumRows(rowCounter);
            target.add(rangeCsvTable);
        }
        return new CsvTables(target);
    }
//    public void addTitleRow(List<String> outputColTitles){
//        var titleRow = new Row();
//        var numOutputColumns = outputColTitles.size();
//        var titleMap = new HashMap<Integer, String>();
//        for(int i = 0; i < source.getNumColumns(); i++){
//            titleMap.put(i, outputColTitles.get(i%numOutputColumns));
//        }
//        for(int i = 0; i < source.getNumColumns(); i++) {
//            titleRow.addCell(titleMap.get(i));
//        }
//        source.setNumRows(source.getNumRows()+1);
//        source.getRows().addFirst(titleRow);
//    }

//
//    public CsvTableCollection removeEmptyColumns(){

//    }
//
//    public CsvTableCollection mapByAmountRanges(List<Range<Integer>> amountRanges, int mappingColIndex){
//        var CsvTables = new ArrayList<CsvTable>();
//        for(Range<Integer> range : amountRanges) {
//            var lb = range.getLowerBound();
//            var ub = range.getUpperBound();
//            var rangeCsvTable = new CsvTable(source.getNumColumns(),source.outputColTitles, source.getMapper());
//            var rowCounter = 0;
//            for (int i = 0; i < source.rowSize(); i++) {
//                var row = source.get(i);
//                var cell = mapper.objToDoubleCell(row.getCell(mappingColIndex));
//                if (cellIsInAmountRange(cell.getData(), lb, ub)) {
//                    rangeCsvTable.add(row);
//                    rowCounter++;
//                }
//            }
//            rangeCsvTable.setNumRows(rowCounter);
//            CsvTables.add(rangeCsvTable);
//        }
//        return new CsvTableCollection(source.getMapper(), CsvTables);
//    }
//
//    public CsvTableCollection mapByDateRanges(List<Range<LocalDate>> dateRanges, int mappingColIndex){
//        var CsvTables = new ArrayList<CsvTable>();
//        for(Range<LocalDate> range : dateRanges) {
//            var lb = range.getLowerBound();
//            var ub = range.getUpperBound();
//            var rangeCsvTable = new CsvTable(source.getNumColumns(),source.outputColTitles, source.getMapper());
//            var rowCounter = 0;
//            for (int i = 0; i < source.rowSize(); i++) {
//                var row = source.get(i);
//                var cell = mapper.objToDateCell(row.getCell(mappingColIndex));
//                if (cellIsInDateRange(cell.getData(), lb, ub)) {
//                    rangeCsvTable.add(row);
//                    rowCounter++;
//                }
//            }
//            rangeCsvTable.setNumRows(rowCounter);
//            CsvTables.add(rangeCsvTable);
//        }
//
//        return new CsvTableCollection(source.getMapper(), CsvTables);
//    }
//
//    public CsvTableCollection mapByKeywords(List<String> keywords, int mappingColIndex){
//        var CsvTables = new ArrayList<CsvTable>();
//        for(String keyword : keywords) {
//            var keywordCsvTable = new CsvTable(source.getNumColumns(),source.outputColTitles, source.getMapper());
//            var rowCounter = 0;
//            for (int i = 0; i < source.rowSize(); i++) {
//                var row = source.get(i);
//                var cell = mapper.objToStringCell(row.getCell(mappingColIndex));
//                if (cellContainsString(cell.getData(), keyword)) {
//                    keywordCsvTable.add(row);
//                    rowCounter++;
//                }
//            }
//            if(rowCounter > 1) {
//                keywordCsvTable.setNumRows(rowCounter);
//                CsvTables.add(keywordCsvTable);
//            }
//        }
//        return new CsvTableCollection(source.getMapper(), CsvTables);
//    }
//
//    public CsvTableCollection mapByExcelFunctions(CsvTable CsvTable, IndexCsvTable indexCsvTable, List<String> excelFunctions, Set<Integer> mappingColIndices){
//        Row row = new Row();
//        for(String excelFunction : excelFunctions){
//            if (excelFunction.equals("SUM")) {
//                for (int i = 0; i < CsvTable.getNumColumns(); i++) {
//                    if (mappingColIndices.contains(i)) {
//                        var colStart = indexCsvTable.get(0, i);
//                        var colEnd = indexCsvTable.get(CsvTable.getNumRows(), i);
//                        row.addCell("=SUM(" + colStart + ":" + colEnd + ")");
//                    }else {
//                        row.addCell("");
//                    }
//                }
//            }
//        }
//        CsvTable.addLast(row);
//        CsvTable.setNumRows(CsvTable.getNumRows() + 1);
//        return new CsvTableCollection(source.getMapper(), List.of(CsvTable));
//    }
//
    private boolean cellIsInAmountRange(Double cellData, int lowerBound, int upperBound){
        return lowerBound < cellData && cellData < upperBound;
    }
//
//    private boolean cellIsInDateRange(LocalDate cellData, LocalDate lowerBound, LocalDate upperBound){
//        return lowerBound.isBefore(cellData) && cellData.isBefore(upperBound);
//    }
//
//    private boolean cellContainsString(String cellData, String str){
//        return cellData.contains(str);
//    }
}
