package org.example.repository;

public interface CRUDRepository<T> {

    T getByArg(String arg);

    T getAll();

}
