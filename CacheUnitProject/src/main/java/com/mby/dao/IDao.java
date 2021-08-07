package com.mby.dao;

import java.io.FileNotFoundException;


/**
 *
 * @param <ID> the id of each page in the Hard Disk
 * @param <T> the type of the model of the pages
 */
public interface IDao<ID extends java.io.Serializable,T>{
    /**
     *
     * @param id the id of the page that you want the page
     * @return
     * @throws FileNotFoundException if there exception of reading from the Hard Disk
     */
    T find(ID id) throws FileNotFoundException;

    /**
     *
     * @param entity the page to delete from the Hard Disk
     * @throws Exception if the reading and writing to the Hard Disk with fails
     */
    void delete(T entity) throws Exception;

    /**
     *
     * @param entity the page to save from the Hard Disk
     * @throws Exception if the reading and writing to the Hard Disk with fails
     */
    void save(T entity) throws Exception;
}