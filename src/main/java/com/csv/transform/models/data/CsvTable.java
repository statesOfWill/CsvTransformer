package com.csv.transform.models.data;

import com.csv.transform.models.data.iterators.CsvRowIterator;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Data
public class CsvTable implements Collection<Row> {
    private boolean transformationSuccessful;
    private String absoluteFilePath;
    private int numColumns;
    private int numRows;
    private int mappingColumnIndex;
    private List<String> outputColTitles;
    private List<Row> rows;
    private List<Range<Integer>> amountRanges;
    private List<Range<LocalDate>> dateRanges;
    private List<String> highLevelKeywords;
    private List<String> paymentKeywords;
    private List<String> purchaseKeywords;
    private List<String> outputColumnTitles;

    public CsvTable() {
        this.numColumns = 0;
        this.numRows = 0;
        this.rows = new ArrayList<>();
    }

    public CsvTable(int numRows, int numColumns) {
        this.numColumns = numColumns;
        this.numRows = numRows;
        this.rows = new ArrayList<>();
        for(int i = 0; i < numRows; i++){
            this.rows.add(new Row(numColumns));
        }
    }
    public CsvTable(int numColumns, List<String> outputColTitles) {
        this.numColumns = numColumns;
        this.rows = new ArrayList<>();
        this.outputColTitles = outputColTitles;
    }
    public CsvTable(int numRows) {
        this.rows = new ArrayList<>();
        this.numRows = numRows;
    }

    public Row get(int i){
        return this.rows.get(i);
    }

    public boolean replace(Row replace, Row with){
        var index = this.rows.indexOf(replace);
        this.rows.remove(replace);
        this.rows.add(index, with);
        return true;
    }

    @Override
    public int size() {
        return this.numRows;
    }

    @Override
    public boolean isEmpty() {
        return this.rows.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return this.rows.contains(o);
    }

    @Override
    public Iterator<Row> iterator() {
        return new CsvRowIterator(this.rows);
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean add(Row row) {
        this.rows.add(row);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        this.rows.remove(o);
        return true;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends Row> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    private void addLast(Row row){
        this.getRows().addLast(row);
    }

    public int rowSize(){
        return this.numRows;
    }
    public int columnSize(){
        return this.numColumns;
    }

}
