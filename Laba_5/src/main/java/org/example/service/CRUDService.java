package org.example.service;


public interface CRUDService<T> {
    String getAll();
    T get(String name);
    String add(T data);
    String update(T data);
    String delete(T data);
}
