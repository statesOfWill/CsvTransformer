package com.csv.transform;

import com.csv.transform.models.data.Request;
import com.csv.transform.models.data.Response;
import com.csv.transform.transformationStrategies.highLevel.*;
import com.csv.transform.transformationStrategies.lowLevel.map.*;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/init")
    Response initializeCsv(@RequestBody Request<String> request) {
        var source  = stringRequestToCsvTable.transform( request );
        var transformed = amountRangeTransformation.transform( source );
        var toListOfCells = csvTableToListOfCells.transform(transformed.getLeft());
        var savedToDb = saveCellsAsDb.transform(Pair.of(toListOfCells, transformed.getRight()));
        transformed.getLeft().setTransformationSuccessful(savedToDb);
        return csvTableToResponse.transform( transformed.getLeft() );
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
