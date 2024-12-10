package com.csv.transform.transformationStrategies.lowLevel.map;

import com.csv.transform.mapperwrapper.ObjectMapperWrapper;
import com.csv.transform.models.data.Cell;
import com.csv.transform.models.data.CsvTable;
import com.csv.transform.models.data.Row;
import com.csv.transform.transformationStrategies.lowLevel.TransformationStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CsvTableToListOfCells implements TransformationStrategy<CsvTable, ArrayList<ArrayList<Cell<String>>>> {
    @Autowired
    ObjectMapperWrapper mapper;

    @Override
    public ArrayList<ArrayList<Cell<String>>> transform(CsvTable input) {
        var cells = new ArrayList<ArrayList<Cell<String>>>();
        for(Row row : input){
            var cellRow = new ArrayList<Cell<String>>();
            for(Object cellObj : row){
                var cell = mapper.objToStringCell(cellObj);
                cellRow.add(cell);
            }
            cells.add(cellRow);
        }
        return cells;
    }
}