package com.csv.transform.transformationStrategies.lowLevel.map;

import com.csv.transform.models.data.Cell;
import com.csv.transform.models.data.CsvTable;
import com.csv.transform.models.data.Request;
import com.csv.transform.models.data.Row;
import com.csv.transform.transformationStrategies.lowLevel.TransformationStrategy;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Arrays;
import java.util.List;

@Component
public class StringRequestToCsvTable implements TransformationStrategy<Request<String>, CsvTable> {
    private static final String COMMA_DELIMITER = ",";

    @Override
    public CsvTable transform(Request<String> input) {
        var table = new CsvTable();
        CSVFormat csvFormat = CSVFormat.DEFAULT.builder().build();
        try {
            Reader in = new FileReader(input.getBody());
            Iterable<CSVRecord> records = csvFormat.parse(in);
            for(CSVRecord record : records){
                var cells = record.toList();
                var row = new Row();
                for(String cell : cells) {
                    row.add(new Cell<>(cell));
                }
                table.add(row);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return table;
    }
    private List<List<String>> readLines(BufferedReader reader){
        return reader.lines()
                .map(line -> Arrays.asList(line.split(COMMA_DELIMITER)))
                .toList();
    }
}
