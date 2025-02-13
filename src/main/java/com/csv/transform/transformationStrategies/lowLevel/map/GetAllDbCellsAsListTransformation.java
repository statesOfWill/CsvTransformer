package com.csv.transform.transformationStrategies.lowLevel.map;

import com.csv.transform.mapperwrapper.ObjectMapperWrapper;
import com.csv.transform.models.database.DbCell;
import com.csv.transform.models.database.DbCellRepository;
import com.csv.transform.transformationStrategies.lowLevel.TransformationStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GetAllDbCellsAsListTransformation implements TransformationStrategy<String, List<DbCell>> {
    @Autowired
    private DbCellRepository cellRepository;
    @Autowired
    private ObjectMapperWrapper mapper;

    @Override
    public List<DbCell> transform(String input) {
        var allList = new ArrayList<DbCell>();
        for(DbCell dBCell : cellRepository.findAll()){
            allList.add(dBCell);
        }
        return allList;
    }
}
