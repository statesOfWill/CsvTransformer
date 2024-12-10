package com.csv.transform.transformationStrategies.lowLevel.map;

import com.csv.transform.models.data.CsvTable;
import com.csv.transform.models.data.Response;
import com.csv.transform.transformationStrategies.lowLevel.TransformationStrategy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class CsvTableToResponse implements TransformationStrategy<CsvTable, Response> {

    @Override
    public Response transform(CsvTable source) {
        if(source.isTransformationSuccessful())
            return new Response(HttpStatus.OK);
        else
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
