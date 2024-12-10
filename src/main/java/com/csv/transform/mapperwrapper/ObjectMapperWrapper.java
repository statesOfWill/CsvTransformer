package com.csv.transform.mapperwrapper;

import com.csv.transform.models.data.Cell;
import com.csv.transform.models.data.CsvTable;
import com.csv.transform.models.data.Range;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class ObjectMapperWrapper {
    ObjectMapper mapper;
    public ObjectMapperWrapper(ObjectMapper mapper){
        this.mapper = mapper;
    }

    public CsvTable objToCsvTable(Object objTable){
        return mapper.convertValue(objTable, CsvTable.class);
    }

    public Cell<Double> objToDoubleCell(Object Cell){
        JavaType type = mapper.getTypeFactory().constructParametricType(Cell.class, Double.class);
        return mapper.convertValue(Cell, type);
    }
    private boolean isDoubleCell(Cell<String> Cell){
        try{
            var data = Cell.getData();
            mapper.convertValue(data, Double.class);
        }catch(Exception e){
//            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
    private boolean isDateCell(Cell<String> Cell){
        try{
            var data = Cell.getData();
            mapper.convertValue(data, LocalDate.class);
        }catch(Exception e){
//            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public Cell<String> objToStringCell(Object Cell){
        JavaType type = mapper.getTypeFactory().constructParametricType(Cell.class, String.class);
        return mapper.convertValue(Cell, type);
    }
    public Cell<LocalDate> objToDateCell(Object Cell){
        JavaType type = mapper.getTypeFactory().constructParametricType(Cell.class, LocalDate.class);
        return mapper.convertValue(Cell, type);
    }
    public Class<?> objToCSVDataType(Object objCell){
        var Cell = objToStringCell(objCell);
        if(isDoubleCell(Cell)){
            return Double.class;
        }else if(isDateCell(Cell)){
            return LocalDate.class;
        }else{
            return String.class;
        }
    }
    public List<String> objToOutputColTitles(Object outputColTitlesObj){
        List<String> outputColTitles = new ArrayList<>();
        var outputColTitlesObjList = mapper.convertValue(outputColTitlesObj, List.class);
        for(Object stringObj : outputColTitlesObjList){
            outputColTitles.add(mapper.convertValue(stringObj,String.class));
        }
        return outputColTitles;
    }

    public List<String> objToListOfString(Object obj){
        var objList = mapper.convertValue(obj, List.class);
        var strList = new ArrayList<String>();
        for(Object objStr : objList){
            strList.add(mapper.convertValue(objStr, String.class));
        }
        return strList;
    }
    public Set<Integer> objToSetOfInteger(Object obj){
        var objSet = mapper.convertValue(obj, Set.class);
        var intSet = new HashSet<Integer>();
        for(Object objStr : objSet){
            intSet.add(mapper.convertValue(objStr, Integer.class));
        }
        return intSet;
    }

    public List<Range<Integer>> objToAmountRanges(Object amountRangesObj){
        List<Range<Integer>> amountRanges = new ArrayList<>();
        var amountRangeObjList = mapper.convertValue(amountRangesObj, List.class);
        for(Object amountRangeObj : amountRangeObjList){
            var strObj = mapper.convertValue(amountRangeObj, String.class);
            var rangeArr = strObj.split(",");
            var lowerBound = mapper.convertValue(rangeArr[0], Integer.class);
            var upperBound = mapper.convertValue(rangeArr[1], Integer.class);
            amountRanges.add(new Range<>(lowerBound, upperBound));
        }
        return amountRanges;
    }
    public List<Range<LocalDate>> objToDateRanges(Object dateRangesObj){
        List<Range<LocalDate>> dateRanges = new ArrayList<>();
        var dateRangeObjList = mapper.convertValue(dateRangesObj, List.class);
        for(Object dateRangeObj : dateRangeObjList){
            var strObj = mapper.convertValue(dateRangeObj, String.class);
            var rangeArr = strObj.split(",");
            var lowerBound = mapper.convertValue(rangeArr[0], LocalDate.class);
            var upperBound = mapper.convertValue(rangeArr[1], LocalDate.class);
            dateRanges.add(new Range<>(lowerBound, upperBound));
        }
        return dateRanges;
    }
}
