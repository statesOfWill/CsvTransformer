package com.csv.transform.transformationStrategies.lowLevel.map;

import com.csv.transform.models.data.CsvTable;
import com.csv.transform.models.data.Row;
import com.csv.transform.transformationStrategies.lowLevel.TransformationStrategy;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@Setter
@Component
public class NormalizeColumns implements TransformationStrategy<CsvTable, CsvTable> {
    private static final String COMMA_DELIMITER = ",";
    private int normalColSize;

    @Override
    public CsvTable transform(CsvTable emptyTable) {
        try {
            return normalizeColumns(emptyTable);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private CsvTable normalizeColumns(CsvTable table) throws IOException {
        var normalizedTable = new CsvTable(this.normalColSize);
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(table.getAbsoluteFilePath()))) {
            List<List<String>> lines = reader.lines()
                    .map(line -> Arrays.asList(line.split(COMMA_DELIMITER)))
                    .toList();
            for(List<String> line : lines){
                var truncatedRow = new Row();
                for(int i = 0; i < this.normalColSize; i++){
                    truncatedRow.add(line.get(i));
                }
                normalizedTable.add(truncatedRow);
            }
        }
        return normalizedTable;
    }
}
