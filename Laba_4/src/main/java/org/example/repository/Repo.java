package org.example.repository;

public interface Repo<T>{

    String getByArg(String arg);
    String getAll();
    boolean delete(String arg);
    boolean update(String newValue, String oldValue);
    boolean add(T value);

}
