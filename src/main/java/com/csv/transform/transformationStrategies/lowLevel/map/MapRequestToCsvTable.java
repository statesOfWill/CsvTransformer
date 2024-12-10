package com.csv.transform.transformationStrategies.lowLevel.map;

import com.csv.transform.models.data.CsvTable;
import com.csv.transform.models.data.Request;
import com.csv.transform.transformationStrategies.lowLevel.TransformationStrategy;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.util.Arrays;
import java.util.List;

@Component
public class MapRequestToCsvTable implements TransformationStrategy<Request<List<String>>, CsvTable> {
    private static final String COMMA_DELIMITER = ",";

    @Override
    public CsvTable transform(Request<List<String>> input) {
        var table = new CsvTable();
//        try (BufferedReader reader = Files.newBufferedReader(Paths.get(input.getFilePath()))) {
//            var lines = readLines(reader);
//            table.setNumColumns(lines.getFirst().size());
//            table.setNumRows(lines.size());
//            for(List<String> line : lines){
//                var row = new Row();
//                for(String str : line){
//                    row.add(new Cell<Object>(str));
//                }
//                table.add(row);
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        return table;
    }
    private List<List<String>> readLines(BufferedReader reader){
        return reader.lines()
                .map(line -> Arrays.asList(line.split(COMMA_DELIMITER)))
                .toList();
    }
}
