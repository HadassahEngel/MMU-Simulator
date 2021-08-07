package com.mby.dm;

import java.util.Objects;

/**
 *this class is a class of how the pages is stored in the program
 * @param <T> the type of what the pages contend
 */
public class DataModel<T>{
    private T content;
    private Long id;

    /**
     *
     * @param id the id of the page
     * @param content the value of the page
     */
    public DataModel(Long id, T content){
        this.content=content;
        this.id=id;
    }


    public T getContent() {
        return content;
    }

    public Long getDataModelId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setContent(T object) {
        this.content = object;
    }


    @Override
    public boolean equals(Object o) {
        if (!(o instanceof DataModel)) {
            return false;
        }
        DataModel<T> dm = (DataModel<T>) o;
        return this.content.equals(dm.content);
    }


    @Override
    public int hashCode() {
        return Objects.hash(content, id);
    }

    @Override
    public String toString() {
        return "DataModel{" +
                "content=" + content +
                ", id=" + id +
                '}';
    }
}
