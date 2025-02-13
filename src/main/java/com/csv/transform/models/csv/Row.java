package com.csv.transform.models.csv;


import com.csv.transform.iterators.CsvCellIterator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class Row implements Collection<Object> {
    private final List<Object> cells;
    public Row(){
        cells = new ArrayList<>();
    }
    public Row(int numCells){
        cells = new ArrayList<>();
        for(int i = 0; i < numCells; i++){
            cells.add(new Cell<>());
        }
    }
    public Object get(int i){
       return this.cells.get(i);
    }
    public void addCell(String data){this.cells.add(new Cell<>(data));}
    public void addCell(Double data){
        this.cells.add(new Cell<>(data));
    }
    public void addCell(LocalDate data){
        this.cells.add(new Cell<>(data));
    }
    public int size(){
        return this.cells.size();
    }

    @Override
    public boolean isEmpty() {
        return this.cells.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return this.cells.contains(o);
    }

    @Override
    public Iterator<Object> iterator() {
        return new CsvCellIterator(this.cells);
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
    public boolean add(Object o) {
        return this.cells.add(o);
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<?> c) {
        this.cells.addAll(c);
        return true;
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

}
