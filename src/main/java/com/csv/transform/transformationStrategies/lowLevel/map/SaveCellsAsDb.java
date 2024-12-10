package com.csv.transform.transformationStrategies.lowLevel.map;

import com.csv.transform.mapperwrapper.ObjectMapperWrapper;
import com.csv.transform.models.data.Cell;
import com.csv.transform.models.data.ExcelIndexTable;
import com.csv.transform.mysqlDataAccess.DbCell;
import com.csv.transform.mysqlDataAccess.DbCellRepository;
import com.csv.transform.transformationStrategies.lowLevel.TransformationStrategy;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class SaveCellsAsDb implements TransformationStrategy<Pair<ArrayList<ArrayList<Cell<String>>>, ExcelIndexTable>, Boolean> {
    @Autowired
    private DbCellRepository cellRepository;
    @Autowired
    private ObjectMapperWrapper mapper;
    @Override
    public Boolean transform(Pair<ArrayList<ArrayList<Cell<String>>>, ExcelIndexTable> input) {
        var isSuccess = false;
        var cellList = input.getLeft();
        var indexTable = input.getRight();
        for(int i = 0; i < cellList.size();i++){
            var row = cellList.get(i);
            for(int j = 0; j < row.size(); j++){
                Cell<String> strCell = mapper.objToStringCell(row.get(j));
                DbCell dbCell = new DbCell(strCell.getData(), indexTable.get(i, j));
                cellRepository.save(dbCell);
            }
        }
        return isSuccess;
    }
}
