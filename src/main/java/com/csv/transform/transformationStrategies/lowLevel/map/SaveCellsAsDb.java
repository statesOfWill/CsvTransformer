package com.csv.transform.transformationStrategies.lowLevel.map;

import com.csv.transform.mapperwrapper.ObjectMapperWrapper;
import com.csv.transform.models.csv.Cell;
import com.csv.transform.models.csv.CsvTable;
import com.csv.transform.models.excel.ExcelIndexTable;
import com.csv.transform.models.database.DbCell;
import com.csv.transform.models.database.DbCellRepository;
import com.csv.transform.transformationStrategies.lowLevel.TransformationStrategy;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SaveCellsAsDb implements TransformationStrategy<Pair<CsvTable, ExcelIndexTable>, Boolean> {
    @Autowired
    private DbCellRepository cellRepository;
    @Autowired
    private ObjectMapperWrapper mapper;
    @Override
    public Boolean transform(Pair<CsvTable, ExcelIndexTable> input) {
        var cellList = input.getLeft();
        var indexTable = input.getRight();
        for(int i = 0; i < cellList.size();i++){
            var row = cellList.get(i);
            for(int j = 0; j < row.size(); j++){
                try {
                    Cell<String> strCell = mapper.objToStringCell(row.get(j));
                    var data = strCell.getData();
                    var excelIndex = indexTable.get(i, j);
                    System.out.print("saved data: ");
                    System.out.println(data);
                    System.out.print("with excel index: ");
                    System.out.println(excelIndex);
                    DbCell dbCell = new DbCell(data);
                    cellRepository.save(dbCell);
                }catch(Exception e){
                    return false;
                }
            }
        }
        return true;
    }
}
