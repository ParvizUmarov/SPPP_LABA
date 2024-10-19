package org.example.service;


import java.util.Collection;

public interface CRUDService<T> {
    String getAll();
    T get(String name);
}
