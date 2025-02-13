package com.csv.transform.transformationStrategies.lowLevel.map;

import com.csv.transform.models.csv.Cell;
import com.csv.transform.models.csv.CsvTable;
import com.csv.transform.models.csv.Row;
import com.csv.transform.transformationStrategies.lowLevel.TransformationStrategy;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Arrays;
import java.util.List;

@Component
public class StringRequestToCsvTable implements TransformationStrategy<String, CsvTable> {
    private static final String COMMA_DELIMITER = ",";

    @Override
    public CsvTable transform(String input) {
        CSVFormat csvFormat = CSVFormat.DEFAULT.builder().build();
        try {
            Reader in = new StringReader(input);
            Iterable<CSVRecord> records = csvFormat.parse(in);
            var table = new CsvTable();
            var recordCount = 0;
            for(CSVRecord record : records){
                if(recordCount < 1)
                    table.setNumColumns(record.size());
                recordCount++;
                System.out.println("row start");
                var row = new Row();
                for(String cell : record.toList()) {
                    System.out.println(cell);
                    row.add(new Cell<>(cell));
                }
                table.add(row);
                System.out.println("row end");
            }
            System.out.println("table end");
            return table;
//            for(CSVRecord record : records){
//                var cells = record.toList();
//                var row = new Row();
//                for(String cell : cells) {
//                    row.add(new Cell<>(cell));
//                }
//                table.add(row);
//            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    private List<List<String>> readLines(BufferedReader reader){
        return reader.lines()
                .map(line -> Arrays.asList(line.split(COMMA_DELIMITER)))
                .toList();
    }
}
