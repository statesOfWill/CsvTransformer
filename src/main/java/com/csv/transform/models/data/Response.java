package com.csv.transform.models.data;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class Response {
    private final HttpStatus status;
}
