package com.csv.transform;

import com.csv.transform.models.excel.ExcelIndexTable;
import com.csv.transform.models.api.Request;
import com.csv.transform.models.api.Response;
import com.csv.transform.models.database.DbCell;
import com.csv.transform.transformationStrategies.highLevel.*;
import com.csv.transform.transformationStrategies.lowLevel.map.*;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CsvTransformationController {
    @Autowired
    CsvTableToListOfCells csvTableToListOfCells;
    @Autowired
    SaveCellsAsDb saveCellsAsDb;
    @Autowired
    StringRequestToCsvTable stringRequestToCsvTable;
    @Autowired
    MapRequestToCsvTable mapRequestToCsvTable;
    @Autowired
    CsvTableToResponse csvTableToResponse;
    @Autowired
    AmountRangeTransformation amountRangeTransformation;
    @Autowired
    DateRangeTransformation dateRangeTransformation;
    @Autowired
    HighLevelKeywordTransformation highLevelKeywordTransformation;
    @Autowired
    PurchaseKeywordTransformation purchaseKeywordTransformation;
    @Autowired
    PaymentKeywordTransformation paymentKeywordTransformation;
    @Autowired
    GetAllDbCellsAsListTransformation getAllDbCellsTransformation;

    @PostMapping("/init")
    Response initializeCsv(@RequestBody Request<String> request) {
        var source  = stringRequestToCsvTable.transform( request.getBody() );
        var excelIndexTable = new ExcelIndexTable(source.getNumRows(), source.getNumColumns());
        var savedToDb = saveCellsAsDb.transform(Pair.of(source, excelIndexTable));
        source.setTransformationSuccessful(savedToDb);
        return csvTableToResponse.transform( source );
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<DbCell> getAllCells() {
        return getAllDbCellsTransformation.transform("input");
    }

    @PostMapping("/map")
    Response mapBy(@RequestBody Request<List<String>> request) {
        var source  = mapRequestToCsvTable.transform( request );
        var target = switch(request.getTransformationType()){
            case AMOUNT_RANGE -> amountRangeTransformation.transform( source );
            case DATE_RANGE -> dateRangeTransformation.transform( source);
            case HIGH_LEVEL_KEYWORD -> highLevelKeywordTransformation.transform( source );
            case PAYMENT_KEYWORD -> paymentKeywordTransformation.transform( source );
            case PURCHASE_KEYWORD -> purchaseKeywordTransformation.transform( source );
        };
        assert target != null;
        return csvTableToResponse.transform( target.getLeft() );
    }
}
