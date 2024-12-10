package com.csv.transform.models.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Request<T> {
    private TransformationType transformationType;
    private T body;
}
