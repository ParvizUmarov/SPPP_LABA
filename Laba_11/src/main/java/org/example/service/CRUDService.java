package org.example.service;


import org.example.dto.ResponseDto;

import java.util.Collection;

public interface CRUDService<T> {
    Collection<T> getAll();
    T getById(int id);
    ResponseDto add(T data);
    ResponseDto update(T data);
    ResponseDto delete(int id);
    T getByMail(String mail);
}
