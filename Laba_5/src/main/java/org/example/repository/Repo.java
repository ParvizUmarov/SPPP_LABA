package org.example.repository;

import java.util.Collection;

public interface Repo <T>{

    T getByArg(String arg);
    boolean add(T object);
    boolean delete(String name);
    boolean update(Object newValue, Object oldValue);
    Collection<T> getAll();

}
