package com.csv.transform.iterators;


import com.csv.transform.models.csv.CsvTable;

import java.util.Iterator;
import java.util.List;

public class CsvTableIterator implements Iterator<CsvTable> {
    private final List<CsvTable> csvTables;
    private int currentIndex = 0;

    public CsvTableIterator(List<CsvTable> csvTables){
        this.csvTables = csvTables;
    }
    @Override
    public boolean hasNext() {
        return !this.csvTables.isEmpty() && this.csvTables.size() > ( currentIndex + 1 );
    }

    @Override
    public CsvTable next() {
        currentIndex++;
        return this.csvTables.get(currentIndex);
    }
}
