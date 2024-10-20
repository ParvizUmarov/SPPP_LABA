package org.example.service;


import org.example.dto.BarberDto;

public interface CRUDService<T> {
    String getAll();
    T get(String name);
    String add(T data);
    String update(T data);
    String delete(T data);
}
