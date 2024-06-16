package org.jfbarahonag.repository;

import java.sql.SQLException;
import java.util.List;

public interface IRepository <T> {
    List<T> findAll() throws SQLException;
    T getById(Integer id) throws SQLException;
    boolean save(T record) throws SQLException;
    Integer delete(Integer id) throws SQLException;
}
