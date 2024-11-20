package org.example.repository;

public interface Repo<T> {

    T getAll();
    T getByArg(String arg);

}
