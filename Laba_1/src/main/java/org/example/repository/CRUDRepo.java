package org.example.repository;

public interface CRUDRepo<T> {

    T getByArg(String arg);
    T getAll();

}
