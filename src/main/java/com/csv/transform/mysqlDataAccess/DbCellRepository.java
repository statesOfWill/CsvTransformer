package com.csv.transform.mysqlDataAccess;

import org.springframework.data.repository.CrudRepository;

// Spring automatically implements this repository interface in a bean that has the same name
// (with a change in the case - is called cellRepository )
public interface DbCellRepository extends CrudRepository<DbCell, Integer> { }
