package com.csv.transform.models.csv;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Range<T> {
    private T lowerBound;
    private T upperBound;
}
