package org.example.service;

public interface CRUDService<T> {
    String getAll();
    T get(String name);
}
