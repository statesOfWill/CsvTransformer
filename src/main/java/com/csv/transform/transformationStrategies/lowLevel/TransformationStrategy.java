package com.csv.transform.transformationStrategies.lowLevel;

public interface TransformationStrategy< I, O> {
    O transform( I input);
}
