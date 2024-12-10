package com.csv.transform.models.data.iterators;



import com.csv.transform.models.data.Row;

import java.util.Iterator;
import java.util.List;

public class CsvRowIterator implements Iterator<Row> {
    private final List<Row> rows;
    private int currentIndex = 0;

    public CsvRowIterator(List<Row> rows){
        this.rows = rows;
    }
    @Override
    public boolean hasNext() {
        return !this.rows.isEmpty() && this.rows.size() > ( currentIndex + 1 );
    }

    @Override
    public Row next() {
        this.currentIndex++;
        return this.rows.get(currentIndex);
    }
}
