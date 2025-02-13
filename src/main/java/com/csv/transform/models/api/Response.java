package com.csv.transform.models.api;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class Response {
    private final HttpStatus status;
}
