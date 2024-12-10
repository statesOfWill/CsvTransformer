package com.csv.transform.models.data;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Cell<T> {
    T data;
    public Cell(T data) {
        this.data = data;
    }
    public String toString(){
        return this.data.toString();
    }
}
