package com.csv.transform.models.data;

import com.csv.transform.mapperwrapper.ObjectMapperWrapper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Data
public class CsvTables implements Collection<CsvTable> {
    @Autowired
    private ObjectMapperWrapper mapper;
    private final List<CsvTable> csvTables;

    public CsvTables(List<CsvTable> csvTables) {
        this.csvTables = csvTables;
    }

    public int getMaxNumRows(){
        AtomicInteger maxNumRows = new AtomicInteger(0);
        this.csvTables.stream().map(CsvTable::getNumRows).max(Integer::compareTo).ifPresent(maxNumRows::set);
        return maxNumRows.get();
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }


    @Override
    public Iterator<CsvTable> iterator() {
        return null;
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
    public boolean add(CsvTable rows) {
        return false;
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
    public boolean addAll(Collection<? extends CsvTable> c) {
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
}

//    public CsvTable getFirst(){
//        return this.csvTables.getFirst();
//    }
//
//    public CsvTable get(int i){
//        return this.csvTables.get(i);
//    }
//
//    @Override
//    public int size() {
//        return this.csvTables.size();
//    }
//
//    @Override
//    public boolean isEmpty() {
//        return this.csvTables.isEmpty();
//    }
//
//    @Override
//    public boolean contains(Object o) {
//        return this.csvTables.contains(o);
//    }
//
//    @Override
//    public Iterator<CsvTable> iterator() {
//        return new CsvTableIterator(this.csvTables);
//    }
//
//    @Override
//    public Object[] toArray() {
//        return new Object[0];
//    }
//
//    @Override
//    public <T> T[] toArray(T[] a) {
//        return null;
//    }
//
//    @Override
//    public boolean add(CsvTable csvTable) {
//        try {
//            this.csvTables.add(csvTable);
//            return true;
//        }catch(Exception e){
//            return false;
//        }
//    }
//
//    @Override
//    public boolean remove(Object o) {
//        try{
//            this.csvTables.remove(o);
//            return true;
//        }catch(Exception e){
//            return false;
//        }
//    }
//
//    @Override
//    public boolean containsAll(Collection<?> c) {
//        return false;
//    }
//
//    @Override
//    public boolean addAll(Collection<? extends CsvTable> c) {
//        return false;
//    }
//
//    @Override
//    public boolean removeAll(Collection<?> c) {
//        return false;
//    }
//
//    @Override
//    public boolean retainAll(Collection<?> c) {
//        return false;
//    }
//
//    @Override
//    public void clear() {
//
//    }
//}
