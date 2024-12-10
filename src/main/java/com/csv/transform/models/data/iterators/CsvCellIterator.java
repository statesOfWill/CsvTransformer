package com.csv.transform.models.data.iterators;

import java.util.Iterator;
import java.util.List;

public class CsvCellIterator implements Iterator<Object> {
    private final List<Object> cells;
    private int currentIndex = 0;

    public CsvCellIterator(List<Object> cells){
        this.cells = cells;
    }
    @Override
    public boolean hasNext() {
        return !this.cells.isEmpty() && this.cells.size() > ( currentIndex + 1 );
    }

    @Override
    public Object next() {
        this.currentIndex++;
        return this.cells.get(currentIndex);
    }
}
